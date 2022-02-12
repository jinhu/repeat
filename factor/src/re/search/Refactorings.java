package re.search;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTIfStatement;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.ltk.core.refactoring.Change;

public class Refactorings extends ASTVisitor {

	public Refactorings() {
		super(true);
	}
	public List<ICPPASTIfStatement> replacements = new ArrayList<>();
	List<ICPPASTFunctionCallExpression> custom = new ArrayList<>();

	@Override
	public int visit(IASTDeclaration declaration) {
		
		if (declaration instanceof ICPPASTFunctionDefinition fun) {
			if(fun.getDeclarator().getName().toString().contains("efactor")){
				importRefactor(fun);
			}else if(fun.getDeclarator().getName().toString().contains("clean_up")) {		
				importCleanups(fun);
			}
		} 
		return super.visit(declaration);
	}

	private void importCleanups(ICPPASTFunctionDefinition fun) {
		
	}

	private void importRefactor(ICPPASTFunctionDefinition fun) {
		for(var node :fun.getBody().getChildren()) {
			if (node instanceof ICPPASTIfStatement ifStatement) {
				replacements.add(ifStatement);
			}
			else if (node instanceof ICPPASTFunctionCallExpression stat) {
				custom.add(stat);
			}
		}
	}
	

	public void process(ASTRewrite rewrite, IASTCompoundStatement code) {
		var snippet = new SnippetMatcher(code, rewrite);
		ArrayList<Change> changes = new ArrayList<>();
		for (var replacement : replacements) {
			snippet.replace(replacement);
		}

	}


}