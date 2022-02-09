package re.load;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDefinition;
import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.parser.DefaultLogService;
import org.eclipse.cdt.core.parser.IParserLogService;
import org.eclipse.cdt.core.parser.ScannerInfo;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition;
import org.eclipse.core.runtime.CoreException;

import re.use.Appl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Loader implements Appl {

    @Override
    public void start(String[] args) {
        try {
            var loader = new MakefileLoader(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
