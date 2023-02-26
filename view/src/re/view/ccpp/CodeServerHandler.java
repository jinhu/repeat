package re.view.ccpp;

import java.net.InetSocketAddress;

import org.eclipse.jetty.server.Server;


public class CodeServerHandler {

	public void execute()  throws Exception{
				Server server = new Server(8888);
				DiagramHandler ctx = new DiagramHandler();
				server.setHandler(ctx);
				server.start();
	}
}
