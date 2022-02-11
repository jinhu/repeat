package re.view;

import java.util.Iterator;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jetty.server.Server;
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
		Server server = new Server(8888);
		DiagramHandler ctx = new DiagramHandler();
		server.setHandler(ctx);
		server.start();
//		var refactorings = new Refactorings();
//		var atu = re.use.Helper.getAtu("c:\\sw-dev\\re\\example\\refactorings\\example.refactor.c");
//		atu.accept(refactorings);
//		var visitor = new CppSourceVisitor();
//		visitor.setRefactorings(refactorings);
//		var cModel = CoreModel.getDefault().getCModel();
//		try {
//			for (var proj : cModel.getCProjects()) {
//				proj.accept(visitor);
//			}
//			cModel.accept(visitor);
//			var projects = cModel.getCProjects();
//			var srcs = projects[0].getAllSourceRoots();
//		} catch (CoreException e) {
//			e.printStackTrace();
//		}

	}
	
}
