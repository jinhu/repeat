
package re.factor;

import org.eclipse.cdt.core.CProjectNature;
import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.cdt.core.model.*;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.BasicMonitor.Printing;
import org.eclipse.ltk.core.refactoring.Change;
import org.osgi.framework.BundleContext;

import re.search.Juvenal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * The activator class controls the plug-in life cycle
 */
public class WorkspaceVisitor  {

    private ICModel cModel;
	private IProgressMonitor progressMonitor;
	private Juvenal visitor;
	private ASTRewrite rewrite;
	private String refactor;

	public void visitWorkspace() throws Exception {
		visitor = new Juvenal( "c:\\sw-dev\\re\\config\\refactor.c");
		cModel = CoreModel.getDefault().getCModel();
        progressMonitor = BasicMonitor.toIProgressMonitorWithBlocking(new Printing(System.out));
        var projects = Arrays.stream(cModel.getCProjects());
        projects.forEach(this::visitProject);
		
    }

    private void visitProject(ICProject project) {
        try {
            var sources = Arrays.stream(project.getAllSourceRoots());
            sources.forEach(this::visitSource);
        } catch (CModelException e) {
            e.printStackTrace();
        }

    }

    private void visitSource(ISourceRoot source) {
        try {
            var atu = getAtuFromSource(source);
            rewrite = ASTRewrite.create(atu);
    		visitor.setRewriter(rewrite);
            atu.accept(visitor);
            Change c = rewrite.rewriteAST();
            c.perform(progressMonitor);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }

	private IASTTranslationUnit getAtuFromSource(ISourceRoot source) throws CModelException, CoreException {
		var ice = source.getChildren()[0];
		var tu = (ITranslationUnit)ice;
		var atu = tu.getAST();
		return atu;
	}

}
