package re.view.java;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.ltk.core.refactoring.Change;

public class SnippetMatcher {
    private Block code;
	private ASTRewrite rewrite;
	private HashMap<String, List<Statement>>  placeholders;
	static private int[] notFound = new int[0];
  private HashMap<String, String> wildCards = new HashMap<>();

    public SnippetMatcher(Block aCode, ASTRewrite aRewrite) {
        code = aCode;
        rewrite = aRewrite;
    }

    public List<Change> replace(IfStatement replacement) {
        boolean found;
        int offset = 0;
        placeholders = new HashMap<>();
        var matches =code.statements();
        var patterns = ((Block)replacement.getThenStatement()).statements();
        ArrayList<Change> changes = new ArrayList();
        do {
            var range = containNode(patterns, matches, offset);
            found = range.length > 0;
            if (found) {
                offset = range[1] + 1;
                var instert = offset < matches.size() ? matches.get(offset) : null;
                var statementsToRemove = matches.subList(range[0], offset).stream();
//                statementsToRemove.forEach(statement -> rewrite.remove(statement, null));
                if (replacement.getElseStatement() != null) {
                    Stream<Statement> statementsToAdd = ((Block)replacement.getElseStatement()).statements().stream();
//                    statementsToAdd.flatMap(statement -> expandDynamic(statement))
//                    .forEach(statement -> rewrite.insertBefore(code, instert, statement, null));
                }
                wildCards.clear();
                placeholders.clear();
            }
        } while (found);

        return changes;
    }

    private Stream<Statement> expandDynamic(Statement newStat) {
        var text = newStat.toString();
        if (text.startsWith("$")) {
            return placeholders.get(text).stream();
        } else if (text.contains("$")) {
            return Stream.of(replaceDynamic(newStat));
        } else {
            return Stream.of(newStat);
        }
    }

    private Statement replaceDynamic(Statement newStat) {
        var text = newStat.toString();
        for (var entry : placeholders.entrySet()) {
            text = text.replace(entry.getKey(), entry.getValue().stream().map(Statement::toString).collect(Collectors.joining("")));
        }
        return null;//rewrite.createLiteralNode(text);
    }


    private int[] containNode(Statement pattern, Statement match, int start) {
    	if(pattern instanceof Block patternBlock) {
        	var matches = ((Block)match).statements();
            return containNode(patternBlock.statements(), matches, start);

    	} else {
            return compareLeaf(pattern, match) ?  found(0, 0) : notFound;
        }

    }

    private int[] containNode(List<Statement> patterns, List<Statement> matches, int start) {
        ArrayList wildcard = null;
        int currentPattern = 0;
        
        //not enough statement
        if (start + patterns.size() > matches.size()) {
            return notFound;
        }

        for (int index = start; index < matches.size()+start; index++) {
            var key = patterns.get(currentPattern).toString();
            var found = find(patterns.get(currentPattern), matches.get(index));
            if (found) {
                //found move on to match next
                wildcard = null;
                currentPattern++;
            } else {
                if (wildcard != null) {//within wildcard
                    wildcard.add(matches.get(index));
                }
                if (key.startsWith("$$")) {//start of wildcard
                    if (!placeholders.containsKey(key)) {//first encounter
                    	if(currentPattern+1 == patterns.size()) {// no other patterh statement
                    		index = matches.size()-1;
                    		placeholders.put(key, matches.subList(start, index));
                    		return found(start, index);
                    		
                    	}else {
                    		wildcard = new ArrayList<Statement>();
                    		placeholders.put(key, wildcard);
                    		currentPattern++;
                    	}

                    }else {
                    	var exp = placeholders.get(key);
                    	var foundExpansion =  containNode(exp , matches, index);
                    	if(foundExpansion.length >0) {
                    		index = foundExpansion[1];
                    	}else {
                    		return notFound;
                    	}
                    }
                } else {
                    currentPattern = 0;
                }
            }
            if (currentPattern == patterns.size()) {
                return found(start, index);
            }
        }
        return notFound;
    }

    private boolean find(Statement pattern, Statement match) {
        var result = containNode(pattern, match, 0);
        return result.length > 0;
    }


    private int[] found(int start, int end) {
        return new int[]{start, end};
    }


    private boolean compareLeaf(Statement ref, Statement target) {
        var refString = ref.toString();
        var targetString = target.toString();

        if (refString.startsWith("$") && !refString.startsWith("$$") ) {
            if (!wildCards.containsKey(refString)) {
            	wildCards.put(refString, targetString);
            }
            return wildCards.get(refString).equals(targetString);
        } else {
            return refString.equals(targetString);
        }
    }
}
