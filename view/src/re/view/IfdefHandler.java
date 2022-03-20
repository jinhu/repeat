package re.view;

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

public class IfdefHandler extends AbstractHandler implements ICElementVisitor {

	private ASTRewrite rewrite;

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
					macroCleanup(atu, keeps, removals);
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

    public void macroCleanup(IASTTranslationUnit tu, String[] macrosToKeep, String[] macrotoRemove) throws IOException {
    	var path = Path.of(tu.getFilePath());
        var lines = Files.readAllLines(path).toArray(new String[0]);    	
        var mac = Stream.of(tu.getAllPreprocessorStatements())
        		.filter(m->m.getFileLocation().getFileName().equals(tu.getFilePath())).toArray();
        var changed = false; 
		
        for(var macroName: macrosToKeep) {
        	changed |= simplifyIfdef(lines, mac, macroName, false);
    	}
		
        for(var macroName: macrotoRemove) {
        	changed |=simplifyIfdef(lines, mac, macroName, true);
    	}
		
        if(changed) {
        	Files.writeString(path, String.join("\n", lines));
        	System.out.println("updated "+tu.getFilePath());   
        }

    }
    public boolean simplifyIfdef(String[] lines, Object[] stmts, String macroName, boolean remove) throws IOException {
        int start =0;
        int middle = 0;
        int end = 0 ;
        int startDepth =-2;
        int defCount =0;

        var isNotDef = false;
        boolean changed =false;
		for(var stmt: stmts) {
            if (stmt instanceof IASTPreprocessorIfdefStatement ifdef){
            	if(ifdef.getMacroReference() != null
    			&& ifdef.getMacroReference().isReference() 
        		&& ifdef.getMacroReference().getRawSignature().equals(macroName)) {
					start = ifdef.getFileLocation().getStartingLineNumber();
					startDepth = defCount;
					isNotDef = false;
				}
        		defCount++;
            }else if(stmt instanceof IASTPreprocessorIfndefStatement ifndef) {
            	if(ifndef.getMacroReference() != null
    			&& ifndef.getMacroReference().isReference() 
        		&& ifndef.getMacroReference().getRawSignature().equals(macroName)) {
					start = ifndef.getFileLocation().getStartingLineNumber();
					startDepth = defCount;
					isNotDef = true;
				}
        		defCount++;
            }else if(stmt instanceof IASTPreprocessorIfStatement ifstmt) {
            	if(ifstmt.getCondition().toString().equals(macroName)) {
					start = ifstmt.getFileLocation().getStartingLineNumber();
					startDepth = defCount;
				}
        		defCount++;
            }else if(stmt instanceof IASTPreprocessorElseStatement elseif) {
            	if (defCount == startDepth+1) {
                    middle = elseif.getFileLocation().getStartingLineNumber();
            	
            	}
            }
            else if (stmt instanceof IASTPreprocessorEndifStatement endif) {
                defCount--;
                if (defCount == startDepth && startDepth >= 0) {
                	
                    end = endif.getFileLocation().getStartingLineNumber();
                    if(isNotDef==remove) {
                        if(middle==0) {
                        	lines[start-1]="";
                        	lines[end-1]="";
                        }else {
                        	lines[start-1]="";
                	        for(int i =middle;i<= end; i++) {
                	        	lines[i-1]="";        	
                	        }   
                        	
                        }
                    }else {
                    	if(middle==0) {
                    	    for(int i =start;i<= end; i++) {
                	        	lines[i-1]="";        	
                	        }   
                    	}else {
                    	    for(int i =start;i<= middle; i++) {
                	        	lines[i-1]="";        	
                    	    }
                    		lines[end-1]="";        
                    	}
                    }
                    start=0;
                    middle = 0;
                    end=0;
                    startDepth= -2;
                    isNotDef = false;
                    changed = true;
                }
                
            }
        }
		return changed;
    }

}