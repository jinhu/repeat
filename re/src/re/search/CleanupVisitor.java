package re.write;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTIfStatement;

public class CleanupVisitor extends ASTVisitor {
	private List<ICPPASTIfStatement> replacements = new ArrayList<>();
	private List<ICPPASTIfStatement> removements= new ArrayList<>();
	private List<ICPPASTFunctionCallExpression> custom = new ArrayList<>();

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
