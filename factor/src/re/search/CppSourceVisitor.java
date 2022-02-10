
package re.search;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompoundStatement;
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
public class CppSourceVisitor  implements ICElementVisitor{
	private IProgressMonitor progressMonitor;
	private ASTRewrite rewrite;
	private Refactorings refactor;
	
	public void visitWorkspace() throws Exception {
		progressMonitor = BasicMonitor.toIProgressMonitorWithBlocking(new Printing(System.out));
		
    }

	@Override
	public boolean visit(ICElement element) throws CoreException {
		if(element instanceof ITranslationUnit tu) {
			var atu = tu.getAST();
            rewrite = ASTRewrite.create(atu);
            
            atu.accept(new ASTVisitor() {
                @Override
                public int visit(IASTStatement statement) {
                	if (statement instanceof ICPPASTCompoundStatement block) {
                    	var changes = refactor.process(rewrite,block);
                        try {
                        	for(var change : changes) {
                        		change.perform(progressMonitor);
                        	}
            			} catch (CoreException e) {
            				// TODO Auto-generated catch block
            				e.printStackTrace();
            			}

            		}
                	return super.visit(statement);
                	
                }
});
            
            
		}
		return false;
	}

	public void setRefactorings(Refactorings refactor) {
		this.refactor = refactor; 
	}


}
