package re.view.java;

import static org.eclipse.jdt.core.dom.AST.JLS8;
import static org.eclipse.jdt.core.dom.ASTParser.newParser;

import java.util.stream.Stream;

import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICElementVisitor;
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
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.core.commands.IHandler;

import re.factor.ReformatVisitor;

public class ReformatHandler extends AbstractHandler implements IHandler{

	private ReformatVisitor reformatVisitor = new ReformatVisitor();
	protected IProgressMonitor progressMonitor;
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
	    IWorkspace workspace = ResourcesPlugin.getWorkspace();
	    IWorkspaceRoot root = workspace.getRoot();
	    Stream<IProject> projects = Stream.of(root.getProjects()).filter(JdtStream::isOpenJavaNaturedProject);
	    var packages = projects.flatMap(JdtStream::isPackageOwnedByProject);
	    var classes = packages.flatMap(JdtStream::atusInsidePackage);
	    var members = classes.flatMap(JdtStream::methodsInClasses);
        members.forEach(this::printIMethodDetails);
        classes.forEach(this::classInfo);
//	    var rewriters = packages.flatMap(JdtStream::atusInsidePackage);

//	    ASTRewrite rewriter = ASTRewrite.create(cu.getAST());
//	        ListRewrite lrw = rewriter.getListRewrite(cu, CompilationUnit.IMPORTS_PROPERTY);
//		    lrw.insertLast(id, null);
//		    TextEdit edits = rewriter.rewriteAST(document, null);
//		    edits.apply(document);
		}
	
//	    assert "import java.util.List;\nimport java.util.Set;\nclass X {}\n".equals(document.get());


Stream<IType> RefactorModel() {
	Document document = new Document();
    ASTParser parser = newParser(AST.JLS17);
    parser.setSource("import java.util.List;\nclass X {}\n".toCharArray());
    parser.setSource(document.get().toCharArray());
    CompilationUnit cu = (CompilationUnit) parser.createAST(null);
    AST ast = cu.getAST();
    ImportDeclaration id = ast.newImportDeclaration();
    id.setName(ast.newName(new String[] {"java", "util", "Set"}));
    return members;
}
    private void classInfo(ICompilationUnit unit){
        Document doc;
		try {
			doc = new Document(unit.getSource());
	        System.out.println("Has number of lines: " + doc.getNumberOfLines());
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	private ICElementVisitor visitor = new ICElementVisitor() {

		@Override
		public boolean visit(ICElement element) throws CoreException {
			if(element instanceof ICompilationUnit tu) {
				var atu = tu.getAST();
	            if(atu!=null) {
					var rewrite = ASTRewrite.create(atu);
					rewrite.removeAllComments(atu);

		    		try {
		    			var changes = rewrite.rewriteAST();
		    				changes.perform(progressMonitor);
	    			} catch (CoreException e) {
	    				e.printStackTrace();
	    			}
				}

			}
			return true;
		}
	}
    }