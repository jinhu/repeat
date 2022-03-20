package re.factor;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICElementVisitor;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.BasicMonitor.Printing;


/**
 * The activator class controls the plug-in life cycle
 */
public abstract class CppSourceVisitor  implements ICElementVisitor{
	
	protected ASTRewrite rewrite;
	protected IProgressMonitor progressMonitor;

	public CppSourceVisitor(){
		progressMonitor = BasicMonitor.toIProgressMonitorWithBlocking(new Printing(System.out));
    }

	@Override
	public boolean visit(ICElement element) throws CoreException {
		if(element instanceof ITranslationUnit tu) {
			var atu = tu.getAST();
            if(atu!=null) {
            	porcessfile(atu);	
			}
		}
		return true;
	}

	public abstract void porcessfile(IASTTranslationUnit atu) ;
}
