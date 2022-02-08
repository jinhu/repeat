/*
 * generated by Xtext 2.25.0
 */
package re.ui;

import org.eclipse.cdt.core.model.CoreModel;
import org.osgi.framework.BundleContext;

import re.ui.internal.ReActivator;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class ReviewActivator extends ReActivator {


	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		var space = new re.search.WorkspaceVisitor();
		var cModel = CoreModel.getDefault().getCModel();
		cModel.accept(space);
	}
	

	
}
