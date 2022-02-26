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
import java.util.concurrent.TimeUnit;

public class Remover {

	private static void processFile(Path path) {
		if(path.toString().endsWith("_ccmwaid.inf")) {
			System.out.println(path);
			try {
				Files.delete(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}



 String sourceBase = "/mnt/c/sw-dev/demo/voctir/";
 String targetBase = "/mnt/c/sw-dev/demo/voctir/";
 String sourceComponent = "recv";
 String targetComponent = "receive_";
private File workDir;
private Process process;

 public Remover(String... args) {
     if (args.length == 4) {
         sourceBase = args[0];
         sourceComponent = args[1];

         targetBase = args[2];
         targetComponent = args[3];
     }
 }

 // @precondition: register the new component so that it can be build
 // @postcondition: the component is cloned
 public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
     var cloner = new Remover(args);
     cloner.copy();	
     if(cloner.test()) {
    	 cloner.commit();
     }else{
    	 cloner.revert();
     }
 }

 private void revert() throws IOException {
     workDir = new File(sourceBase);
     process = new ProcessBuilder("git reset --hard").directory(workDir).redirectOutput(Redirect.INHERIT).redirectError(Redirect.INHERIT).start();
     
}

private void commit() throws IOException, InterruptedException {
	workDir = new File(sourceBase);
    process = new ProcessBuilder("git","add",".").directory(workDir).redirectOutput(Redirect.INHERIT).redirectError(Redirect.INHERIT).start();
    process.waitFor(2, TimeUnit.SECONDS);
    process = new ProcessBuilder("git","commit","-am", "'auto refactor'").directory(workDir).redirectOutput(Redirect.INHERIT).redirectError(Redirect.INHERIT).start();
    process.waitFor(2, TimeUnit.SECONDS);
    
}

private boolean test() throws IOException, InterruptedException {
    System.out.println("start test");
    workDir = new File(targetBase+targetComponent);//.replaceFirst("_$", "");
    process = new ProcessBuilder("make").directory(workDir).redirectOutput(Redirect.INHERIT).redirectError(Redirect.INHERIT).start();

	 System.out.println("finish test");
     process.waitFor(2, TimeUnit.SECONDS);
	 return process.exitValue() >=0;
}

private void copy() throws IOException {
     cloneComponent(sourceBase, sourceComponent, targetBase, targetComponent);
     updateHeaders(sourceBase+"include/", sourceComponent, targetComponent);
}

 public static void cloneEntity(String sourceBase, String sourceComponent, String targetBaseDir,
                                String targetComponent, Path source) {

     // compose the target path both the component as well as fc needs to be replaced
	 var path =source.toString()
	 .replace(sourceBase, targetBaseDir)
	 .replace(sourceComponent, targetComponent);
	// .replace("_.", ".")
	// .replace("_/", "/")
	// .replaceFirst("_$", "");
     var target = Path.of(path);

     // continue with cloning when one entity fails for what ever reason
     try {
         if (Files.isSymbolicLink(source)) {
             //only clone symlinked csv files for now
             if (source.toString().endsWith(".csv")) {
                 // make sure the working dir is correct, otherwise sym lnk will fail
                 System.setProperty("user.dir", target.getParent().toString());
                 // compose symbolic link source
                 var linksource = Path.of(Files.readSymbolicLink(source).toString().replace(sourceComponent, targetComponent));
                 Files.createSymbolicLink(target, linksource);
             }

         } else if (Files.isDirectory(source)) {
             Files.createDirectory(target);
//				return; // just create new dir
         } else {
             // compose new content
             var content = cloneContent(source, sourceComponent, targetComponent);
             System.setProperty("user.dir", source.getParent().toString());
             Files.write(target, content, StandardOpenOption.CREATE_NEW);

         }
     } catch (Exception e) {
         System.out.println("skipped entry: " + source);
     }
 }

 private static byte[] cloneContent(Path path, String sourceComponent, String targetComponent) throws IOException {
     // text content, let's replace
     if (!path.toString().endsWith(".so") && !path.toString().endsWith(".ddb") && !path.toString().endsWith(".pyc")
             && !path.toString().endsWith(".pye") && !path.toString().endsWith(".vpi")
             && !path.toString().endsWith(".a") && !path.toString().endsWith(".o")) {
         var content = Files.readString(path);
         content = content.replace(sourceComponent, targetComponent);

         // apply exceptions in makefiles if needed
         if (path.toString().endsWith("akefile")) {
             // TODO: specific constrains
         }
         return content.getBytes();
     } else {
         // just return the blob
         return Files.readAllBytes(path);
     }
 }


 //clone all entries within the component
 public void cloneComponent(String sourceBase, String sourceComponent, String targetBaseDir, String targetComponent)
         throws IOException {
     System.out.println("start cloning");


     try (var files = Files.walk(Path.of(sourceBase + sourceComponent))) {
         files.forEach((path) -> cloneEntity(sourceBase, sourceComponent, targetBaseDir, targetComponent, path));
     }
     System.out.println("finished cloning");
}
 public void updateHeaders(String headerBase, String sourceComponent, String targetComponent)
         throws IOException {
     System.out.println("start Header");


     try (var files = Files.walk(Path.of(headerBase))) {
         files.forEach((path) -> cloneEntity(headerBase, sourceComponent, headerBase, targetComponent, path));
     }
     System.out.println("finished Header");
}
}