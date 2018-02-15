package Connection;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.nio.protocol.BasicAsyncRequestConsumer;
import org.apache.http.nio.protocol.HttpAsyncExchange;
import org.apache.http.nio.protocol.HttpAsyncRequestConsumer;
import org.apache.http.nio.protocol.HttpAsyncRequestHandler;
import org.apache.http.protocol.HttpContext;

/**
 * ConnectionHandler is responsible for handling the sending/receiving
 * of HTTP requests/responses from/to the client
 *
 *Also based off of Asynchronous File Server from Apache's HttpCore examples
 */
public class ConnectionHandler implements HttpAsyncRequestHandler<HttpRequest> {
	//private Socket socket = null;

	public ConnectionHandler() {
		super();	
	}

	@Override
	public void handle(HttpRequest request, HttpAsyncExchange httpExchange, HttpContext context)
			throws HttpException, IOException {
		
		final HttpResponse response = httpExchange.getResponse();
		
	}

	@Override
	public HttpAsyncRequestConsumer<HttpRequest> processRequest(HttpRequest arg0, HttpContext arg1)
			throws HttpException, IOException {
		
		return new BasicAsyncRequestConsumer();
	}
}
