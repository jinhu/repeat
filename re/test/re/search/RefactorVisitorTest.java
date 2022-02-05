import re.use.Helper;

import static org.junit.jupiter.api.Assertions.*;

class RefactorVisitorTest {

    @org.junit.jupiter.api.Test
    void visit() {
        atu = Helper.getAtu("fake", """
                int 
                fun(){
                    if(true){
                    }else{
                    }
                    
                    if(false){
                    }
                    if(true){
                    }
                    if(false){
                    }
                    call(1);
                    call(2);
                    call(3);
                    call(4);
                    call(5);
                    
                    
                }
                """);
    }
}