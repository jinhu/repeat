package re.view;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.cdt.core.dom.ast.IASTComment;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorElseStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorEndifStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIfdefStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIfndefStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICElementVisitor;
import org.eclipse.cdt.internal.core.dom.rewrite.commenthandler.ASTCommenter;
import org.eclipse.cdt.internal.core.model.Archive;
import org.eclipse.cdt.internal.core.model.Binary;
import org.eclipse.cdt.internal.core.model.BinaryFunction;
import org.eclipse.cdt.internal.core.model.BinaryModule;
import org.eclipse.cdt.internal.core.model.BinaryVariable;
import org.eclipse.cdt.internal.core.model.CContainer;
import org.eclipse.cdt.internal.core.model.CProject;
import org.eclipse.cdt.internal.core.model.Field;
import org.eclipse.cdt.internal.core.model.Function;
import org.eclipse.cdt.internal.core.model.FunctionDeclaration;
import org.eclipse.cdt.internal.core.model.Include;
import org.eclipse.cdt.internal.core.model.Macro;
import org.eclipse.cdt.internal.core.model.Structure;
import org.eclipse.cdt.internal.core.model.TranslationUnit;
import org.eclipse.cdt.internal.core.model.TypeDef;
import org.eclipse.cdt.internal.core.model.Variable;
import org.eclipse.cdt.internal.core.model.VariableDeclaration;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;

public class CommentHandler extends AbstractHandler implements ICElementVisitor {

	private ASTRewrite rewrite;
	private Map<String, String> replaceText= Map.of();

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			CoreModel.getDefault().getCModel().accept(this);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean visit(ICElement element) throws CoreException {
		if(element instanceof TranslationUnit tu) {
			
			var atu = tu.getAST();
            if(atu!=null) { // && tu.getFile().toString().contains("/memo/memmain.c")) {
        		rewrite = ASTRewrite.create(atu);
        		try {
					formatComment(atu);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
            return false;
		}
		else if(element instanceof FunctionDeclaration
				||element instanceof Function				
				||element instanceof Structure				
				||element instanceof Variable				
				||element instanceof Archive				
				||element instanceof Include				
				||element instanceof TypeDef				
				||element instanceof Binary				
				||element instanceof CContainer				
				||element instanceof Macro				
				||element instanceof VariableDeclaration				
				||element instanceof BinaryFunction				
				||element instanceof BinaryModule				
				||element instanceof BinaryVariable				
				||element instanceof CProject				
				||element instanceof Field) {
//			return false;
		}else {
			System.out.println("potential "+element.getClass()+" : "+element.toString());
		}
	return true;
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