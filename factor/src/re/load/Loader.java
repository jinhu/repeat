package re.load;


import re.use.Appl;

import java.io.IOException;

public class Loader implements Appl {

    @Override
    public void start(String[] args) {
        try {
            var loader = new MakefileLoader(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
