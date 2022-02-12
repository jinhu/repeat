package re.search;

import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTIfStatement;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
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
    private int[] notFound=new int[0];


    public Snippet(IASTCompoundStatement aCode, ASTRewrite aRewrite) {
        code = aCode;
        rewrite = aRewrite;
    }

    public List<Change> replace(ICPPASTIfStatement replacement) {
        boolean found;
        int offset = 0;
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
                if(replacement.getElseClause()!=null) {
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


    private int[] containNode(IASTNode pattern, IASTNode match, int offset) {
        var patterns = pattern.getChildren();
        var matches = match.getChildren();
        if (offset + patterns.length > matches.length) {
            return notFound;
        }
        if(patterns.length == 0 && matches.length == 0){
            //if no children
            return compareEndNode(pattern, match)?
            found(0, 0): notFound;
        }

        int j = 0;
        ArrayList<IASTNode> statList = null;
        int matchStart = -1;
        for (int i = offset; i < matches.length; i++) {
            if (j == 0) {
                matchStart = i;
            }
        	var key = patterns[j].getRawSignature();
            if (key.startsWith("$$")) {
                if (!dynamicLists.containsKey(key)) {
                    if(j+1<patterns.length) {
                        statList = new ArrayList<IASTNode>();
                    	dynamicLists.put(key, statList);
                    	statList.add(matches[i]);
                    	j++;
                    }else {
                    	dynamicLists.put(key, List.of(matches));
                    	return  new int[] {0,matches.length-1};
                    }
                }
                
            } else if (containNode(patterns[j], matches[i],0).length>0) {
                statList = null;
                j++;
            } else {
                if (statList != null) {
                    statList.add(matches[i]);
                } else {
                    j = 0;
                }
            }

            if (j == patterns.length) {
                return new int[]{matchStart, i};
            }
        }


    }

    private int[] found(int start, int end){ return new int[]{start,end}};


    private boolean compareEndNode(IASTNode ref, IASTNode target) {
		var refString = ref.toString();
		var targetString = target.toString();

		if (refString.startsWith("$")) {
		    if (!dynamicLists.containsKey(refString)) {
		    	dynamicLists.put(refString, List.of(target));
		    }
		    return dynamicLists.get(refString).equals(targetString);
		} else {
		    return refString.equals(targetString);
		}
	}
}
