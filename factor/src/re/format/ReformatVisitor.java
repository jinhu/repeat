package re.format;

import java.util.Iterator;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTComment;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite.CommentPosition;
import org.eclipse.cdt.internal.core.dom.rewrite.ASTLiteralNode;
import org.w3c.dom.Text;

import re.use.CppSourceVisitor;

public class ReformatVisitor extends CppSourceVisitor {

	private ASTVisitor commentVisitor = new ASTVisitor() {
	};

	@Override
	public void porcessfile(IASTTranslationUnit atu) {
		if(atu.getFilePath().contains("bplus.c")){
			var rewrite = ASTRewrite.create(atu);
			rewrite.removeAllComments(atu);
			var comm = new ASTLiteralNode("/*new comment*/");
//			comm.getChildren();
//			rewrite.addComment(atu, (IASTComment) comm, CommentPosition.leading);
			rewrite.rewriteAST();
		}
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
////				var temp = comments[i].toString().replaceAll("\\/?\\*\\/?", "\n");
////				start = loc.getStartingLineNumber();			
////				text = "/*"+ temp;		
////		}
////			if(i+1==comments.length) {
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
////				rewrite.addComment(atu,comments[i] , CommentPosition.leading);
//				break;
//				
//			}
//		}
//		rewrite.rewriteAST();
//
//		}
	}
	/*
	;++
	; $Id: spfall.c,v 1.1 1994/09/21 07:25:19 seriint Approved $
	; $Log: spfall.c,v $
	 * Revision 1.1  1994/09/21  07:25:19  seriint
	 * Initial revision
	 *
	;
	;		(c) Philips Export B.V. 1987
	;		    All rights reserved.
	;
	;  FUNCTIONAL DESCRIPTION:
	;  lists the boxtype of all entries in the spoolheader file
	;
	;  CALLING SEQUENCE:
	;  status = spf_all(pc)
	;
	;  FORMAL PARAMETERS:
	;  pc - ptr to CMDQUA structure.
	;
	;  PRECONDITIONS:
	;  None.
	;
	;  KNOWN LIMITATIONS:
	;  None.
	;
	;--
	*/

	private String formatComment(char[] comment) {
		var text =  new String(comment);
		return text.replaceAll("^Revision .*$", "");
	}

}
//ASTLiteralNode