package re.view;

import java.io.IOException;
import java.nio.file.StandardOpenOption;

import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICElementVisitor;
import org.eclipse.cdt.internal.core.model.*;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;

import re.port.Reporter; 
public class CommentHandler extends AbstractHandler implements ICElementVisitor {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			CoreModel.getDefault().getCModel().accept(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public boolean visit(ICElement element) throws CoreException {
		if(element instanceof CModel model) {
			System.out.println("   1 1ontext: " +element+" - "+ element.getParent()+" "+ element.getLocationURI());						
		}else if(element instanceof CProject) {
			System.out.println("   2 container: " +element+" : "+element.getElementName()+" - "+element.getElementType()+" - "+ element.getLocationURI());			
		}else if(element instanceof SourceRoot) {
			System.out.println("   3 component: " +element+" : "+element.getElementName()+" - "+element.getElementType()+" - "+ element.getLocationURI());			
		}else if(element instanceof CContainer) {
			System.out.println("   4 CContainer: " +element+" : "+element.getElementName()+" - "+element.getElementType()+" - "+ element.getLocationURI());			
		}else if(element instanceof TranslationUnit tu) {
			var ast  = tu.getAST();
			var rewrite = ASTRewrite.create(ast);
//			var comment = rewrite.getAllComment();
			System.out.println("   4 code: " +element+" : "+element.getElementName()+" - "+element.getElementType()+" - "+ element.getLocationURI());			
		}else if(element instanceof Include) {
			System.out.println("    5 dependency: " +element+" : "+element.getElementName()+" - "+element.getElementType()+" - "+ element.getLocationURI());			
		}else if(element instanceof Function) {
			System.out.println("    4 function: " +element+" : "+element.getElementName()+" - "+element.getElementType()+" - "+ element.getLocationURI());			
		}else if(element instanceof Variable) {
			System.out.println("    5 variable: " +element+" : "+element.getElementName()+" - "+element.getElementType()+" - "+ element.getLocationURI());			
		}else if(element instanceof Macro) {
			System.out.println("    5 macro: " +element+" : "+element.getElementName()+" - "+element.getElementType()+" - "+ element.getLocationURI());			
		}else if(element instanceof FunctionDeclaration fun) {
//				report.appendNode(fun);
			System.out.println("    5 function: " +element+" : "+element.getElementName()+" - "+element.getElementType()+" - "+ element.getLocationURI());
		}else if(element instanceof TypeDef) {
			System.out.println("    5 type: " +element+" : "+element.getElementName()+" - "+element.getElementType()+" - "+ element.getLocationURI());			
		}else if(element instanceof TypeDef) {
			System.out.println("    5 type: " +element+" : "+element.getElementName()+" - "+element.getElementType()+" - "+ element.getLocationURI());			
		}else if(element instanceof Structure) {
			System.out.println("    5 Structure: " +element+" : "+element.getElementName()+" - "+element.getElementType()+" - "+ element.getLocationURI());			
		}else {	
			if(element.toString().equals(".git")){
				System.out.println("found git,skip children");
				return false;
			}
			if(element instanceof Binary) {
				System.out.println("found binary,skip children");
				return false;
			}
			System.out.println(element.getClass());//+" -" +element+" : "+" - "+element.getElementType()+" - "+ element.getLocationURI());
		}

		return true;
	}	
}