package re.search;

import java.io.IOException;
import java.util.Iterator;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTSimpleDeclSpecifier;
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
	
	public void configure(String refactor) {
        model = re.use.Helper.getAtu(refactor);
        visitor =new JuvenateVisitor();
        model.accept(visitor);
	}
    
    @Override
    public int visit(IASTStatement statement) {
    	if (statement instanceof ICPPASTCompoundStatement block) {
        	replace(block);
//        	remove(statement);
//			
//		}else {
//	    	process(statement);			
		}
    	return super.visit(statement);
    }

	private void replace(IASTCompoundStatement parent) {
		
		var statements = parent.getChildren();
		
		for(var replacement : visitor.refactor.replacements) {
			var finds = replacement.getThenClause().getChildren();
			var newStats = replacement.getElseClause().getChildren();
			if(statements.length<finds.length) {
				break;
			}
			try {	
			for (int i = 0; i < statements.length; i++) {
				var matchAll= true;
				for (int j = 0; j < finds.length; j++) {
					if(!compareNode(statements[i+j], finds[j])){ 
						matchAll =false;
						break;
					}
				}
				if(matchAll) {
					IASTNode instert =null;
					if(statements.length<+finds.length) {
						instert =statements[i+finds.length];
					}
					for (int j = 0; j < newStats.length; j++) {
						rewriter.insertBefore(parent, instert, newStats[j], null); // ((statement[i+j], null);
					}	
					for (int j = 0; j < finds.length; j++) {
						rewriter.remove(statements[i+j], null);
					}
					i+=finds.length;
				}
			}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
//			var find = replacement.getThenClause().getChildren()[0];
//			if(find.getRawSignature().equals(first.getRawSignature())){ 
//				var rew = rewriter.replace(first, replacement.getElseClause().getChildren()[0], null);
//				return;
//			}
//			var replaceTarget = expandReference(parent.getChildren(),ref); 
//			var replacement = replacement.getElseClause().getChildren();
//		    rewriter.replace(statement, [0], null);
//		    

//	private boolean compareStatement(IASTNode statement, IASTNode reference) {
//		if(statement.getClass() == reference.getClass()) {
//			if(statement.getChildren()[0] instanceof ICPPASTFunctionCallExpression targetFun
//			&& reference.getChildren()[0] instanceof ICPPASTFunctionCallExpression referenceFun) {
//				if(targetFun.getFunctionNameExpression().toString().equals( referenceFun.getFunctionNameExpression().toString())) {
//					var targetArgs = targetFun.getArguments();
//					var referenceArgs = referenceFun.getArguments();
//					if(targetArgs.length !=referenceArgs.length) {
//						return false;
//					}
//					for (int i = 0; i < targetArgs.length; i++) {
//						if(!targetArgs[i].toString().equals(referenceArgs[i].toString())) {
//							return false;
//						}
//					
//					}
//					return true;
//			}
//			}
//			else if(statement.getChildren()[0] instanceof IASTSimpleDeclaration targetDecl
//			     && reference.getChildren()[0] instanceof IASTSimpleDeclaration referenceDecl) {
//
//				var targetArgs = targetDecl.getDeclarators();
//				var referenceArgs = referenceDecl.getDeclarators();
//
//				if(targetArgs.length !=referenceArgs.length) {
//					return false;
//				}
//				
//				for (int i = 0; i < targetArgs.length; i++) {
//					if(!targetArgs[i].toString().equals(referenceArgs[i].toString())) {
//						return false;
//					}
//				
//				}
//				return true;
////					if(targetDecl`.getFunctionNameExpression().toString().equals( referenceFun.getFunctionNameExpression().toString())) {
////								var targetArgs = targetFun.getArguments();
////								var referenceArgs = referenceFun.getArguments();
////								if(targetArgs.length !=referenceArgs.length) {
////									return false;
////								}
////								for (int i = 0; i < targetArgs.length; i++) {
////									if(!targetArgs[i].toString().equals(referenceArgs[i].toString())) {
////										return false;
////									}
////								
////								}
////								return true;
////					}
//				return true;
//			}
//		}
//		return false;
//	}

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

    private static boolean compareNode(IASTNode ref,  IASTNode target) {
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
    		return ref.toString().equals(target.toString());
    	}
        return true;
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