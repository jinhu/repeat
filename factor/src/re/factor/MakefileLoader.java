package re.factor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDefinition;
import org.eclipse.cdt.core.parser.ScannerInfo;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition;

public class MakefileLoader  implements Appl {

    @Override
    public void start(String[] args) {
        try {
            var loader = new MakefileLoader(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   private String makefile;
	private String target;
	private HashMap<String, String> macroDefinitions;
	private ScannerInfo scanInfo;

	public MakefileLoader(String[] args) throws IOException {

        makefile = args[0];
        target = args[1];
        macroDefinitions = loadCompilerMacro();
        var includePath = loadHeaders();
        scanInfo = new ScannerInfo(macroDefinitions, includePath);

        var files = loadMakefile(makefile)
                 .filter(t -> t.trim().startsWith(target))
                 .flatMap(this::lineToFiles);

        var functions =
                files.map(this::fileToAtu)
                .filter(x -> x!=null)
                .flatMap(this::atuToFunctions);

        var funList = functions.collect(Collectors.toList());
        for (var fun : funList){
            System.out.println(fun);
        }
	}
    private IASTTranslationUnit fileToAtu(String filePath) {
        var fullPath = makefile+"/../"+filePath;
        return Helper.getAtu(fullPath);
    }


    private Stream<ICPPASTFunctionDefinition> atuToFunctions(IASTTranslationUnit atu) {
        ArrayList<ICPPASTFunctionDefinition> funcs = new ArrayList<>();
        if(atu==null){
            return funcs.stream();
        }
        var children = atu.getChildren();
        return Arrays.stream(children).filter(node-> (node instanceof CPPASTFunctionDefinition)).map(node-> (ICPPASTFunctionDefinition)node);
    }

    private String[] loadHeaders() {
        return new String[0];
    }
    private Stream<String> loadMakefile(String makefile) throws IOException {
        var content = Files.readString(Path.of(makefile));
        content = content.replaceAll("\\\\\\s*\\n", " ");
        return Arrays.stream(content.split("\n"));
    }

    private Stream<String> lineToFiles(String line) {
        line = line.replaceAll(".*\\+?=", ""); // remove the target part
//        var tokens = line.trim().split("\\s+"); // split the sources
//        return Arrays.stream(tokens);
        return Pattern.compile("\\s+").splitAsStream(line);
    }

    private final HashMap<String, String> loadCompilerMacro() {
        return new HashMap<>();
    }


}
