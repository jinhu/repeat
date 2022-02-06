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
        	visitor.refactor.replace(block);
//        	remove(statement);
//			
//		}else {
//	    	process(statement);			
		}
    	return super.visit(statement);
    }


	public void setRewriter(ASTRewrite rewrite) {
		rewriter =rewrite;
		
	}

}