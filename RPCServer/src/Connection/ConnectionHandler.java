package Connection;

import java.net.*;

public class ConnectionHandler extends Thread {
	private Socket socket = null;

	public ConnectionHandler(Socket socket) {
		super("ConnectionHandler");
		this.socket = socket;
	}
	
	public void run() {
		
	}
}
