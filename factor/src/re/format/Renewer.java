package re.format;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.net.URISyntaxException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.yaml.snakeyaml.Yaml;

public class Renewer {

	public String codeBase;
	public List<String> binaryExtension;
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
		workDir = new File(codeBase);// .replaceFirst("_$", "");
		process = new ProcessBuilder("make").directory(workDir).redirectOutput(Redirect.INHERIT)
				.redirectError(Redirect.INHERIT).start();

		System.out.println("finish test");
		process.waitFor(2, TimeUnit.SECONDS);
		return process.exitValue() == 0;
	}

	private boolean revert() throws IOException, InterruptedException {
		workDir = new File(codeBase);
		process = new ProcessBuilder("git","reset","--hard").directory(workDir).redirectOutput(Redirect.INHERIT)
				.redirectError(Redirect.INHERIT).start();
		process.waitFor(2, TimeUnit.SECONDS);
		return process.exitValue() >= 0;
	}

	private boolean commit() throws IOException, InterruptedException {
		workDir = new File(codeBase);
		process = new ProcessBuilder("git", "add", ".").directory(workDir).redirectOutput(Redirect.INHERIT)
				.redirectError(Redirect.INHERIT).start();
		process.waitFor(2, TimeUnit.SECONDS);
		process = new ProcessBuilder("git", "commit", "-am", "'auto refactor'").directory(workDir)
				.redirectOutput(Redirect.INHERIT).redirectError(Redirect.INHERIT).start();
		process.waitFor(2, TimeUnit.SECONDS);
		return process.exitValue() >= 0;
	}



	private void cloneEntity(Path source) {
		try {
			var target = composeTargetPath(source);
			switch (ContentTypeOf(source)) {
				case Link:  	cloneLink(source, target); 		break;
				case Directory:	Files.createDirectory(target); 	break;
				case Binary:	Files.copy(source, target); 	break;
				case Content:	cloneContent(source, target); 	break;
				case Garbage:   Files.delete(source); 			break;
			}
		} catch (IOException e) {
			if(e instanceof FileAlreadyExistsException) {
				System.out.println(source+" already exist");
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
		
		if(Files.isSymbolicLink(source)) 		return ContentType.Directory;
		if(Files.isDirectory(source)) 			return ContentType.Directory;
		if(name.endsWith("_ccmwaid.inf")) 		return ContentType.Garbage;
		if(binaryExtension.contains(extension)) return ContentType.Binary;
												return ContentType.Content;
	}

	private void cloneContent(Path source, Path target) throws IOException {
			var text = Files.readString(source);
			text = replaceText(text, updates);
			Files.write(target, text.getBytes(), StandardOpenOption.CREATE_NEW);

			//			text = text.replace('', ' ').replaceAll(";\\+\\+", "").replaceAll("\\n;\\s*\\n", "\n")
//					.replaceAll("; \\$Id:.*\\$", "").replaceAll("; \\$Log:.*\\$", "")
//					.replaceAll(";\\n;\\s+\\(c\\) .*\\d+\\n;\\s+All rights reserved.\\n;\\n",
//							" (c) Example B.V. 2022   All rights reserved.\n");
//			text = text.replaceAll(";  FUNCTIONAL DESCRIPTION:\\n;  (.*)", "/// $1");
//			text = text.replaceAll(";  PRECONDITIONS:\n;  (.*)", "@Preconditions $1");
//			text = text.replaceAll(";  KNOWN LIMITATIONS:\n;  (.*)", "@Limitations $1");
//			text = text.replaceAll(";  (\\S+)    (.*)", "@param $1\t$2");
//
//			text = text.replaceAll("static\\s+(\\S+) ?\\(", "int $1(");
//			text = text.replaceAll("static\\s+(\\S+)\\s+(\\S+) ?\\(", "$1 $2(");
//			text = text.replaceAll("extern int	errno;", "#include <errno.h>\nextern int	errno;");
//			text = text.replaceAll("extern char.*sys_errlist\\[\\];", "");

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
