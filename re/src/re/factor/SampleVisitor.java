package re.factor;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;

public class SampleVisitor extends ASTVisitor {

    private final ASTRewrite rewrite;

    SampleVisitor(ASTRewrite rewriter) {
        super(true);
        rewrite = rewriter;
    }

    @Override
    public int visit(IASTStatement stm) {

        if (stm instanceof IASTIfStatement) {
            rewrite.insertBefore(stm.getParent(), stm,
                    rewrite.createLiteralNode("callTo(1,2,3);"), null);
        }
        return PROCESS_CONTINUE;
    }

}