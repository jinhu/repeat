package re.format;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import re.use.CppSourceVisitor;

public class Remover {

	public static void main(String[] args) throws IOException {
	
	    try (var files = Files.walk(Path.of("C:/sw-dev/demo/voctir"))) {
	        files.forEach((path) -> processFile(path));
	        
	    }
	}

	private static Object processFile(Path path) {
		if(path.toString().endsWith("_ccmwaid.inf")) {
			System.out.println(path);
			try {
				Files.delete(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
