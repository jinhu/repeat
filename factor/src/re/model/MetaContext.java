package re.model;

import org.eclipse.cdt.core.model.ICModel;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MetaContext {
	// top level of C4 model: context
    public String name;
    public String lang;
    public String version;
    public String domain;
    public List<String> dependencies;
    public List<MetaContainer> containers;

    public MetaContext() {
	}

	public static void main(String[] args) throws IOException {
        var yml = new Yaml();
        MetaContext context= yml.load(Files.readString(Path.of("resources/meta-context.yml")));
        context.saveAsCMake();
    }

    private void saveAsCMake() {

    }
}
