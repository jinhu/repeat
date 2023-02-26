package re.view.ccpp;

import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICElementVisitor;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import re.factor.ReformatVisitor;

public class ReformatHandler extends AbstractHandler {

	private ReformatVisitor reformatVisitor = new ReformatVisitor();
	protected IProgressMonitor progressMonitor;
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			CoreModel.getDefault().getCModel().accept(visitor );
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private ICElementVisitor visitor = new ICElementVisitor() {
		@Override
		public boolean visit(ICElement element) throws CoreException {
			if(element instanceof ITranslationUnit tu) {
				var atu = tu.getAST();
	            if(atu!=null) {
					var rewrite = ASTRewrite.create(atu);
					rewrite.removeAllComments(atu);

		    		try {
		    			var changes = rewrite.rewriteAST();
		    				changes.perform(progressMonitor);
	    			} catch (CoreException e) {
	    				e.printStackTrace();
	    			}
				}

			}
			return true;
		}
	};

}