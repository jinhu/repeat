package re.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTIfStatement;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;

public class RefactorVisitor extends ASTVisitor {
	public RefactorVisitor() {
		super(true);
	}

	public List<ICPPASTIfStatement> replacements = new ArrayList<>();
	List<ICPPASTFunctionCallExpression> custom = new ArrayList<>();
	private HashMap<String, String> dynamic = new HashMap<>();
	private HashMap<String, IASTNode> dynamicStatements = new HashMap<>();
	private HashMap<String, List<IASTNode>> dynamicLists = new HashMap<>();

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

	public void replace(ASTRewrite rewrite, IASTCompoundStatement block) {
		for (var replacement : replacements) {
			var start = new int[] {0,0};
			var totalStatement = block.getChildren().length;
			int seachLength = replacement.getThenClause().getChildren().length;
			while (start.length >= 0 && start[1] + seachLength <= totalStatement) {
				start = containNode(replacement.getThenClause(), block, start[1]+1);

				if (start.length > 0) {
					removeStatement(rewrite, Arrays.copyOfRange(block.getStatements(), start[0], start[1]+1) );
					if(replacement.getElseClause()!=null) {
						replaceNewStatements(rewrite, block, replacement.getElseClause().getChildren(), start[1] + 1);
					}
				}
				dynamic.clear();
				dynamicLists.clear();
			}
		}
	}

	private void removeStatement(ASTRewrite rewriter, IASTNode[] finds) {
		for (var find : finds) {
			rewriter.remove(find, null);
		}
	}

	private void replaceNewStatements(ASTRewrite rewriter, IASTCompoundStatement parent, IASTNode[] newStats, int insertOffset) {
			IASTNode instert = null;
			if (parent.getChildren().length > insertOffset) {
				instert = parent.getChildren()[insertOffset];
			}

			for (var newStat : newStats) {
				var text = newStat.getRawSignature();
				if(text.startsWith("$")) {
					var statments = dynamicLists.get(text);
					for(var statement : statments ) {
						rewriter.insertBefore(parent, instert, statement, null);
						
					}
				}else if(text.contains("$")) {
					text = replaceDynamic(newStat);
					rewriter.insertBefore(parent, instert, rewriter.createLiteralNode(text), null);
					
				}else {
					rewriter.insertBefore(parent, instert, newStat, null);
				}
			}
		}

	private String replaceDynamic(IASTNode newStat) {
		var text = newStat.getRawSignature();
		for (var entry : dynamic.entrySet()) {
			text = text.replace(entry.getKey(), entry.getValue());
		}
		return text;
	}



	private int[] containNode(IASTNode ref,  IASTNode target,int offset) {
    	var refChildren = ref.getChildren();
    	var targetChildren = target.getChildren();
    	if(offset+refChildren.length > targetChildren.length) {
    		return new int[0];
    	}

    	int j=0;
    	ArrayList<IASTNode> statList = null;
    	int matchStart=-1;
        for (int i = offset; i < targetChildren.length; i++) {
        	if(j==0) {
        		matchStart=i;
        	}
        	if(refChildren[j].getRawSignature().startsWith("$$")) {
				if(!dynamicLists.containsKey(refChildren[j].toString())) {
					 statList = new ArrayList<IASTNode>();
					dynamicLists.put(refChildren[j].toString(), statList);
					statList.add(targetChildren[i]);
					j++;
				}
				
			
			}else if(refChildren[j].getRawSignature().startsWith("$")) {
				statList = null;
					if(!dynamicLists.containsKey(refChildren[j].toString())) {
						dynamicLists.put(refChildren[j].toString(),List.of(targetChildren[i]));
						j++;
					}
					else if(compareNode(dynamicLists.get(refChildren[j]).get(0), targetChildren[i])){
					j++;
					matchStart=i;
				}
			}else if(compareNode(refChildren[j], targetChildren[i])){
				statList = null;

				j++;
			}else{
				if(statList!=null) {
					statList.add(targetChildren[i]);
				}else {
					j=0;
				}
			}
        	
			if(j==refChildren.length){
				return new int[]{matchStart,i};
			}
        }
        return new int[0];
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
