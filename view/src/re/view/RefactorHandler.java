package re.view;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
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
		   var doc = ((TextSelection)selection).getText();
		   var atu = re.use.Helper.getAtu("tmp.c", doc);
		   var juvenator =new re.search.JuvenateVisitor();
		   atu.accept(juvenator);
//		   var structSelection = (IStructuredSelection) selection;
//		   var cu = (ICompilationUnit)structSelection.getFirstElement();
//		   IFile ir = ((IFile)(
//				   ).getResource());
//		   var test = structSelection.toString();
//		   MessageDialog.openInformation( window.getShell(),"File Size",test);

		   		

		   return null;
	}
}