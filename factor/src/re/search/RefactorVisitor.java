package re.search;

import java.util.Arrays;

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
	private IProgressMonitor progressMonitor;
	private ASTRewrite rewrite;
	private Refactorings refactor;
	private TextMatcher textVisitor;
	
	public RefactorVisitor(){
		progressMonitor = BasicMonitor.toIProgressMonitorWithBlocking(new Printing(System.out));
    }

	@Override
	public void porcessfile(IASTTranslationUnit atu) {
		rewrite = ASTRewrite.create(atu);
		textVisitor = new TextMatcher(atu, rewrite);
        atu.accept(blockVisitor);
        
        atu.accept(textVisitor);
		
        
//        Arrays.stream(atu.getAllPreprocessorStatements())
//        .filter(m->m.getContainingFilename().contains(atu.getFilePath()))
//        .forEach(this::removeObsoleteBranches);
//        
//     	var change = rewrite.rewriteAST();
//     	try {
//			change.perform(progressMonitor);
//		} catch (CoreException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        for (var macro : macros) {
//			System.out.println(macro.getRawSignature()+" from "+macro.getContainingFilename());
//		}
        
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
