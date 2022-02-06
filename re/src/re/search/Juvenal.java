package re.search;

import java.io.IOException;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionCallExpression;
import org.eclipse.core.runtime.CoreException;

import re.use.Appl;

public class Juvenal extends ASTVisitor implements Appl {
    private IASTTranslationUnit model;
	private JuvenateVisitor visitor;
	private ASTRewrite rewriter;
    public Juvenal(String string) {
    	super(true);
    	configure(string);
	}
    
	@Override
    public void start(String[] args) throws CoreException {
		var config="c:\\sw-dev\\re\\config\\sample.re.factor.c";
    	System.out.println(config);
        configure(config);
    }
	
    public static void main(String[] args) throws IOException {
    	var juvenal = new Juvenal(args[0]);
    }

	public void configure(String refactor) {
        model = re.use.Helper.getAtu(refactor);
        visitor =new JuvenateVisitor();
        model.accept(visitor);
	}
    
    @Override
    public int visit(IASTStatement statement) {
//    	if (statement instanceof ICPPASTCompoundStatement block) {
        	replace(statement);
//        	remove(statement);
//			
//		}else {
//	    	process(statement);			
//		}
    	return super.visit(statement);
    }

	private void replace(IASTStatement first) {
		
		for(var replacement : visitor.refactor.replacements) {
			var find = replacement.getThenClause().getChildren()[0];
			if(find.getRawSignature().equals(first.getRawSignature())){ 
				var rew = rewriter.replace(first, replacement.getElseClause().getChildren()[0], null);
				return;
			}
//			var replaceTarget = expandReference(parent.getChildren(),ref); 
//			var replacement = replacement.getElseClause().getChildren();
//		    rewriter.replace(statement, [0], null);
//		    
		}
	}

//	private Object expandReference(ICPPASTCompoundStatement parent, IASTNode[] ref) {
//		var expandedRef = new ArrayList<IASTNode>();
//		var base =  parent.getChildren();
//		for (int i = 0; i < base.length; i++) {
//			var allMatch = true;
//			for (int j = 0; j < ref.length; j++) {
//			
//				if(ref[j].getClass()==base[i+j].getClass()) {
//					//find wildcard
//				}else {
//					allMatch = false;
//					break;
//				}
//			}
//			if(allMatch) {
//				return expandedRef;
//			}
//			
//		}
//		return ref;
//	
//	}

//	private void process(IASTStatement statement) {
//		
//	}

//	private void remove(IASTStatement statement) {
//		for(var ifStatement: visitor.refactor.removements) {
//			var remove = expand(ifStatement.getThenClause().getChildren(), statements);
//		        rewriter.remove((rest, null);
//		    }
//		}		
//	}


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

	public void setRewriter(ASTRewrite rewrite) {
		rewriter =rewrite;
		
	}

}