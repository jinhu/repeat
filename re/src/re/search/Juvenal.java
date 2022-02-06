package re.search;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
import re.use.Helper;

public class Juvenal extends ASTVisitor implements Appl {
    private HashMap<String, String> dynamic = new HashMap<>();
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
					if(!compareNode(finds[j], statements[i+j] )){ 
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
						var text = newStats[j].getRawSignature();
						for (var entry : dynamic.entrySet()) {
							text = text.replace(entry.getKey(),entry.getValue());
						}
						rewriter.insertBefore(parent, instert,rewriter.createLiteralNode(text), null); // ((statement[i+j], null);
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

	public void setRewriter(ASTRewrite rewrite) {
		rewriter =rewrite;
		
	}

}