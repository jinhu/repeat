package re.view.java;

import java.util.stream.Stream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jface.text.Document;
import org.eclipse.core.commands.IHandler;

import re.factor.ReformatVisitor;

public class ReformatHandler extends AbstractHandler implements IHandler{

	private ReformatVisitor reformatVisitor = new ReformatVisitor();
	protected IProgressMonitor progressMonitor;
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
        // Get the root of the workspace
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = workspace.getRoot();
        Stream<IProject> projects = Stream.of(root.getProjects()).filter(this::isOpenJavaNaturedProject);
        var packages = projects.flatMap(this::isPackageOwnedByProject);
        var classes = packages.flatMap(this::atusInsidePackage);
        var members = classes.flatMap(this::methodsInClasses);
        members.forEach(this::printIMethodDetails);
		return members;
}
    boolean isOpenJavaNaturedProject(IProject project) {
    	System.out.println(project);
    	try {
			return 	 project.isOpen() && project.isAccessible() && project.isNatureEnabled("org.eclipse.jdt.core.javanature");
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }

    private Stream<IPackageFragment> isPackageOwnedByProject(IProject project) {
    	
    		try {
    	    	var javaProject = JavaCore.create(project);
				return Stream.of( javaProject.getPackageFragments()).filter(p -> {
					try {
						return p.getKind() == IPackageFragmentRoot.K_SOURCE;
					} catch (JavaModelException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return false;
				});
			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    	return null;
    }
    private Stream<ICompilationUnit> atusInsidePackage(IPackageFragment mypackage){
       try {
		return Stream.of(mypackage.getCompilationUnits());
	} catch (JavaModelException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
   }

    private Stream<IType> methodsInClasses(ICompilationUnit unit) {
    	try {
			return Stream.of(unit.getAllTypes());
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }

    private void xmethodsInClasses(ICompilationUnit unit) throws JavaModelException{
        System.out.println("Source file " + unit.getElementName());
        Document doc = new Document(unit.getSource());
        System.out.println("Has number of lines: " + doc.getNumberOfLines());
//        xprintIMethods(unit);
    }

    private void printIMethodDetails(IType type) {
        IMethod[] methods;
		try {
			methods = type.getMethods();
	        for (IMethod method : methods) {

	            System.out.println("Method name " + method.getElementName());
	            System.out.println("Signature " + method.getSignature());
	            System.out.println("Return Type " + method.getReturnType());

	        }

		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
//	private ICElementVisitor visitor = new ICElementVisitor() {
//		@Override
//		public boolean visit(ICElement element) throws CoreException {
//			if(element instanceof ITranslationUnit tu) {
//				var atu = tu.getAST();
//	            if(atu!=null) {
//					var rewrite = ASTRewrite.create(atu);
//					rewrite.removeAllComments(atu);
//
//		    		try {
//		    			var changes = rewrite.rewriteAST();
//		    				changes.perform(progressMonitor);
//	    			} catch (CoreException e) {
//	    				e.printStackTrace();
//	    			}
//				}
//
//			}
//			return true;
//		}
//	};

}