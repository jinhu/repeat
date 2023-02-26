package re.view.java;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;


public class ReportHandler extends AbstractHandler {


@Override
public Object execute(ExecutionEvent event) throws ExecutionException {
    List<IJavaProject> projectList = new LinkedList<IJavaProject>();
    try {
       IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
       IProject[] projects = workspaceRoot.getProjects();
       for(int i = 0; i < projects.length; i++) {
          IProject project = projects[i];
          if(project.isOpen() && project.hasNature(JavaCore.NATURE_ID)) {
             projectList.add(JavaCore.create(project));
          }
       }
    }
    catch(CoreException ce) {
       ce.printStackTrace();
    }
    return projectList;
}
}