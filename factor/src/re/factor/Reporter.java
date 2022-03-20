package re.port;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage;
import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.parser.DefaultLogService;
import org.eclipse.cdt.core.parser.FileContent;
import org.eclipse.cdt.core.parser.IParserLogService;
import org.eclipse.cdt.core.parser.IncludeFileContentProvider;
import org.eclipse.cdt.core.parser.ScannerInfo;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration;

public class Reporter {

	String header="""
<?xml version="1.0" encoding="utf-8"?>
<graphml xmlns="http://graphml.graphdrawing.org/xmlns" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" schemaLocation="http://graphml.graphdrawing.org/xmlns http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd">
  <key id="name"             for="node" attr.name="name"       attr.type="string" />
  <key id="type"             for="node" attr.name="type"       attr.type="string" />
  <key id="symbol"           for="node" attr.name="symbol"     attr.type="string" />
  <key id="lineNumber"       for="node" attr.name="lineNumber" attr.type="int" />
  
  <key id="type"             for="edge" attr.name="type" attr.type="string" />
  <key id="sourceLineNumber" for="edge" attr.name="sourceLineNumber" attr.type="int" />
  <key id="labelE"           for="edge" attr.name="labelE" attr.type="string" />
  <key id="labelV"           for="node" attr.name="labelV" attr.type="string" />
      <graph edgedefault="directed"> """;
	String footer="""
    </graph>
  </graphml>""";
	String node = """  
<node id="%d" labels=":CppDeclaration">
    <data key="name">%s</data>
    <data key="type">CppDeclaration</data>
    <data key="scope">%s</data>
    <data key="location">%s:%d</data>
</node>""";
	String edge="""
<edge source="68616" target="69635" label="Source">
  <desc>type=Source source=cpp_macrodef//xifs/WPxSIMMODExDEF/WPxSIMMODExDEFtyp.h/WPxSIMMODExDEF_TESTBENCH target=/xifs/WPxSIMMODExDEF/WPxSIMMODExDEFtyp.h type=Source</desc>
  <data key="type">Source</data>
  <data key="labelE">Source</data>
</edge>""";
	
	private int id;
//
//	@Override
//	public int visit(IASTDeclarator declarator) {
//		try {
//			var location = declarator.getFileLocation();
//			Files.writeString(graphml, String.format(node,id++, declarator.getName(), location.getFileName(), location.getFileName(), location.getStartingLineNumber()));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return super.visit(declarator);
//	}
//
//	@Override
//	public int visit(IASTStatement designator) {
//		try {
//			var location = designator.getFileLocation();
//			Files.writeString(graphml,String.format(node,id++, designator, location.getFileName(), location.getFileName(), location.getStartingLineNumber()));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return super.visit(designator);
//	}
//	public void toText(String content) throws IOException {
//		Files.writeString(graphml, header);
//		Files.writeString(graphml, footer);
//		
//	}

	public CharSequence reportContainment(IASTFunctionDeclarator declarator) {
		var location = declarator.getFileLocation();
		return  String.format(node,id++, declarator.getName(), 
				location.getFileName(), location.getFileName(), location.getStartingLineNumber());
	}

	public CharSequence reportContainment(IASTDeclarator declarator) {
		var location = declarator.getFileLocation();
		return  String.format(node,id++, declarator.getName(), 
				location.getFileName(), location.getFileName(), location.getStartingLineNumber());
	}
	public CharSequence reportRelation(String currentFun, ICPPASTFunctionCallExpression fun) {
			var location = fun.getFileLocation();
			return  String.format(edge ,id++, currentFun, fun.getFunctionNameExpression(), 
					location.getFileName(), location.getFileName(), location.getStartingLineNumber());
	}


}
