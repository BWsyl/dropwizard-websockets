package websockets.resources;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class BroadcastServlet extends WebSocketServlet{

	 /**
	 * 
	 */
	private static final long serialVersionUID = -2554726701202659655L;

	@Override
	   public void configure(WebSocketServletFactory factory) {
	      factory.register(BroadcastSocket.class);
	   }
	
}
