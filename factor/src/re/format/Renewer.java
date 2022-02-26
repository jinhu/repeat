package re.format;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.text.edits.ReplaceEdit;

import re.use.CppSourceVisitor;

public class Renewer {

	public static void main(String[] args) throws IOException, InterruptedException {
		//auto TCR
		var target = "/mnt/c/sw-dev/demo/voctir";
	    try (var files = Files.walk(Path.of(target))) {
	        files.forEach((path) -> processFile(path));
	        
	    }
	    Process process = Runtime.getRuntime().exec("/usr/bin/make", null, new File(target));
	    //
      BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
      StringBuilder result = new StringBuilder();
      String line;
      while ((line = in.readLine()) != null) {
              System.out.println(line);
      }
        process.waitFor(300, TimeUnit.SECONDS);
	    
      if (!String.valueOf(process.exitValue()).matches("[01]")) {      
        process = Runtime.getRuntime().exec("git reset --hard", null, new File(target));
        process.waitFor(60, TimeUnit.SECONDS);
      }else {
       process = Runtime.getRuntime().exec("git commit -am'auto refactor ...'", null, new File(target));
       process.waitFor(60, TimeUnit.SECONDS);

      }
	}
	static int i=0;
	private static void processFile(Path path) {
		if(path.toString().endsWith(".c")) {
			try {
				System.out.println("file: "+ (i++) + "\t "+path);
				var text = Files.readString(path);
				text = text.replace('', ' ')
//						.replaceAll(";\\+\\+", "")
//						.replaceAll("\\n;\\s*\\n", "\n")
						.replaceAll("; \\$Id:.*\\$","")
						.replaceAll("; \\$Log:.*\\$","")
						.replaceAll(";\\n;\\s+\\(c\\) .*\\d+\\n;\\s+All rights reserved.\\n;\\n", " (c) Example B.V. 2022   All rights reserved.\n");
				text = text.replaceAll(";  FUNCTIONAL DESCRIPTION:\\n;  (.*)", "/// $1");
				text = text.replaceAll(";  PRECONDITIONS:\n;  (.*)", "@Preconditions $1");
				text = text.replaceAll(";  KNOWN LIMITATIONS:\n;  (.*)","@Limitations $1");
				text = text.replaceAll(";  (\\S+)    (.*)","@param $1\t$2");
				
				text = text.replaceAll("static\\s+(\\S+) ?\\(", "int $1(");
				text = text.replaceAll("static\\s+(\\S+)\\s+(\\S+) ?\\(", "$1 $2(");
				text = text.replaceAll("extern int	errno;", "#include <errno.h>\nextern int	errno;");
				text = text.replaceAll("extern char.*sys_errlist\\[\\];", "");				
				Files.write(path, text.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
//        BufferedReader err = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        StringBuilder errResult = new StringBuilder();
//        String errLine;
//        while ((errLine = err.readLine()) != null) {
//            System.out.println(errLine);
//            errResult.append(errLine).append(System.lineSeparator());
//        }
//        process.waitFor(5, TimeUnit.SECONDS);
//        if (!ignoreError && !String.valueOf(process.exitValue()).matches("[01]")) {
//            throw new Exception(
//                    "Error: " + process.exitValue() + " " + errResult + " " + result);
//        }
