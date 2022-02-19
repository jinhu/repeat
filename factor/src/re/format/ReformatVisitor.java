package re.format;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.cdt.internal.core.dom.rewrite.commenthandler.ASTCommenter;
import org.eclipse.core.runtime.CoreException;

import re.use.CppSourceVisitor;

public class ReformatVisitor extends CppSourceVisitor {


	private ASTVisitor commentVisitor = new ASTVisitor() {
//		visit()
	};

	@Override
	public void porcessfile(IASTTranslationUnit atu) {
		var factory = atu.getASTNodeFactory();
//		factory.newc
		rewrite = ASTRewrite.create(atu);
		rewrite.removeAllComments(atu);
			
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
		var changes = rewrite.rewriteAST();
		try {
			changes.perform(progressMonitor);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

	private String formatComment(char[] comment) {
		var text =  new String(comment);
		return text.replaceAll("^Revision .*$", "");
	}

}
//ASTLiteralNode
