package re;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import re.use.Appl;

import java.net.URI;

public class Server implements Appl {
    @Override
    public void start(String[] args) throws CoreException {
        System.out.println("Reserve src");

        String name = "UMLet";
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root= workspace.getRoot();
        IProject project= root.getProject(name);
        project.create(null);

        project.open(null);

        CCorePlugin.getDefault().createCDTProject(project.getDescription(), project, null);

        ICProject soproject = CoreModel.getDefault().create(project);

        ITranslationUnit tu = CoreModel.getDefault().createTranslationUnitFrom(CoreModel.getDefault().create(project), (URI)null);

        IASTTranslationUnit ast = CoreModel.getDefault().createTranslationUnitFrom(CoreModel.getDefault().create(project), (URI)null).getAST();

    }
}
