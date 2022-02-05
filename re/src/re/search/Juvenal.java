package re.write;

import org.eclipse.cdt.core.CProjectNature;
import org.eclipse.cdt.core.dom.ast.*;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ltk.core.refactoring.Change;

import re.use.Appl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Juvenal implements Appl {
    private IASTTranslationUnit model;
	private ASTVisitor visitor;
    public Juvenal(String[] args) {
        model = re.use.Helper.getAtu(args[0]);
        model.accept(visitor);        		
	}
    
	@Override
    public void start(String[] args) throws CoreException {
    	System.out.println("Rejuvenate src");
        model = re.use.Helper.getAtu("c:\\sw-dev\\whars\\litho\\components\\WHAV\\com\\int\\bin\\WHAVPECH_main.c");
        visitor =new RefactorVisitor();
    }
	
    public static void main(String[] args) throws IOException {
    	var juvenal = new Juvenal(args);
    }
    
    


}