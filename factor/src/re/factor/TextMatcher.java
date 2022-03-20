package re.factor;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTIfStatement;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.ltk.core.refactoring.Change;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextMatcher extends ASTVisitor{
    private final ASTRewrite rewrite;
    private String text;
    private HashMap<String, String> wildCards = new HashMap<>();
    private HashMap<String, List<IASTNode>> placeholders = new HashMap<>();
    static private int[] notFound = new int[0];
    ArrayList<IASTNode> wildcard = null;


    public TextMatcher(IASTTranslationUnit atu, ASTRewrite aRewrite) {
        text = atu.getRawSignature();
        rewrite = aRewrite;
    }

    @Override
    public int visit(IASTTranslationUnit tu) {
		text =text.replaceAll("#ifdef\s+MSC", "#ifdef 0");
		try {
			java.nio.file.Files.write(Path.of(tu.getContainingFilename()), text.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return 0;
    }
    

}
