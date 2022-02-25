package re.format;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.text.edits.ReplaceEdit;

import re.use.CppSourceVisitor;

public class Renewer {

	public static void main(String[] args) throws IOException {
	
	    try (var files = Files.walk(Path.of("C:/sw-dev/demo/voctir"))) {
	        files.forEach((path) -> processFile(path));
	        
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
