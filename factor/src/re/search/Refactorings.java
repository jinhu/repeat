package re.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTInitializer;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTIfStatement;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTEqualsInitializer;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration;
import org.eclipse.ltk.core.refactoring.Change;

public class Refactorings extends ASTVisitor {

	public Refactorings() {
		super(true);
	}
	public List<ICPPASTIfStatement> replacements = new ArrayList<>();
	public List<ICPPASTIfStatement> macros = new ArrayList<>();
	List<ICPPASTFunctionCallExpression> custom = new ArrayList<>();
	Map<String, String> properties = new HashMap<>();

	@Override
	public int visit(IASTDeclaration declaration) {
		
		if (declaration instanceof ICPPASTFunctionDefinition fun) {
			if(fun.getDeclarator().getName().toString().contains("refactor")){
				importRefactor(fun);
			}else if(fun.getDeclarator().getName().toString().contains("clean_up")) {		
				importCleanups(fun);
			}else if(fun.getDeclarator().getName().toString().contains("context")) {		
			importContext(fun);
			}
		} 
		else if (declaration instanceof CPPASTSimpleDeclaration prop) {
			for(var p :prop.getDeclarators()) {
				if(p.getInitializer()!=null) {
					var value = ((CPPASTEqualsInitializer)p.getInitializer()).getInitializerClause().getRawSignature().replace("\"","");
					properties.put(p.getName().toString(),value);
				}
			}
		}
		return super.visit(declaration);
	}

	private void importCleanups(ICPPASTFunctionDefinition fun) {
		
	}

	private void importContext(ICPPASTFunctionDefinition fun) {
		for(var node :fun.getBody().getChildren()) {
			if (node instanceof ICPPASTIfStatement ifStatement) {
				macros.add(ifStatement);
			}
		}
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

	public String getFileFilter() {
		// TODO Auto-generated method stub
		return properties.get("file_filter");
	}


}