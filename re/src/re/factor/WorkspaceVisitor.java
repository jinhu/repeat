
package re.factor;

import java.util.Arrays;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICElementVisitor;
import org.eclipse.cdt.core.model.ICModel;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.model.ISourceRoot;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.BasicMonitor.Printing;
import org.eclipse.ltk.core.refactoring.Change;

import re.search.Juvenal;

/**
 * The activator class controls the plug-in life cycle
 */
public class WorkspaceVisitor  implements ICElementVisitor{

    private ICModel cModel;
	private IProgressMonitor progressMonitor;
	private Juvenal visitor;
	private ASTRewrite rewrite;
	private String refactor;

	public void visitWorkspace() throws Exception {
		visitor = new Juvenal( "c:\\sw-dev\\re\\config\\refactor.c");
		cModel = CoreModel.getDefault().getCModel();
		progressMonitor = BasicMonitor.toIProgressMonitorWithBlocking(new Printing(System.out));
		cModel.accept(this);
		
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
