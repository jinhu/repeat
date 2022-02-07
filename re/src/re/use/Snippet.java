package re.use;

import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTIfStatement;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class Snippet {
    private final ASTRewrite rewrite;
    private IASTCompoundStatement code;
    private HashMap<String, String> dynamicExpression = new HashMap<>();
    private HashMap<String, List<IASTNode>> dynamicLists = new HashMap<>();


    public Snippet(IASTCompoundStatement aCode, ASTRewrite aRewrite) {
        code = aCode;
        rewrite = aRewrite;
    }

    public void replace(ICPPASTIfStatement replacement) {
        boolean found;
        int offset = 0;
        do {
            var range = containNode(replacement.getThenClause(), code, offset);
            found = range.length > 0;
            if (found) {
                offset = range[1] + 1;
                var statements = code.getChildren();
                var instert = offset < code.getChildren().length ? statements[offset] :null;
                var statementsToRemove = Arrays.stream(Arrays.copyOfRange(code.getChildren(), range[0], offset));
                statementsToRemove.forEach(statement->rewrite.remove(statement, null));
                var statementsToAdd = Arrays.stream(replacement.getElseClause().getChildren());
                statementsToAdd.flatMap(statement->expandDynamic(statement))
                 .forEach(statement->rewrite.insertBefore(code, instert, statement, null));
            }
            dynamicExpression.clear();
            dynamicLists.clear();
        } while (found);
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
        for (var entry : dynamicExpression.entrySet()) {
            text = text.replace(entry.getKey(), entry.getValue());
        }
        return rewrite.createLiteralNode(text);
    }


    private int[] containNode(IASTNode ref, IASTNode target, int offset) {
        var refChildren = ref.getChildren();
        var targetChildren = target.getChildren();
        if (offset + refChildren.length > targetChildren.length) {
            return new int[0];
        }

        int j = 0;
        ArrayList<IASTNode> statList = null;
        int matchStart = -1;
        for (int i = offset; i < targetChildren.length; i++) {
            if (j == 0) {
                matchStart = i;
            }
            if (refChildren[j].getRawSignature().startsWith("$$")) {
                if (!dynamicLists.containsKey(refChildren[j].toString())) {
                    statList = new ArrayList<IASTNode>();
                    dynamicLists.put(refChildren[j].toString(), statList);
                    statList.add(targetChildren[i]);
                    j++;
                }


            } else if (refChildren[j].getRawSignature().startsWith("$")) {
                statList = null;
                if (!dynamicLists.containsKey(refChildren[j].toString())) {
                    dynamicLists.put(refChildren[j].toString(), List.of(targetChildren[i]));
                    j++;
                } else if (compareNode(dynamicLists.get(refChildren[j]).get(0), targetChildren[i])) {
                    j++;
                    matchStart = i;
                }
            } else if (compareNode(refChildren[j], targetChildren[i])) {
                statList = null;

                j++;
            } else {
                if (statList != null) {
                    statList.add(targetChildren[i]);
                } else {
                    j = 0;
                }
            }

            if (j == refChildren.length) {
                return new int[]{matchStart, i};
            }
        }
        return new int[0];
    }

    private boolean compareNode(IASTNode ref, IASTNode target) {
        var refChildren = ref.getChildren();
        var targetChildren = target.getChildren();
        if (refChildren.length != targetChildren.length) {
            if (ref.getRawSignature().startsWith("$")) {
                dynamicLists.put(ref.getRawSignature(), List.of(target));
                return true;
            }
            return false;
        }
        for (int i = 0; i < targetChildren.length; i++) {
            if (!compareNode(refChildren[i], targetChildren[i])) {
                return false;
            }
        }

        if (targetChildren.length == 0) {
            var refString = ref.toString();
            var targetString = target.toString();

            if (refString.startsWith("$")) {
                if (!dynamicExpression.containsKey(refString)) {
                    dynamicExpression.put(refString, targetString);
                }
                return dynamicExpression.get(refString).equals(targetString);
            } else {
                return refString.equals(targetString);
            }
        }
        return true;
    }
}
