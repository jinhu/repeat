package re.serve;
//import javax.servlet.Sevlet;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import org.yaml.snakeyaml.Yaml;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Path("/diagram")
public class UmlServlet implements Servlet {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
//        var data  = getClass().getResourceAsStream("/depal.puml").readAllBytes();
//        var uml = new String(data);
//        Map<String,String> result =  new Yaml().load(getClass().getResourceAsStream("/initiatives/"+initiative+".result.yml"));
//        for(var  line : result.entrySet()){
//            uml = uml.replace(line.getKey(),line.getValue());
//        }
//        var outPath = Paths.get("../../documentation/project/src/data/"+initiative + ".puml");
//        try {
//            Files.write(outPath, uml.getBytes(StandardCharsets.UTF_8));
//        }catch(IOException ioe){
//            System.out.println("Can't save file in "+outPath);
//            System.out.println(uml);
//        }
        var reader = new SourceStringReader("""
        		@startuml 
a->b
@enduml

        		""");
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        var option = new FileFormatOption(net.sourceforge.plantuml.FileFormat.SVG);
        String string = reader.outputImage( os, option).getDescription();

		response.setContentType("image/svg+xml");
		 os.close();

		response.getWriter().append(new String(os.toByteArray()));
	}
	
	
	@GET
    @Path("/")
    public Object list() throws IOException {
        var yml = new Yaml();
        var stream = getClass().getResourceAsStream("/initiatives.yml");
        return yml.load(stream);
    }

	@GET
    @Path("/{initiative}.svg")
    @Produces("image/svg+xml")
    public String readSvg(@PathParam("initiative") String initiative) throws IOException, java.net.URISyntaxException{
        var data  = getClass().getResourceAsStream("/initiatives/"+initiative+".puml").readAllBytes();
        var uml = new String(data);
        Map<String,String> result =  new Yaml().load(getClass().getResourceAsStream("/initiatives/"+initiative+".result.yml"));
        for(var  line : result.entrySet()){
            uml = uml.replace(line.getKey(),line.getValue());
        }
        var outPath = Paths.get("../../documentation/project/src/data/"+initiative + ".puml");
        try {
            Files.write(outPath, uml.getBytes(StandardCharsets.UTF_8));
        }catch(IOException ioe){
            System.out.println("Can't save file in "+outPath);
            System.out.println(uml);
        }
        var reader = new SourceStringReader(uml);
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        var option = new FileFormatOption(net.sourceforge.plantuml.FileFormat.SVG);
        String string = reader.outputImage( os, option).getDescription();
        os.close();
        return new String(os.toByteArray());
    }
}
