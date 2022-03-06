package re.model;

import java.util.List;

public class MetaFunction {
	List<String> modifiers;
	List<MetaVariable> parameters;	
	MetaVariable returnType;
	String name;
	List<MetaStatement> body;
}
