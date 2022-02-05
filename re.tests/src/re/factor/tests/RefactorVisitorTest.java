package re.factor.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.junit.jupiter.api.Test;

import re.search.RefactorVisitor;
import re.use.Helper;

class RefactorVisitorTest {

    @Test
    void testVisit() {
        var atu = Helper.getAtu("fake", """
                int  fun(){
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
                    
                    return 0;
                }
                """);
//        atu = Helper.getAtu("c:\\sw-dev\\whars\\java\\filter-dsls\\removeMarkSensorCleanup.c");
        var cleanup = new RefactorVisitor();
//        atu.accept(cleanup);
        assertEquals(1, cleanup.replacements.size() );
    }
}