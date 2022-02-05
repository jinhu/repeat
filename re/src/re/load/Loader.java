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

    private String makefile;
    private String target;
    IIndex index = null;
    int options = 0;
    IParserLogService log = new DefaultLogService();
    private ScannerInfo scanInfo;

    public Loader() {
    }

    @Override
    public void start(String[] args) throws CoreException, IOException {
        var loader = new MakefileLoader(args);
    }



}
