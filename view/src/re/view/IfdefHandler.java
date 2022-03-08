package re.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorElifStatement;
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
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import re.search.RefactorVisitor;
import re.search.Refactorings;
import re.search.TextMatcher;
import re.use.Helper;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

public class IfdefHandler extends AbstractHandler implements ICElementVisitor {

	private ASTRewrite rewrite;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
//		var window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
//		var selectionService = window.getSelectionService();
//		IWorkbenchPart workbenchPart = window.getActivePage().getActivePart(); 
//		IFile file = (IFile) workbenchPart.getSite().getPage().getActiveEditor().getEditorInput().getAdapter(IFile.class);
//		if (file == null) return null;
//		var atu = Helper.getAtu(file.getRawLocation().toString());
//
//		var selection = selectionService.getSelection();
//		var refactorings = new Refactorings();
//		atu.accept(refactorings);
//
//		var visitor = new RefactorVisitor();
//		visitor.setRefactorings(refactorings);
		
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
			System.out.println("found "+tu.getFile().toString());
			var atu = tu.getAST();
            if(atu!=null) {
        		rewrite = ASTRewrite.create(atu);
        		try {
					remove(atu,"OSF");
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


    public void remove(IASTTranslationUnit tu, String macroName) throws IOException {
        int start =0;
        int middle = 0;
        int end = 0 ;
        int startDepth =-2;
        int defCount =0;

    	var path = Path.of(tu.getFilePath());
        var lines1 = Files.readAllLines(path);
        String[] lines =  lines1.toArray(new String[0]);
        
        boolean changed =false;
		for (var stmt : tu.getAllPreprocessorStatements()) {
        	if(!tu.getFilePath().contains(stmt.getFileLocation().getFileName())) continue;
            if (stmt instanceof IASTPreprocessorIfdefStatement
            ||  stmt instanceof IASTPreprocessorIfndefStatement
            ||  stmt instanceof IASTPreprocessorIfStatement) {
            	var ifdef =(IASTPreprocessorIfdefStatement)stmt;
            		if (ifdef!=null && ifdef.getMacroReference() != null
    				&&ifdef.getMacroReference().isReference() &&ifdef.getMacroReference().getRawSignature().equals(macroName)) {
            					start = ifdef.getFileLocation().getStartingLineNumber();
            					startDepth = defCount;
    				}
        		defCount++;
            }else if(stmt instanceof IASTPreprocessorElseStatement elseif) {
            	if (defCount == startDepth) {
                    middle = elseif.getFileLocation().getStartingLineNumber();
            	
            	}
            }
            else if (stmt instanceof IASTPreprocessorEndifStatement endif) {
                defCount--;
                if (defCount == startDepth && startDepth > 0) {
                    end = endif.getFileLocation().getStartingLineNumber();
                    if(middle==0) {
                    	middle =end;
                    }
        	        if(middle !=end) {
        	        	lines[end-1]="";        	
        	        }

        	        for(int i =middle-1;i>= start-1; i--) {
        	        	lines[i]="";        	
        	        }   
        	        changed =true;
                }
                
            }
        }
        if(changed) {
        	Files.writeString(path, String.join("\n", lines));
        	System.out.println("updated "+tu.getFilePath());   
        }
    }
}