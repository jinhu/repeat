package re.search;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIfdefStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorStatement;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompoundStatement;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICElementVisitor;
import org.eclipse.cdt.core.model.ICModel;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.internal.core.dom.parser.ASTAmbiguousNode;
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
	private TextMatcher textVisitor;
	
@Override
	public void porcessfile(IASTTranslationUnit atu) {
		rewrite = ASTRewrite.create(atu);
		textVisitor = new TextMatcher(atu, rewrite);
        atu.accept(blockVisitor);
        
        atu.accept(textVisitor);
    
    
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
	        	if (statement instanceof IASTPreprocessorIfdefStatement ifdef) {
	        		System.out.println(ifdef);
	        	}
	        	
            }catch (CoreException e) {
				e.printStackTrace();
		    }
        	return super.visit(statement);    	
        }  
    };

	private void removeObsoleteBranches(IASTPreprocessorStatement macro) {
		if(macro instanceof IASTPreprocessorIfdefStatement ifdef) {
			if(new String(ifdef.getCondition()).equals("MSC")){
				var newMacro =re.use.Helper.getAtu("tmp.c","#ifdef 0");
				var m =newMacro.getAllPreprocessorStatements()[0];
				rewrite.insertBefore(macro.getParent(), macro,  m, null);
				rewrite.remove(macro, null);
				rewrite.replace(macro, m, null);
				ifdef.getChildren();
				var mark = true;
			}
		}
//		if(macro instanceof IASTPreprocessor ifdef) {
//			if(ifdef.getCondition().toString().equals("MSC"){
//				mark = true;
//			}
//		}
//			if(ifdef.getCondition().toString().equals("MSC"){
//				mark = true;
//			}
//			if(ifdef.getCondition().toString().equals("MSC"){
//				mark = true;
//			}
//		}
	}
}
