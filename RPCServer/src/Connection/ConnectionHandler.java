package Connection;

import java.io.IOException;
import java.util.Locale;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.nio.protocol.BasicAsyncRequestConsumer;
import org.apache.http.nio.protocol.BasicAsyncResponseProducer;
import org.apache.http.nio.protocol.HttpAsyncExchange;
import org.apache.http.nio.protocol.HttpAsyncRequestConsumer;
import org.apache.http.nio.protocol.HttpAsyncRequestHandler;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import Parsing.RequestParser;

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
		System.out.println(">> Got request");
		final HttpResponse response = httpExchange.getResponse();
		handleInternal((HttpEntityEnclosingRequest) request, response, context);
		httpExchange.submitResponse(new BasicAsyncResponseProducer(response));
	}

	private void handleInternal(HttpEntityEnclosingRequest request, HttpResponse response, HttpContext context) 
			throws HttpException, IOException {
		
		RequestParser parser = new RequestParser();
		
		// Check to make sure this is a POST request
		// This "guarantees" that there will be an entity
		String method = request.getRequestLine()
				.getMethod()
				.toUpperCase(Locale.ENGLISH);
		
		if(!method.equals("POST")) {
			throw new MethodNotSupportedException(method 
					+ " method not supported");
		}
		
		// Get the entity and pull the message from it
		String jsonString = EntityUtils.toString(request.getEntity());
		
		// Pass the string to the parser, which should return 
		// the json response as a string
		String responseMessage = parser.parseMethod(jsonString);
		
		// Build our return entity
		final NStringEntity entity = new NStringEntity(
				responseMessage,
				ContentType.create("application/json"));
		
		// Attach the entity and set the status code
		response.setStatusCode(HttpStatus.SC_OK);
		response.setEntity(entity);
	}


	@Override
	public HttpAsyncRequestConsumer<HttpRequest> processRequest(HttpRequest arg0, HttpContext arg1)
			throws HttpException, IOException {
		
		return new BasicAsyncRequestConsumer();
	}
}
