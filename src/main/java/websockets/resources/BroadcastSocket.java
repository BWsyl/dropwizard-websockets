package websockets.resources;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class BroadcastSocket extends WebSocketAdapter{

	private static Map<String,WebSocketSession> sessions = new ConcurrentHashMap<String, WebSocketSession>();
	
	 @Override
	   public void onWebSocketConnect(Session session) {
	      super.onWebSocketConnect(session);
		 super.getSession().setIdleTimeout(-1);
	      sessions.put(Integer.toHexString(session.hashCode()), WebSocketSession.builder().session(session).build());
	      log.info("Socket Connected: {}", Integer.toHexString(session.hashCode()));
	   }

	   @Override
	   public void onWebSocketClose(int statusCode, String reason) {
	      super.onWebSocketClose(statusCode, reason);
		   sessions.remove(Integer.toHexString(getSession().hashCode()));
	      log.info("Socket Closed: [{}] {}", statusCode, reason);
	   }

	   @Override
	   public void onWebSocketError(Throwable cause) {
	      super.onWebSocketError(cause);
		   sessions.remove(Integer.toHexString(getSession().hashCode()));
	      log.error("Websocket error", cause);
	   }

	   @Override
	   public void onWebSocketText(String message) {
		   log.debug("Got text "+message+" from "+Integer.toHexString(getSession().hashCode()));
		   if(message.equalsIgnoreCase("authent")){
				   sessions.get(Integer.toHexString(getSession().hashCode())).setAuthenticated(true);
			   }
	   }

	   public static void broadcast(String msg) {
	      sessions.forEach((key,session) -> {
	         try {
	        	 if(session.isAuthenticated() && session.getSession().isOpen()){
					 log.trace("******************* sending *****************");
	        		 session.getSession().getRemote().sendString(msg);
	        	 }
	         } catch (IOException e) {
	            log.error("Problem broadcasting message", e);
	         }
	      });
	   }
}
