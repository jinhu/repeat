package re.view.ccpp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

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

import re.factor.ccpp.MacroMatcher;

public class IfdefHandler extends AbstractHandler implements ICElementVisitor {

	private ASTRewrite rewrite;
	MacroMatcher snippet = new MacroMatcher();

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
					var removals = new String[]{"OSF","OS390","apollo","SR9","P9070V3R5", "lint"};
					var keeps = new String[]{"SYSV", "SOLAR", "AIX", "HPUX"};
					snippet.macroCleanup(atu, keeps, removals);
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

}