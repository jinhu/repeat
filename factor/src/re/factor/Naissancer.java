package re.factor;

import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage;
import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.parser.*;

import re.factor.ccpp.Helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Naissancer implements Appl {
    private List<IASTIfStatement> functionRefactoring= new ArrayList<>(0);
    private List<IASTFunctionCallExpression> fileRefactoring = new ArrayList<>(0);

    @Override
    public void start(String[] args) {

        System.out.println("Renaissance fun");
        loadFilter();

    }
    private IASTTranslationUnit loadFilter() {
        FileContent reader = FileContent.create("", """
                #define message "more description"
                #define file_filter "*.c"
                #define version_control "git"
                #define code_base "c:\\sw-dev\\src"
                #define includes "c:\\sw-dev\\cti\\includes"
                #define write "false"
                #define log "c:\\sw-dev\\cti\\log"
                #define commit_message "auto refactor"
                #define track_changes "c:\\sw-dev\\cti\\changes"
                #define includes "more description"
                int function_refactor(){
                    if(true){
                        THXAtrace($CC, $THXA_TRACE_INT, $__func__, $add);
                    }else{
                        THXXXXAtrace($CC, $add);                        
                    }
                }
                """.toCharArray());
        IncludeFileContentProvider fileCreator = new Helper.EmptyFileContentProvider();
        IIndex index = null;
        int options = 0;
        IParserLogService log = new DefaultLogService();
        var scanInfo = new ScannerInfo(new HashMap<String,String>(0), new String[0]);

        try {
            var filter =  GPPLanguage.getDefault().getASTTranslationUnit(reader, scanInfo, fileCreator, null, 0, log);
            for (var child : filter.getChildren()){
                if(child instanceof ICPPASTFunctionDefinition fun){
                    switch(fun.getDeclarator().getName().toString()) {
                        case "function_refactor":
                            for (var statement : fun.getBody().getChildren()) {
                                if (statement instanceof IASTIfStatement ifstatement) {
                                    functionRefactoring.add(ifstatement);
                                }
                            }
                        case "file_refactor":
                            for (var statement : fun.getBody().getChildren()) {
                                if (statement instanceof IASTFunctionCallExpression functionCall) {
                                    fileRefactoring.add(functionCall);
                                }
                            }
                    }
                }
            }
//            var b = filter.getComments();
//            var c =   filter.getDeclarations();


        } catch (Exception e) {
        }
        return null;
    }
}
