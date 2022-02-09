package re.format;

import org.eclipse.cdt.core.model.ICModel;

import re.model.AbstractAstModel;

public class Phrase {
	public static String TITLE ="Hello Re";
	public GraphFormat graph;
	
	public Phrase(AbstractAstModel model) {
		graph = new GraphFormat(model);
		graph.toText("c:\\sw-dev\\demo\\yed.graphml");
	}
}
