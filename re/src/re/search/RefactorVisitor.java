package re.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTIfStatement;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;

public class RefactorVisitor extends ASTVisitor {
	public RefactorVisitor() {
		super(true);
	}

	public List<ICPPASTIfStatement> replacements = new ArrayList<>();
	List<ICPPASTIfStatement> removements= new ArrayList<>();
	List<ICPPASTFunctionCallExpression> custom = new ArrayList<>();
	private HashMap<String, String> dynamic = new HashMap<>();

	@Override
	public int visit(IASTStatement statement) {
		if (statement instanceof ICPPASTIfStatement ifStatement) {
				replacements.add(ifStatement);
		}
		else if (statement instanceof ICPPASTFunctionCallExpression fun) {
			custom.add(fun);
			
		}
		return super.visit(statement);
	}

	public void replace(IASTCompoundStatement block, ASTRewrite rewrite) {
		for (var replacement : replacements) {
			var start = 0;
			var totalStatement = block.getChildren().length;
			int seachLength = replacement.getThenClause().getChildren().length;
			while (start >= 0 && start + seachLength <= totalStatement) {
				start = containNode(replacement.getThenClause(), block, start);
				if (start >= 0) {
					if(replacement.getElseClause()!=null) {
						replaceNewStatements(rewrite, block, replacement.getElseClause().getChildren(), start + 1);
					}
					removeStatement(rewrite, replacement.getThenClause().getChildren());
				}
				dynamic.clear();
			}
		}
	}

	private void removeStatement(ASTRewrite rewriter, IASTNode[] finds) {
		for (int j = 0; j < finds.length; j++) {
//			rewrite.remove(statements[start - j], null);
			String text = replaceDynanic(finds[j]);
			rewriter.remove(rewriter.createLiteralNode(text), null);
		}
	}

	private void replaceNewStatements(ASTRewrite rewriter, IASTCompoundStatement parent, IASTNode[] newStats, int insertOffset) {
			IASTNode instert = null;
			if (parent.getChildren().length < insertOffset) {
				instert = parent.getChildren()[insertOffset];
			}

			for (int j = 0; j < newStats.length; j++) {
				String text = replaceDynanic(newStats[j]);
				rewriter.insertBefore(parent, instert, rewriter.createLiteralNode(text), null);
			}
		}

	private String replaceDynanic(IASTNode newStat) {
		var text = newStat.getRawSignature();
		for (var entry : dynamic.entrySet()) {
			text = text.replace(entry.getKey(), entry.getValue());
		}
		return text;
	}



	private int containNode(IASTNode ref,  IASTNode target,int offset) {
    	var refChildren = ref.getChildren();
    	var targetChildren = target.getChildren();
    	if(offset+refChildren.length > targetChildren.length) {
    		return -1;
    	}
    	int j=0;
        for (int i = offset; i < targetChildren.length; i++) {
			if(compareNode(refChildren[j], targetChildren[i])){
				j++;
				if(j==refChildren.length){
					return i;
				}
			}else{
				j=0;
			}
        }
        return -1;
    }

	private boolean compareNode(IASTNode ref,  IASTNode target) {
		var refChildren = ref.getChildren();
		var targetChildren = target.getChildren();
		if(refChildren.length != targetChildren.length) {
			return false;
		}
		for (int i = 0; i < targetChildren.length; i++) {
			if(!compareNode(refChildren[i], targetChildren[i])){
				return false;
			}
		}

		if(targetChildren.length==0) {
			var refString = ref.toString();
			var targetString = target.toString();

			if(refString.startsWith("$")) {
				if(!dynamic.containsKey(refString)) {
					dynamic.put(refString,targetString);
				}
				return dynamic.get(refString).equals(targetString);
			}else {
				return refString.equals(targetString);
			}
		}
		return true;
	}
    public static String getNode(IASTNode node) {
        StringBuilder output = new StringBuilder();
        if (node != null) {
            processNode(output, "", node);
        }
        return output.toString();
    }


    private static void processNode(StringBuilder output, String indent, IASTNode node) {
        String raw = node.getRawSignature();
        String[] rawLines = raw.split(System.lineSeparator());

        output.append(indent + "(" + node.getClass().getSimpleName() + ", " + node.getFileLocation() + "):");
        if (rawLines.length < 2) {
            output.append(" |" + raw + "|");
        } else {
            for (String line : rawLines) {
                output.append(System.lineSeparator() + indent + "    |" + line + "|");
            }
        }
        output.append(System.lineSeparator());

        for (IASTNode child : node.getChildren()) {
            processNode(output, indent + "  ", child);
        }
    }

}
