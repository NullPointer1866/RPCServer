package Connection;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.nio.protocol.BasicAsyncRequestConsumer;
import org.apache.http.nio.protocol.BasicAsyncResponseProducer;
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
		handleInternal((HttpEntityEnclosingRequest) request, response, context);
		httpExchange.submitResponse(new BasicAsyncResponseProducer(response));
	}

	private void handleInternal(HttpEntityEnclosingRequest request, HttpResponse response, HttpContext context) 
			throws HttpException, IOException {
		
		// Check to make sure this is a POST request
		// This "guarantees" that there will be an entity
		String method = request.getRequestLine()
				.getMethod()
				.toUpperCase(Locale.ENGLISH);
		
		if(!method.equals("POST")) {
			throw new MethodNotSupportedException(method 
					+ " method not supported");
		}
		
		HttpEntity body = request.getEntity();
		
		String jsonString = pullMessage(request, body);
		
	}

	private String pullMessage(HttpEntityEnclosingRequest request, HttpEntity body)
			throws UnsupportedOperationException, IOException {
		
		try(DataInputStream content = new DataInputStream(body.getContent())) {
			
			Header contentLength = request.getFirstHeader("Content-Length");
			int len = Integer.parseInt(contentLength.getValue());
			
			byte[] raw_string = new byte[len];
			content.readFully(raw_string, 0, len);
			
			return new String(raw_string);			
		} 
	}

	@Override
	public HttpAsyncRequestConsumer<HttpRequest> processRequest(HttpRequest arg0, HttpContext arg1)
			throws HttpException, IOException {
		
		return new BasicAsyncRequestConsumer();
	}
}
