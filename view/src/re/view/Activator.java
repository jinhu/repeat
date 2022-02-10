package re.view;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import re.search.CppSourceVisitor;
import re.search.Refactorings;
import view.internal.ViewActivator;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends ViewActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		var refactorings = new Refactorings();
		var atu = re.use.Helper.getAtu("c:\\sw-dev\\re\\example\\refactorings\\example.refactor.c");
		atu.accept(refactorings);
		var space = new CppSourceVisitor();
		space.setRefactorings(refactorings);
		var cModel = CoreModel.getDefault().getCModel();
		try {
			cModel.accept(space);
		} catch (CoreException e) {
			e.printStackTrace();
		}

	}
	
}
