package re.search;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDeclarator;

public class JuvenateVisitor extends ASTVisitor {

	ASTVisitor cleanup = new CleanupVisitor();
	ASTVisitor refactor = new RefactorVisitor();

	@Override
	public int visit(IASTDeclaration declaration) {

		if (declaration instanceof ICPPASTFunctionDeclarator fun) {
			if(isRefactor(fun)) {
				fun.accept(refactor);
			}else if(isCleanup(fun)) {
				fun.accept(cleanup);
			}
		}
		return super.visit(declaration);
	}

	private boolean isCleanup(ICPPASTFunctionDeclarator fun) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isRefactor(ICPPASTFunctionDeclarator fun) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}