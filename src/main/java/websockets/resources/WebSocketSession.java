package websockets.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;
import org.eclipse.jetty.websocket.api.Session;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class WebSocketSession {

	private Session session;
	
	private boolean isAuthenticated = false;
}
