package Connection;

import java.util.concurrent.TimeUnit;

import org.apache.http.ExceptionLogger;
import org.apache.http.impl.nio.bootstrap.HttpServer;
import org.apache.http.impl.nio.bootstrap.ServerBootstrap;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
/**
 * Server object which passes connections to
 * a handler.
 * 
 * Based on Asynchronous File Server example found at
 * https://hc.apache.org/httpcomponents-core-4.4.x/httpcore-nio/examples/org/apache/http/examples/nio/NHttpFileServer.java
 *
 */
public class CommerceServer {

	public static void main(String[] args) throws Exception {
		
		// Establish port Number
		int portNumber = 6666;
		
		// Set up Timeout and Delay config
		final IOReactorConfig config = IOReactorConfig.custom()
				.setSoTimeout(15000)
				.setTcpNoDelay(true)
				.build();
		
		// Build the server object
		final HttpServer server = ServerBootstrap.bootstrap()
				.setListenerPort(portNumber) // Tell it which port to listen on
				.setServerInfo("Test/1.1") // Add the header (not needed?)
				.setIOReactorConfig(config) // Pass our config
				.setExceptionLogger(ExceptionLogger.STD_ERR) // Setup error logging
				.registerHandler("*", new ConnectionHandler()) // Add our handler to the server
				.create(); // Create the server
		System.out.println(">> Created server");
		// Start the server
		server.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				server.shutdown(5, TimeUnit.SECONDS);
			}
		});
	}
}