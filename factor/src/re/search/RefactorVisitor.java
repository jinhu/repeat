package re.search;

import java.util.regex.Pattern;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompoundStatement;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICElementVisitor;
import org.eclipse.cdt.core.model.ICModel;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.BasicMonitor.Printing;
import org.eclipse.ltk.core.refactoring.Change;

import re.use.CppSourceVisitor;

/**
 * The activator class controls the plug-in life cycle
 */
public class RefactorVisitor  extends CppSourceVisitor{
	private Refactorings refactor;
	
@Override
	public void porcessfile(IASTTranslationUnit atu) {
		if(Pattern.matches(refactor.getFileFilter(),  atu.getFilePath())) {
			rewrite = ASTRewrite.create(atu);
			atu.accept(blockVisitor);
		}
	}

	public void setRefactorings(Refactorings refactor) {
		this.refactor = refactor; 
	}
	
	ASTVisitor blockVisitor = new ASTVisitor(true) {
        @Override
        public int visit(IASTStatement statement) {
            try {
	        	if (statement instanceof ICPPASTCompoundStatement block) {
	        		refactor.process(rewrite, block);
	            	var change = rewrite.rewriteAST();
	            	change.perform(progressMonitor);
				} 
            }catch (CoreException e) {
				e.printStackTrace();
		    }
        	return super.visit(statement);    	
        } 
    };
}
