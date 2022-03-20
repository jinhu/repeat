package re.format;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Renewer {

	String sourceBase = "/mnt/c/sw-dev/demo/voctir/";
	String targetBase = "/mnt/c/sw-dev/demo/voctir/";
	String sourceComponent = "recv";
	String targetComponent = "receive_";
	private File workDir;
	private Process process;

	public Renewer(String... args) {
		if (args.length == 4) {
			sourceBase = args[0];
			sourceComponent = args[1];

			targetBase = args[2];
			targetComponent = args[3];
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {

		var renewer = new Renewer(args);
		renewer.run(false);
		if (renewer.test()) {
			renewer.commit();
		} else {
			renewer.revert();
		}
	}

	private boolean revert() throws IOException {
		workDir = new File(sourceBase);
		process = new ProcessBuilder("git reset --hard").directory(workDir).redirectOutput(Redirect.INHERIT)
				.redirectError(Redirect.INHERIT).start();
		return process.exitValue() >= 0;
	}

	private boolean commit() throws IOException, InterruptedException {
		workDir = new File(sourceBase);
		process = new ProcessBuilder("git", "add", ".").directory(workDir).redirectOutput(Redirect.INHERIT)
				.redirectError(Redirect.INHERIT).start();
		process.waitFor(2, TimeUnit.SECONDS);
		process = new ProcessBuilder("git", "commit", "-am", "'auto refactor'").directory(workDir)
				.redirectOutput(Redirect.INHERIT).redirectError(Redirect.INHERIT).start();
		process.waitFor(2, TimeUnit.SECONDS);
		return process.exitValue() >= 0;
	}

	private boolean test() throws IOException, InterruptedException {
		System.out.println("start test");
		workDir = new File(targetBase + targetComponent);// .replaceFirst("_$", "");
		process = new ProcessBuilder("make").directory(workDir).redirectOutput(Redirect.INHERIT)
				.redirectError(Redirect.INHERIT).start();

		System.out.println("finish test");
		process.waitFor(2, TimeUnit.SECONDS);
		return process.exitValue() >= 0;
	}

	private void run(boolean finalRun) throws IOException {
			var files = Files.walk(Path.of(sourceBase + sourceComponent));
			files.forEach(this::cloneEntity);
			if(finalRun) files.forEach(this::delete);
			files = Files.walk(Path.of(sourceBase + "include/"));
			files.forEach(this::cloneEntity);
			if(finalRun) files.forEach(this::delete);
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
			e.printStackTrace();
		}
	}
	private Path composeTargetPath(Path source) {
		var path = source.toString().replace(sourceBase, targetBase).replace(sourceComponent, targetComponent);
		// .replace("_.", ".").replace("_/", "/").replaceFirst("_$", "");
		var target = Path.of(path);
		return target;
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
	enum ContentType {
		Directory, Link, Binary, Content, Garbage
	}
	List<String> binaryExtension = List.of("so","ddb","pyc","pye","vpi","a","o");

	private void cloneContent(Path source, Path target) throws IOException {
			var text = Files.readString(source);
			text = text.replace(sourceComponent, targetComponent);
			text = text.replace('', ' ').replaceAll(";\\+\\+", "").replaceAll("\\n;\\s*\\n", "\n")
					.replaceAll("; \\$Id:.*\\$", "").replaceAll("; \\$Log:.*\\$", "")
					.replaceAll(";\\n;\\s+\\(c\\) .*\\d+\\n;\\s+All rights reserved.\\n;\\n",
							" (c) Example B.V. 2022   All rights reserved.\n");
			text = text.replaceAll(";  FUNCTIONAL DESCRIPTION:\\n;  (.*)", "/// $1");
			text = text.replaceAll(";  PRECONDITIONS:\n;  (.*)", "@Preconditions $1");
			text = text.replaceAll(";  KNOWN LIMITATIONS:\n;  (.*)", "@Limitations $1");
			text = text.replaceAll(";  (\\S+)    (.*)", "@param $1\t$2");

			text = text.replaceAll("static\\s+(\\S+) ?\\(", "int $1(");
			text = text.replaceAll("static\\s+(\\S+)\\s+(\\S+) ?\\(", "$1 $2(");
			text = text.replaceAll("extern int	errno;", "#include <errno.h>\nextern int	errno;");
			text = text.replaceAll("extern char.*sys_errlist\\[\\];", "");

			// apply exceptions in makefiles if needed
//			if (source.toString().endsWith("akefile")) {
//			}
			Files.write(target, text.getBytes(), StandardOpenOption.CREATE_NEW);

	}


	private void cloneLink(Path source, Path target) throws IOException {
		// make sure the working dir is correct, otherwise sym lnk will fail
		System.setProperty("user.dir", target.getParent().toString());
		// compose symbolic link source
		var linksource = Path.of(Files.readSymbolicLink(source).toString().replace(sourceComponent, targetComponent));
		Files.createSymbolicLink(target, linksource);
	}

	private void delete(Path path) {
		try {
			Files.delete(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
