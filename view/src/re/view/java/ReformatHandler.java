package re.view.java;

import static org.eclipse.jdt.core.dom.ASTParser.newParser;

import java.util.stream.Stream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEditGroup;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

import re.factor.Helper;
import re.factor.ReformatVisitor;

public class ReformatHandler extends AbstractHandler implements IHandler{

	private ReformatVisitor reformatVisitor = new ReformatVisitor();
	protected IProgressMonitor progressMonitor;
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		var refactoring = getRefactoring(event);
	    IWorkspace workspace = ResourcesPlugin.getWorkspace();
	    IWorkspaceRoot root = workspace.getRoot();
	    var project=root.getProject("java-code-from-last-milinium");
	    var proj = JavaCore.create(project);
	    Stream<IProject> projects = Stream.of(root.getProjects()).filter(JdtStream::isOpenJavaNaturedProject);
	    var packages = projects.flatMap(JdtStream::isPackageOwnedByProject);
	    var classes = packages.flatMap(JdtStream::atusInsidePackage);
	    var members = classes.flatMap(JdtStream::methodsInClasses);
//        members.forEach(this::printIMethodDetails);
//        classes.forEach(this::renameClass);
//	    var rewriters = packages.flatMap(JdtStream::atusInsidePackage);
	    members.forEach(this::renameClass);
//      
		return members;

		}
	
//	    assert "import java.util.List;\nimport java.util.Set;\nclass X {}\n".equals(document.get());


	private ASTVisitor getRefactoring(ExecutionEvent event) throws ExecutionException {
		var window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		var selectionService = window.getSelectionService();
//		IWorkbenchPart workbenchPart = window.getActivePage().getActivePart(); 
//		IEditorInput text = workbenchPart.getSite().getPage().getActiveEditor().getEditorInput();
	    ASTParser parser = newParser(AST.JLS17);
//		var file = (IClassFile) workbenchPart.getSite().getPage().getActiveEditor().getEditorInput().getAdapter(IFile.class);
//		if (file == null) return null;
	    parser.setSource( selectionService.getSelection().toString().toCharArray());


//        parser.setResolveBindings(true);
	    var atu =parser.createAST(null);
		var refactorings = new JavaRefactorings();
		atu.accept(refactorings);
		return refactorings;
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
	        	if(method.getElementName().contains("_")) {
	        		System.out.println("Method name " + method.getElementName());
//	        		System.out.println("Signature " + method.getSignature());
//	        		System.out.println("Return Type " + method.getReturnType());
	        	}
	        }

		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	private void renameClass(IType node) {
    	
	    AST ast = node.getCompilationUnit().re;
		ASTRewrite rewriter = ASTRewrite.create(ast);
	    
		SimpleName newClassName = ast.newSimpleName("TestBundle");//(IJavaElement) node).getElementName());
		TextEditGroup edit = new TextEditGroup("ref");
		rewriter.replace((ASTNode) node, newClassName, edit);

//	    lrw.insertLast(id, null);
//	    TextEdit edits = rewriter.rewriteAST(document, null);
//		rewriter.rewriteAST();
	    //edits.apply(document);

	}

}