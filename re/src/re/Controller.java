package re;

import org.eclipse.core.runtime.CoreException;

import re.load.Loader;
import re.use.Appl;
import re.use.Helper;
import re.view.Naissancer;
import re.search.Juvenal;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class Controller {


    private Map<String, Object> apps = Map.of(
            "replace",  new Naissancer(),
            "juvenate",   new Juvenal(),
            "load"    ,   new Loader(),
            "serve"   ,   new Server()
            );

    public static void main(String[] args) throws CoreException, IOException {
       new Controller().start(args);

    }

    private void start(String[] args) throws CoreException, IOException {
        var app = (Appl)apps.getOrDefault(args[0], new Helper());
        app.start(Arrays.copyOfRange(args, 1, args.length));

    }


}
