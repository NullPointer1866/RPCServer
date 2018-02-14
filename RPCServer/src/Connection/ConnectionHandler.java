package Connection;

import java.io.IOException;
import java.net.*;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.nio.protocol.HttpAsyncExchange;
import org.apache.http.nio.protocol.HttpAsyncRequestConsumer;
import org.apache.http.nio.protocol.HttpAsyncRequestHandler;
import org.apache.http.protocol.HttpContext;

public class ConnectionHandler implements HttpAsyncRequestHandler<HttpRequest> {
	//private Socket socket = null;

	public ConnectionHandler() {
		super();	
	}

	@Override
	public void handle(HttpRequest arg0, HttpAsyncExchange arg1, HttpContext arg2) throws HttpException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HttpAsyncRequestConsumer<HttpRequest> processRequest(HttpRequest arg0, HttpContext arg1)
			throws HttpException, IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
