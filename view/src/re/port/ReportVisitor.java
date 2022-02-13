package re.port;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDefinition;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICElementVisitor;
import org.eclipse.cdt.core.model.ICModel;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration;
import org.eclipse.core.runtime.CoreException;

public class ReportVisitor implements ICElementVisitor {

	private Reporter Reporter;
	
	public ReportVisitor(){
		
    }

	@Override
	public boolean visit(ICElement element) throws CoreException {
		if(element instanceof ITranslationUnit tu) {
			var atu = tu.getAST();
            if(atu!=null) {
        		System.out.println(atu.getFilePath());
        		for(var decle : atu.getChildren()) {
        			System.out.print(decle);
//        			System.out.println(decle.getRawSignature());
        			
        		}
            atu.accept(new ASTVisitor(true) {
                @Override
                public int visit(IASTDeclaration decl) {
                	if (decl instanceof ICPPASTFunctionDefinition fun) {
                		System.out.println("from:"+ fun.getDeclarator().getRawSignature());
                		System.out.println(fun.getBody().getChildren().length);
                	}             
                	else if(decl instanceof CPPASTSimpleDeclaration type) {
                		System.out.println("from:"+ type.getRawSignature());
                		System.out.println(type.getAttributes());                		
                	}
                	return super.visit(decl);
                	
                } });
            
            }   
		}
		return true;
	}

	public void walk(ICModel cModel) {
			try {
				for (var project : cModel.getCProjects()) {
					var roots =project.getAllSourceRoots();
					for (var root : roots) {
						var ice = root.getChildren()[0];
						ice.accept(this);
					}
				}
			} catch (CModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}

