package re.search;

import org.eclipse.cdt.core.dom.ast.*;
import org.eclipse.core.runtime.CoreException;

import re.use.Appl;

import java.io.IOException;

public class Juvenal implements Appl {
    private IASTTranslationUnit model;
	private ASTVisitor visitor;
    public Juvenal() {
    		}
    
	@Override
    public void start(String[] args) throws CoreException {
    	System.out.println("Rejuvenate src");
        model = re.use.Helper.getAtu("c:\\sw-dev\\whars\\java\\filter-dsls\\removeMarkSensorCleanup.c");
        visitor =new RefactorVisitor();
        model.accept(visitor);
    }
	
    public static void main(String[] args) throws IOException {
    	var juvenal = new Juvenal();
    }
    
    


}