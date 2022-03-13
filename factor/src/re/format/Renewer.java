package re.format;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.net.URISyntaxException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.parser.DefaultLogService;
import org.eclipse.cdt.core.parser.FileContent;
import org.eclipse.cdt.core.parser.IncludeFileContentProvider;
import org.eclipse.cdt.core.parser.ScannerInfo;
import org.yaml.snakeyaml.Yaml;

public class Renewer {

	public String codeBase;
	public String refactor;
	public String ignore;
	public List<String> binaryExtension;
	public List<String> content;
	public HashMap<String,String> updates;
	public HashMap<String,String> renamings;
	private File workDir;
	private Process process;
	

	enum ContentType {
		Directory, Link, Binary, Content, Garbage
	}

	public Renewer() {
	}


	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
        var yaml = new Yaml();
		Renewer renewer = yaml.load(Files.readString(Path.of("resources/renew.yml")));

		renewer.run(false);
		if (renewer.test()) {
			renewer.commit();
		} else {
			renewer.revert();
		}
	}
	private void run(boolean finalRun) throws IOException {
		var files = Files.walk(Path.of(codeBase));
		files.forEach(this::cloneEntity);
		if(finalRun) files.forEach(this::delete);
	}
	
	private boolean test() throws IOException, InterruptedException {
		System.out.println("start test");
		workDir = new File(replaceText(codeBase, renamings));// .replaceFirst("_$", "");
		process = new ProcessBuilder("make").directory(workDir).redirectOutput(Redirect.INHERIT)
				.redirectError(Redirect.INHERIT).start();

		System.out.println("finish test");
		process.waitFor(2000, TimeUnit.SECONDS);
		return process.exitValue() == 0;
	}

	private boolean revert() throws IOException, InterruptedException {
		workDir = new File(replaceText(codeBase, renamings));// .replaceFirst("_$", "");
		process = new ProcessBuilder("git","reset","--hard").directory(workDir).redirectOutput(Redirect.INHERIT)
				.redirectError(Redirect.INHERIT).start();
		process.waitFor(2000, TimeUnit.SECONDS);
		return process.exitValue() >= 0;
	}

	private boolean commit() throws IOException, InterruptedException {
		workDir = new File(replaceText(codeBase, renamings));// .replaceFirst("_$", "");
		process = new ProcessBuilder("git", "add", ".").directory(workDir).redirectOutput(Redirect.INHERIT)
				.redirectError(Redirect.INHERIT).start();
		process.waitFor(200, TimeUnit.SECONDS);
		process = new ProcessBuilder("git", "commit", "-am", "'auto refactor'").directory(workDir)
				.redirectOutput(Redirect.INHERIT).redirectError(Redirect.INHERIT).start();
		process.waitFor(200, TimeUnit.SECONDS);
		return process.exitValue() >= 0;
	}

	private void cloneEntity(Path source) {
		var target = composeTargetPath(source);
		try {
			switch (ContentTypeOf(source)) {
				case Link:  	cloneLink(source, target); 		break;
				case Directory:	Files.createDirectory(target); 	break;
				case Binary:	Files.copy(source, target); 	break;
				case Content:	cloneContent(source, target); 	break;
				case Garbage:                                   break;
			}
		} catch (IOException e) {
			if(e instanceof FileAlreadyExistsException) {
				System.out.println(target+" already exist");
			}else{
				e.printStackTrace();
			}
		}
	}
	
	private Path composeTargetPath(Path source) {
		var path = source.toString();
		path = replaceText(path, renamings);
		return Path.of(path);
	}
	
	String replaceText(String source, HashMap<String, String> modifications) {
		for(var mod : modifications.entrySet()) {
			source = source.replaceAll(mod.getKey(), mod.getValue());
			}
		return source;
	}
	private ContentType ContentTypeOf(Path source) {
		var name = source.toString();
		var extension = name.substring(name.lastIndexOf(".")+1);
		
		if(Files.isSymbolicLink(source)) 		return ContentType.Link;
		if(Files.isDirectory(source)) 			return ContentType.Directory;
		if(binaryExtension.contains(extension)) return ContentType.Binary;
		if(name.matches(ignore))    			return ContentType.Garbage;
		/*if(content.contains(extension))*/		return ContentType.Content;
	}

	private void cloneContent(Path source, Path target) throws IOException {
			var text = Files.readString(source);
//			text = text.replace("\n", "@@@");
			text = replaceText(text, renamings);
			text = replaceText(text, updates);
//			text = text.replace("@@@", "\n");
			if(source.toString().endsWith(".c")) {
				refactor(source, text);
			}
			Files.write(target, text.getBytes(), StandardOpenOption.CREATE);
	}

	private void refactor(Path source, String text) {
//	    var atu = re.use.Helper.getAtu(source.toString(), text);
//	    var decls = atu.getDeclarations();
//	    for(var decl : decls) {
//	    	if(decl instanceof IASTFunctionDefinition) {
//	    		int i =0;
//	    	}
//	    }
	}
		


	private void cloneLink(Path source, Path target) throws IOException {
		System.setProperty("user.dir", target.getParent().toString());
		var reference = Files.readSymbolicLink(source).toString();
		reference = replaceText(reference, renamings);
		Files.createSymbolicLink(target, Path.of(reference));
	}

	private void delete(Path path) {
		try {
			Files.delete(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
