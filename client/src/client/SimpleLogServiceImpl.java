package client;

import org.osgi.service.component.annotations.*;

@Component
public class SimpleLogServiceImpl implements SimpleLogService {

	public SimpleLogServiceImpl() {
		int x=0;
	}
	@Override
	public void log(String message) {
		System.out.println(message);
	}
	
}
