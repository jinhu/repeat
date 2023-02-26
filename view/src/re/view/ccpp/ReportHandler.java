package re.view.ccpp;

import java.io.IOException;

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;

import re.factor.ReportVisitor;

public class ReportHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			var report = new ReportVisitor();
			report.start();
			CoreModel.getDefault().getCModel().accept(report);
			report.finish();
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}