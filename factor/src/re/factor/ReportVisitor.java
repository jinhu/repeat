package re.factor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDefinition;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration;

public class ReportVisitor extends CppSourceVisitor {
	private Path graphml = Path.of("c:\\sw-dev\\result.graphml");
	private String currentFun;

	public void start() throws IOException {
		Files.writeString(graphml, report.header);
	}
	public void finish() throws IOException {
		Files.writeString(graphml, report.header,StandardOpenOption.APPEND);
	}

	private Reporter report = new Reporter();
	private ASTVisitor relationVisitor = new ASTVisitor(true) {

		@Override
		public int visit(IASTDeclaration decl) {
			try {
				if (decl instanceof ICPPASTFunctionDefinition fun) {
					Files.writeString(graphml, report.reportContainment(fun.getDeclarator()),StandardOpenOption.APPEND);
					currentFun = fun.getDeclarator().getRawSignature();
					fun.accept(statementVisior);
				} else if (decl instanceof CPPASTSimpleDeclaration type) {
					for(var declarator: type.getDeclarators()) {
						Files.writeString(graphml, report.reportContainment(declarator),StandardOpenOption.APPEND);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return super.visit(decl);
		}
	};

	private ASTVisitor statementVisior = new ASTVisitor(true) {

		@Override
		public int visit(IASTStatement decl) {
			if (decl instanceof ICPPASTFunctionCallExpression fun) {

				try {
					Files.writeString(graphml, report.reportRelation(currentFun, fun),StandardOpenOption.APPEND);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (decl instanceof CPPASTSimpleDeclaration type) {
				System.out.println("from:" + type.getRawSignature());
				System.out.println(type.getAttributes());
			}
			return super.visit(decl);

		}
	};

	private String currentFile;

	@Override
	public void porcessfile(IASTTranslationUnit atu) {
		currentFile = atu.getFilePath();
		atu.accept(relationVisitor);
	}

}
