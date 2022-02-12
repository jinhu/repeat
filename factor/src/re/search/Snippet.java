package re.search;

import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTIfStatement;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.ltk.core.refactoring.Change;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Snippet {
    private final ASTRewrite rewrite;
    private IASTCompoundStatement code;
    private HashMap<String, String> dynamicExpression = new HashMap<>();
    private HashMap<String, List<IASTNode>> dynamicLists = new HashMap<>();
    private int[] notFound = new int[0];
    ArrayList<IASTNode> wildcard = null;


    public Snippet(IASTCompoundStatement aCode, ASTRewrite aRewrite) {
        code = aCode;
        rewrite = aRewrite;
    }

    public List<Change> replace(ICPPASTIfStatement replacement) {
        boolean found;
        int offset = 0;
        dynamicLists = new HashMap<>();
        ArrayList<Change> changes = new ArrayList();
        do {
            var range = containNode(replacement.getThenClause(), code, offset);
            found = range.length > 0;
            if (found) {
                offset = range[1] + 1;
                var statements = code.getChildren();
                var instert = offset < code.getChildren().length ? statements[offset] : null;
                var statementsToRemove = Arrays.stream(Arrays.copyOfRange(code.getChildren(), range[0], offset));
                statementsToRemove.forEach(statement -> rewrite.remove(statement, null));
                if (replacement.getElseClause() != null) {
                    var statementsToAdd = Arrays.stream(replacement.getElseClause().getChildren());
                    statementsToAdd.flatMap(statement -> expandDynamic(statement))
                            .forEach(statement -> rewrite.insertBefore(code, instert, statement, null));
                }
                dynamicExpression.clear();
                dynamicLists.clear();
            }
        } while (found);

        return changes;
    }

    private Stream<IASTNode> expandDynamic(IASTNode newStat) {
        var text = newStat.getRawSignature();
        if (text.startsWith("$")) {
            return dynamicLists.get(text).stream();
        } else if (text.contains("$")) {
            return Stream.of(replaceDynamic(newStat));
        } else {
            return Stream.of(newStat);
        }
    }

    private IASTNode replaceDynamic(IASTNode newStat) {
        var text = newStat.getRawSignature();
        for (var entry : dynamicLists.entrySet()) {
            text = text.replace(entry.getKey(), entry.getValue().stream().map(IASTNode::getRawSignature).collect(Collectors.joining("")));
        }
        return rewrite.createLiteralNode(text);
    }


    private int[] containNode(IASTNode pattern, IASTNode match, int start) {
        var patterns = pattern.getChildren();
        var matches = match.getChildren();
        if (patterns.length == 0 ) {
            return compareEndNode(pattern, match) ?
                    found(0, 0) : notFound;
        }

        return containNode(patterns, matches, start);

    }

    private int[] containNode(IASTNode[] patterns, IASTNode[] matches, int start) {
        ArrayList<IASTNode> wildcard = null;
        int currentPattern = 0;

        if (start + patterns.length > matches.length) {
            return notFound;
        }

        for (int end = start; end < matches.length; end++) {
            var key = patterns[currentPattern].getRawSignature();
            var found = find(patterns[currentPattern], matches[end]);
            if (found) {
                //found move on to match next
                wildcard = null;
                currentPattern++;
            } else {
                if (wildcard != null) {//within wildcard
                    wildcard.add(matches[end]);
                }
                if (key.startsWith("$$")) {//start of wildcard
                    if (!dynamicLists.containsKey(key)) {//first encounter
                    	if(currentPattern+1 == patterns.length) {// no other patterh statement
                    		end = matches.length-1;
                    		dynamicLists.put(key, List.of(Arrays.copyOfRange(matches, start, end)));
                    		return found(start, end);
                    		
                    	}else {
                    		wildcard = new ArrayList<IASTNode>();
                    		dynamicLists.put(key, wildcard);
                    		currentPattern++;
                    	}

                    }else {
                    	var exp = dynamicLists.get(key).toArray();
                    	var foundExpansion =  containNode((IASTNode[])exp , matches, end);
                    	if(foundExpansion.length >0) {
                    		end = foundExpansion[1];
                    	}else {
                    		return notFound;
                    	}
                    }
                } else {
                    currentPattern = 0;
                }
            }
            if (currentPattern == patterns.length) {
                return found(start, end);
            }
        }
        return notFound;
    }

    private boolean find(IASTNode pattern, IASTNode match) {
        var result = containNode(pattern, match, 0);
        return result.length > 0;
    }

    private int[] found(int start, int end) {
        return new int[]{start, end};
    }


    private boolean compareEndNode(IASTNode ref, IASTNode target) {
        var refString = ref.toString();
        var targetString = target.toString();

        if (refString.startsWith("$") && !refString.startsWith("$$") ) {
            if (!dynamicExpression.containsKey(refString)) {
            	dynamicExpression.put(refString, targetString);
            }
            return dynamicExpression.get(refString).equals(targetString);
        } else {
            return refString.equals(targetString);
        }
    }
}
