package re.view;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import re.search.Refactorings;
import re.search.CppSourceVisitor;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

public class RefactorHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		var window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		var wb = PlatformUI.getWorkbench();
		var activeWorkbenchWindow = wb.getActiveWorkbenchWindow();
		var selectionService = activeWorkbenchWindow.getSelectionService();
		var selection = selectionService.getSelection();
		var refactorings = new Refactorings();

	    IASTTranslationUnit atu = null;
		if (selection instanceof IStructuredSelection structured) {
		    var cu = structured.getFirstElement() ;
//		    var ir = ((IFile)(cu).getResource());
//		    if (cu instanceof ICompulationUnit) {
//				ICOMPulationUnit new_name = (ICOMPulationUnit)cu;
//				
//			}

			
		}
	    else {
			var doc = ((TextSelection) selection).getText();
			atu = re.use.Helper.getAtu("tmp.c", doc);
	    	
	    }
		atu.accept(refactorings);
		var visitor = new CppSourceVisitor();
		visitor.setRefactorings(refactorings);
		var cModel = CoreModel.getDefault().getCModel();
		try {
//			cModel.accept(visitor);
			for (var project : cModel.getCProjects()) {
				project.accept(visitor);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

//		   IFile ir = ((IFile)(
//				   ).getResource());
//		   var test = structSelection.toString();
//		   MessageDialog.openInformation( window.getShell(),"File Size",test);

		return null;
	}
}