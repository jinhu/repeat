package re.search;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTIfStatement;

public class RefactorVisitor extends ASTVisitor {

	public List<ICPPASTIfStatement> replacements = new ArrayList<>();
	List<ICPPASTIfStatement> removements= new ArrayList<>();
	List<ICPPASTFunctionCallExpression> custom = new ArrayList<>();

	public RefactorVisitor() {
		this.shouldVisitTranslationUnit =true;
		this.shouldVisitDeclarations =true;
	}

	@Override
	public int visit(IASTStatement statement) {
		if (statement instanceof ICPPASTIfStatement ifStatement) {
			if(ifStatement.getElseClause()!=null) {
				replacements.add(ifStatement);
			}else {
				removements.add(ifStatement);
			}
		}
		else if (statement instanceof ICPPASTFunctionCallExpression fun) {
			custom.add(fun);
			
		}
		return super.visit(statement);
	}
}
