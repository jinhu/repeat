package re.factor.ccpp;

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

public class MacroMatcher {
	public static String TITLE ="Hello Re";
	public GraphFormat graph;
	
	public MacroMatcher() {
		graph = new GraphFormat();
//		graph.toText("c:\\sw-dev\\demo\\yed.graphml");
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
  
