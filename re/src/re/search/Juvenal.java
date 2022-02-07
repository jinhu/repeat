package re.search;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompoundStatement;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
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
    	System.out.println(args[0]);
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
        	visitor.refactor.process(rewriter,block);
//		}else {
//	    	process(statement);			
		}
    	return super.visit(statement);
    }


	public void setRewriter(ASTRewrite rewrite) {
		rewriter =rewrite;
		
	}

}