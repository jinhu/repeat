package re.format;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.ICElementVisitor;
import org.eclipse.cdt.internal.core.model.Binary;
import org.eclipse.cdt.internal.core.model.CContainer;
import org.eclipse.cdt.internal.core.model.CModel;
import org.eclipse.cdt.internal.core.model.CProject;
import org.eclipse.cdt.internal.core.model.Function;
import org.eclipse.cdt.internal.core.model.FunctionDeclaration;
import org.eclipse.cdt.internal.core.model.Include;
import org.eclipse.cdt.internal.core.model.Macro;
import org.eclipse.cdt.internal.core.model.SourceRoot;
import org.eclipse.cdt.internal.core.model.Structure;
import org.eclipse.cdt.internal.core.model.TranslationUnit;
import org.eclipse.cdt.internal.core.model.TypeDef;
import org.eclipse.cdt.internal.core.model.Variable;
import org.eclipse.cdt.utils.BinaryFile;
import org.eclipse.core.runtime.CoreException;
import org.yaml.snakeyaml.Yaml;

import re.model.MetaContext;

public class Transformer implements ICElementVisitor {

	
	private MetaContext context;

	@Override
	public boolean visit(ICElement element) throws CoreException {
		if(element instanceof CModel model) {
	        try {
				var yml = new Yaml();
				Files.write(Path.of("/mnt/c/sw-dev/demo/c4.yml"), yml.dump(model).getBytes());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("   1 1ontext: " +element+" - "+ element.getParent()+" "+ element.getLocationURI());						
		}else if(element instanceof CProject) {
			System.out.println("   2 container: " +element+" : "+element.getElementName()+" - "+element.getElementType()+" - "+ element.getLocationURI());			
		}else if(element instanceof SourceRoot) {
			System.out.println("   3 component: " +element+" : "+element.getElementName()+" - "+element.getElementType()+" - "+ element.getLocationURI());			
		}else if(element instanceof CContainer) {
			if(element.toString().equals(".git")){
				System.out.println("found git,skip children");
				return false;
			}
			if(element instanceof Binary) {
				System.out.println("found binary,skip children");
				return false;
			}
			System.out.println("   4 CContainer: " +element+" : "+element.getElementName()+" - "+element.getElementType()+" - "+ element.getLocationURI());			
		}else if(element instanceof TranslationUnit) {
			System.out.println("   4 code: " +element+" : "+element.getElementName()+" - "+element.getElementType()+" - "+ element.getLocationURI());			
		}else if(element instanceof Include) {
			System.out.println("    5 dependency: " +element+" : "+element.getElementName()+" - "+element.getElementType()+" - "+ element.getLocationURI());			
		}else if(element instanceof Function fun) {
			System.out.println("    4 function: " +element+" : "+element.getElementName()+" - "+element.getElementType()+" - "+ element.getLocationURI());			
		}else if(element instanceof Variable) {
			System.out.println("    5 variable: " +element+" : "+element.getElementName()+" - "+element.getElementType()+" - "+ element.getLocationURI());			
		}else if(element instanceof Macro) {
			System.out.println("    5 macro: " +element+" : "+element.getElementName()+" - "+element.getElementType()+" - "+ element.getLocationURI());			
		}else if(element instanceof FunctionDeclaration) {
			System.out.println("    5 function: " +element+" : "+element.getElementName()+" - "+element.getElementType()+" - "+ element.getLocationURI());			
		}else if(element instanceof TypeDef) {
			System.out.println("    5 type: " +element+" : "+element.getElementName()+" - "+element.getElementType()+" - "+ element.getLocationURI());			
		}else if(element instanceof Structure) {
			System.out.println("    5 Structure: " +element+" : "+element.getElementName()+" - "+element.getElementType()+" - "+ element.getLocationURI());			
		}else {	
			System.out.println(element.getClass());//+" -" +element+" : "+" - "+element.getElementType()+" - "+ element.getLocationURI());
		}
		return true;
	}

	public MetaContext getModel() {
		return context;
		
	}

}
