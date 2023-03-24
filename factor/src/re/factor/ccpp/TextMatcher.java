package re.factor.ccpp;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTIfStatement;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.ltk.core.refactoring.Change;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextMatcher{
    private String text;

	public TextMatcher(IASTTranslationUnit atu) {
        text = atu.getRawSignature();
    }

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
	String replaceText(String source, Map<String, String> modifications) {
		for(var mod : modifications.entrySet()) {
			source = source.replaceAll(mod.getKey(), mod.getValue());
			}
		return source;
	}
    @SuppressWarnings("restriction")
	public void formatComment(IASTTranslationUnit tu) throws IOException {
    	System.out.println(tu.getFilePath());
        var rewrite = ASTRewrite.create(tu);
        var factory = tu.getASTNodeFactory();
        if(tu.getComments().length >0) {
        
       
    	var comment = tu.getComments()[0];
    	var text = comment.getRawSignature();
    	
        text = text.replaceAll(";\n;\\s+\\(c\\) .*\\d+\n;\\s+All rights reserved.\n;\n", " (c) Example B.V. 2022   All rights reserved.\n");
    	text = text.replaceAll(";  FUNCTIONAL DESCRIPTION.\\n;  (.*)", "@brief $1");
		text = text.replaceAll(";  FORMAL PARAMETERS:", "");
		text = text.replaceAll(";  CALLING SEQUENCE:", "@sequence");
		text = text.replaceAll(";  PRECONDITIONS:\\n;  (.*)", "@preconditions $1");
		text = text.replaceAll(";  KNOWN LIMITATIONS:\\n;  (.*)", "@limitations $1");
		text = text.replaceAll(";  (\\S+)(    |	-	)(.*)", "@param $1\t$2");
		text = text.replaceAll(";--|;\\+\\+|;\\s*\\n|; \\$Id.*\\$|; \\$Log.*\\$", "\n");
		text = text.replaceAll(" . Revision \\S*  (\\S*)  \\S*  (\\w*).*\\n .(.*)\\n .(.*)", "#$1\t$2\t$3$4");
		text = text.replaceAll("(\\n\\s*)+", "\n");
		
//        text =replaceText(text , replaceText);
		if(!text.equals(comment.getRawSignature())) {
		System.out.println(text);
		var path = Path.of(tu.getFilePath());
		var source = Files.readString(path);
		source = source.replace(comment.getRawSignature(),text);
		Files.write(path,source.getBytes());
		}

        }
    }

}
