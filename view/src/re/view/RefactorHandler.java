package re.view;

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

import re.search.RefactorVisitor;
import re.search.Refactorings;
import re.use.Helper;

public class RefactorHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		var window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		var selectionService = window.getSelectionService();
		IWorkbenchPart workbenchPart = window.getActivePage().getActivePart(); 
		IFile file = (IFile) workbenchPart.getSite().getPage().getActiveEditor().getEditorInput().getAdapter(IFile.class);
		if (file == null) return null;
		var atu = Helper.getAtu(file.getRawLocation().toString());

		var selection = selectionService.getSelection();
		var refactorings = new Refactorings();
		atu.accept(refactorings);

		var visitor = new RefactorVisitor();
		visitor.setRefactorings(refactorings);
		
		try {
			CoreModel.getDefault().getCModel().accept(visitor);
		} catch (CoreException e) {
			e.printStackTrace();
		}

		return null;
	}
}