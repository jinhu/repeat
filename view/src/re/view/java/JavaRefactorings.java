package re.view.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;


public class JavaRefactorings extends ASTVisitor {

	public JavaRefactorings() {
		super(true);
	}
	public List<IfStatement> replacements = new ArrayList<>();
	public List<IfStatement> macros = new ArrayList<>();
	List<MethodInvocation> custom = new ArrayList<>();
	Map<String, String> properties = new HashMap<>();

	@Override
	public boolean visit(MethodDeclaration fun) {
		
		if(fun.getName().toString().contains("efactor")){
			importRefactor(fun);
		}else if(fun.getName().toString().contains("clean_up")) {		
			importCleanups(fun);
		}else if(fun.getName().toString().contains("context")) {		
		importContext(fun);
		}
	return super.visit(fun);
}
	@Override
	public boolean visit(SingleVariableDeclaration prop) {
			if(prop.getInitializer()!=null) {
				var value = prop.getInitializer().toString().replace("\"","");
				properties.put(prop.getName().toString(),value);
			}
		
	return super.visit(prop);
}

	private void importCleanups(MethodDeclaration fun) {
		
	}

	private void importContext(MethodDeclaration fun ) {
		for(var node :fun.getBody().statements()) {
			if (node instanceof IfStatement ifStatement) {
				macros.add(ifStatement);
			}
		}
	}
	private void importRefactor(MethodDeclaration fun) {
		for(var node :fun.getBody().statements()) {
			if (node instanceof IfStatement ifStatement) {
				replacements.add(ifStatement);
			}
			else if (node instanceof MethodInvocation stat) {
				custom.add(stat);
			}
		}
	}
	

	public void process(ASTRewrite rewrite, Block code) {
		var snippet = new JavaSnippetMatcher(code, rewrite);
//		ArrayList<Change> changes = new ArrayList<>();
		for (var replacement : replacements) {
			snippet.replace(replacement);
		}

	}

	public String getFileFilter() {
		return properties.get("file_filter");
	}


}