package Connection;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpProcessorBuilder;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.EntityUtils;

public class TestClient {
	
	public static void main(String[] args) throws UnknownHostException, IOException, HttpException {
		
		HttpProcessor httpProc = HttpProcessorBuilder.create()
				.add(new RequestContent())
				.add(new RequestTargetHost())
				.add(new RequestConnControl())
				.add(new RequestUserAgent("Test/1.1"))
				.add(new RequestExpectContinue(true))
				.build();
		
		HttpRequestExecutor httpexecutor = new HttpRequestExecutor();
		
		HttpCoreContext coreContext = HttpCoreContext.create();
		HttpHost host = new HttpHost("localhost", 6666);
		coreContext.setTargetHost(host);
		
		DefaultBHttpClientConnection conn = new DefaultBHttpClientConnection(8 * 1024);
		ConnectionReuseStrategy strategy = DefaultConnectionReuseStrategy.INSTANCE;
		
		String getItemsjsonString = "{\"version\" : 1.0, "
				+ "\"methodName\" : \"getItems\","
				+ "\"params\" : []}";
		
		String purchaseItemsjsonString = "{\"version\" : 1.0, "
				+ "\"methodName\" : \"purchaseItem\","
				+ "\"params\" : [\"Socks\" , 2]}";
		HttpEntity entity = new NStringEntity(purchaseItemsjsonString, ContentType.create("application/json", Consts.UTF_8));
		
		try {
			if(!conn.isOpen()) {
				Socket socket = new Socket(host.getHostName(), host.getPort());
				conn.bind(socket);
			}
			
			BasicHttpEntityEnclosingRequest request = new BasicHttpEntityEnclosingRequest("POST", "localhost:6666");
			request.setEntity(entity);
			System.out.println(">> Request URI: " + request.getRequestLine().getUri());
			
			httpexecutor.preProcess(request, httpProc, coreContext);
			HttpResponse response = httpexecutor.execute(request, conn, coreContext);
			httpexecutor.postProcess(response, httpProc, coreContext);
			
			System.out.println(">> Response: " + response.getStatusLine());
			System.out.println(EntityUtils.toString(response.getEntity()));
			System.out.println("==============================");
			
			if (!strategy.keepAlive(response, coreContext)) {
                conn.close();
            } else {
                System.out.println("Connection kept alive...");
            }
			
		} finally {
			conn.close();
		}
		
	}
	
}
