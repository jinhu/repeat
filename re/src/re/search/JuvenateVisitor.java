package re.search;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDefinition;

public class JuvenateVisitor extends ASTVisitor {

	public JuvenateVisitor() {
		super(true);
	}
	
	CleanupVisitor cleanup = new CleanupVisitor();
	RefactorVisitor refactor = new RefactorVisitor();

	@Override
	public int visit(IASTDeclaration declaration) {
		
		if (declaration instanceof ICPPASTFunctionDefinition fun) {
			if(isRefactor(fun)) {
				fun.accept(refactor);
			}else if(isCleanup(fun)) {
				fun.accept(cleanup);
			}
		} 
		return super.visit(declaration);
	}

	private boolean isCleanup(ICPPASTFunctionDefinition fun) {
		
		return fun.getDeclarator().getName().toString().contains("clean_up");
	}

	private boolean isRefactor(ICPPASTFunctionDefinition fun) {
		return fun.getDeclarator().getName().toString().contains("efactor");
	}
	
	
}