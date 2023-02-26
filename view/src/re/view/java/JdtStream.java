package re.view.java;

import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.Document;

public  class JdtStream {
    static boolean isOpenJavaNaturedProject(IProject project) {
    	System.out.println(project);
    	try {
			return 	 project.isOpen() && project.isAccessible() && project.isNatureEnabled("org.eclipse.jdt.core.javanature");
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }

   static  Stream<IPackageFragment> isPackageOwnedByProject(IProject project) {
    	
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
    static  Stream<ICompilationUnit> atusInsidePackage(IPackageFragment mypackage){
       try {
		return Stream.of(mypackage.getCompilationUnits());
	} catch (JavaModelException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
   }

    static Stream<IType> methodsInClasses(ICompilationUnit unit) {
    	try {
			return Stream.of(unit.getAllTypes());
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
}
