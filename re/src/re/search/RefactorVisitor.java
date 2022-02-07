package re.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTIfStatement;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;

public class RefactorVisitor extends ASTVisitor {
	public RefactorVisitor() {
		super(true);
	}

	public List<ICPPASTIfStatement> replacements = new ArrayList<>();
	List<ICPPASTFunctionCallExpression> custom = new ArrayList<>();

	@Override
	public int visit(IASTStatement statement) {
		if (statement instanceof ICPPASTIfStatement ifStatement) {
				replacements.add(ifStatement);
		}
		else if (statement instanceof ICPPASTFunctionCallExpression fun) {
			custom.add(fun);
			
		}
		return super.visit(statement);
	}

	public void process(ASTRewrite rewrite, IASTCompoundStatement code) {
		var snippet = new re.use.Snippet(code, rewrite);
		for (var replacement : replacements) {
			snippet.replace(replacement);
		}
	}



    public static String getNode(IASTNode node) {
        StringBuilder output = new StringBuilder();
        if (node != null) {
            processNode(output, "", node);
        }
        return output.toString();
    }


    private static void processNode(StringBuilder output, String indent, IASTNode node) {
        String raw = node.getRawSignature();
        String[] rawLines = raw.split(System.lineSeparator());

        output.append(indent + "(" + node.getClass().getSimpleName() + ", " + node.getFileLocation() + "):");
        if (rawLines.length < 2) {
            output.append(" |" + raw + "|");
        } else {
            for (String line : rawLines) {
                output.append(System.lineSeparator() + indent + "    |" + line + "|");
            }
        }
        output.append(System.lineSeparator());

        for (IASTNode child : node.getChildren()) {
            processNode(output, indent + "  ", child);
        }
    }

}
