package Connection;

import java.io.*;
import java.net.*;

public class CommerceServer {

	public static void main(String[] args) throws IOException {
		
		//Establish port Number
		int portNumber = 6666;
		boolean listening = true;
		
		try(ServerSocket server = new ServerSocket(portNumber)) {
			
			while(listening) {
				new ConnectionHandler(server.accept()).start();
			}
		} catch(IOException e) {
			// Catch any problems listening on the port
			System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
		}
	}

}
