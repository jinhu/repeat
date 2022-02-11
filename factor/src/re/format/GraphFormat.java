package re.format;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTStatement;

public class GraphFormat extends ASTVisitor {

	private Path graphml = Path.of("c:\\sw-dev\\result.graphml");
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
	public GraphFormat() {
	}

	@Override
	public int visit(IASTDeclarator declarator) {
		try {
			var location = declarator.getFileLocation();
			Files.writeString(graphml, String.format(node,id++, declarator.getName(), location.getFileName(), location.getFileName(), location.getStartingLineNumber()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return super.visit(declarator);
	}
	@Override
	public int visit(IASTStatement designator) {
		try {
			var location = designator.getFileLocation();
			Files.writeString(graphml,String.format(node,id++, designator, location.getFileName(), location.getFileName(), location.getStartingLineNumber()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return super.visit(designator);
	}
	public void toText(String content) throws IOException {
		Files.writeString(graphml, header);
		Files.writeString(graphml, footer);
		
	}
	
	
}
