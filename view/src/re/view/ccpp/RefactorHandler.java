package re.view.ccpp;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIfdefStatement;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompoundStatement;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICElementVisitor;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.BasicMonitor.Printing;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

import re.factor.ccpp.Helper;
import re.factor.ccpp.Refactorings;

public class RefactorHandler extends AbstractHandler implements ICElementVisitor{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			refactorings = getRefactoring(event);
			CoreModel.getDefault().getCModel().accept(this);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null;
	}


	private Refactorings getRefactoring(ExecutionEvent event) throws ExecutionException {
		var window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		var selectionService = window.getSelectionService();
		IWorkbenchPart workbenchPart = window.getActivePage().getActivePart(); 
		IFile file = (IFile) workbenchPart.getSite().getPage().getActiveEditor().getEditorInput().getAdapter(IFile.class);
		if (file == null) return null;
		var atu = Helper.getAtu(file.getRawLocation().toString());

		var selection = selectionService.getSelection();
		var refactorings = new Refactorings();
		atu.accept(refactorings);
		return refactorings;
	}
//	@Override
//	public boolean visit(ICElement element) throws CoreException {
//		if(element instanceof ITranslationUnit tu) {
//			var atu = tu.getAST();
//            if(atu!=null) {
//        		rewrite = ASTRewrite.create(atu);
//                atu.accept(visitor);
//            	var change = rewrite.rewriteAST();
//            	change.perform(progressMonitor);
//			}
//		}
//		return true;
//	}

	@Override
	public boolean visit(ICElement element) throws CoreException {
		HandlerHelper.visit(visitor, element, progressMonitor);
		return true;
	}


	Refactorings refactorings;
	ASTRewrite rewrite;

	protected IProgressMonitor progressMonitor = BasicMonitor.toIProgressMonitorWithBlocking(new Printing(System.out));


	ASTVisitor visitor = new ASTVisitor(true) {
        @Override
        public int visit(IASTStatement statement) {
        	if (statement instanceof ICPPASTCompoundStatement block) {
	        		refactorings.process(rewrite, block);
			}
        	return super.visit(statement);
        }
    };

}