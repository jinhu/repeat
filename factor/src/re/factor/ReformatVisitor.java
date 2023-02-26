package re.factor;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICElementVisitor;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.BasicMonitor.Printing;

public class ReformatVisitor {


	protected ASTRewrite rewrite;
	protected IProgressMonitor progressMonitor = BasicMonitor.toIProgressMonitorWithBlocking(new Printing(System.out));


	private ICElementVisitor commentVisitor = new ICElementVisitor() {
		@Override
		public boolean visit(ICElement element) throws CoreException {
			if(element instanceof ITranslationUnit tu) {
				var atu = tu.getAST();
	            if(atu!=null) {
					rewrite = ASTRewrite.create(atu);
					rewrite.removeAllComments(atu);
				}
	    		try {
	    			var changes = rewrite.rewriteAST();
	    				changes.perform(progressMonitor);
    			} catch (CoreException e) {
    				e.printStackTrace();
    			}

			}
			return true;
		}
	};

	private String formatComment(char[] comment) {
		var text =  new String(comment);
		return text.replaceAll("^Revision .*$", "");
	}
			
//			//var comm = new IASTComment.("/*new comment*/");
////			comm.getChildren();
//			rewrite.addComment(atu, (IASTComment) comm, CommentPosition.leading);
//			var text = rewrite.toString();
//			rewrite.rewriteAST();
//		}
//		atu.accept(commentVisitor );
//		var comments = atu.getComments();
//		var start =0; 
//		var text ="";
//		for (int i = 0; i < comments.length; i++) {
//			var loc = comments[i].getFileLocation();
//			if(loc==null)continue;
//			if(loc.getStartingLineNumber() ==start +1) {
//				text += comments[i].toString()+"\n";
//				start++;
//				rewrite.remove(comments[i], null);				
//			}
//			else{
//				var temp = comments[i].toString().replaceAll("\\/?\\*\\/?", "\n");
//				start = loc.getStartingLineNumber();			
//				text = "/*"+ temp;		
//		}
//			if(i+1==comments.length) {
//				text = text.replaceAll("^Revision .*$", "")
//						.replaceAll("\\/?\\*\\/?", "\n")
//						.replaceAll("\n+", "\n")
//				.replaceAll(";++\s*", "\n")
//				.replaceAll("; \\$Id:.*\\$", "\n")
//				.replaceAll("; \\$Log:.*\\$", "\n")
//				.replaceAll(".* Revision .*", "\n")
//				.replaceAll(";\s+\\(c\\).*", "\n")
//				.replaceAll("\n+", "\n");
//				comments[i].setComment(text.toCharArray() );
//				rewrite.addComment(atu,comments[i] , CommentPosition.leading);
//				
//				System.out.print(rewrite.toString());
//				break;
//				
//			}
//		}
//		ASTCommenter.addCommentsToMap(atu, null);
//		ASTCommenter.
//		var comment = atu.getComments();
//		rewrite.removeAllComments(atu);
//		rewrite.remove(atu, null);

}
//ASTLiteralNode
