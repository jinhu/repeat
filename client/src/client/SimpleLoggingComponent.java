package client;

import org.osgi.service.component.annotations.*;

@Component
public class SimpleLoggingComponent {
	
	private SimpleLogService simpleLogService;
	
	@Reference
    public void bindLogger(SimpleLogService logService) {
		this.simpleLogService = logService;
    }

    public void unbindLogger(SimpleLogService logService) {
		this.simpleLogService = null;
    }

    @Activate
	public void activate() {
		if (simpleLogService != null) {
			simpleLogService.log("Yee ha, I'm logging!");
		}
	}
	
    @Deactivate
	public void deactivate() {
		if (simpleLogService != null) {
			simpleLogService.log("Yee ha, I'm logging!");
		}
	}
}
