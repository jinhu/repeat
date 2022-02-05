package re;


import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.BasicMonitor.Printing;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class Actor implements BundleActivator {
    private boolean codeServiceStarted;

    @Override
    public void start(BundleContext context) throws Exception {
        initializeProjects("c:\\sw-dev\\demo");
//        CDTIndexerDatabase.testQueries();
    }


    @Override
    public void stop(BundleContext context) throws Exception {
    }

    //    @Override
//    public void earlyStartup() {
//    }
    static final Predicate<? super Path> PROJECT_FILES_FILTER = p -> p.toString().matches(".*[\\\\/]\\.project");
    private static final int MAX_WAIT_TIME_INDEXING = (int) TimeUnit.MINUTES.toMillis(10);

    public static void initializeProjects(String codebase) throws IOException {
        enableAutoBuilding();
        var indexManager = CCorePlugin.getIndexManager();
        try (var files = Files.walk(Path.of(codebase))) {
            var projects = files.filter(PROJECT_FILES_FILTER);
            projects.forEach(f -> {
                try {
                    var coreModel = CoreModel.getDefault();
                    var cModel = coreModel.getCModel();
                    var workspace = cModel.getWorkspace();
                    var projectDescription = workspace
                            .loadProjectDescription(org.eclipse.core.runtime.Path.fromOSString(f.toString()));
                    var projectName = projectDescription.getName();
                    var pm = BasicMonitor.toIProgressMonitorWithBlocking(new Printing(System.out));
                    var projectCreated = false;
                    if (!projectExists(projectName)) {
                        projectCreated = true;
                        var project = workspace.getRoot().getProject(projectName);
                        project.create(projectDescription, pm);
                    } else {
                        System.out.println("Using existing project " + projectName);
                    }
                    var project = workspace.getRoot().getProject(projectName);
                    project.open(pm);
//					project.build(IncrementalProjectBuilder.INCREMENTAL_BUILD, pm);
                    var cp = CCorePlugin.getDefault().getCoreModel().getCModel().getCProject(projectName);
                    if (!cp.isOpen()) {
                        try {
                            cp.open(pm);
                        } catch (Exception e) {
                            // retry the second time it works
                            cp.open(pm);
                        }
                    }
                    if (projectCreated || !indexManager.isProjectIndexed(cp) ){ //|| !indexManager.isProjectContentSynced(cp)) {
//						indexManager.reindex(cp);
//						indexManager.joinIndexer(MAX_WAIT_TIME_INDEXING, pm);
                    }
                    project.refreshLocal(Integer.MAX_VALUE, new NullProgressMonitor() {
                        @Override
                        public void done() {
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

    }


    private static void enableAutoBuilding() {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        if (!workspace.isAutoBuilding()) {
            try {
                IWorkspaceDescription desc = workspace.getDescription();
                desc.setAutoBuilding(true);
                workspace.setDescription(desc);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean projectExists(String projectName) {
        var projects = CoreModel.getDefault().getCModel().getWorkspace().getRoot().getProjects();
        for (IProject project : projects) {
            if (project.getName().equals(projectName)) {
                return true;
            }
        }
        return false;
    }

}


