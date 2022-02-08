
package re.search;

import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICElementVisitor;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.BasicMonitor.Printing;
import org.eclipse.ltk.core.refactoring.Change;

/**
 * The activator class controls the plug-in life cycle
 */
public class WorkspaceVisitor  implements ICElementVisitor{
	private IProgressMonitor progressMonitor;
	private Juvenal visitor;
	private ASTRewrite rewrite;

	public void visitWorkspace() throws Exception {
		visitor = new Juvenal( "c:\\sw-dev\\re\\config\\refactor.c");
		progressMonitor = BasicMonitor.toIProgressMonitorWithBlocking(new Printing(System.out));
		
    }

	@Override
	public boolean visit(ICElement element) throws CoreException {
		if(element instanceof ITranslationUnit tu) {
			var atu = tu.getAST();
            rewrite = ASTRewrite.create(atu);
    		visitor.setRewriter(rewrite);
            atu.accept(visitor);
            Change c = rewrite.rewriteAST();
            c.perform(progressMonitor);
		}
		return false;
	}


}
