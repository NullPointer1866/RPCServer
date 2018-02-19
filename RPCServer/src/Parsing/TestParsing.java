package Parsing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import BusinessLogic.BusinessLogic;

public class TestParsing {

	public static void RPCResponseToJson() {
		RPCResponse resp = new RPCResponse();
		resp.addToResponse("hello");
		resp.addToResponse("world");
		resp.addToResponse(2.0);
		
		System.out.println(resp.toJson());		
	}
	
	
	public static void main(String[] args) throws IOException {
		
		BusinessLogic.initDB();
		
		BufferedReader reader = new BufferedReader(new FileReader(new File("requestExample.json")));
		String[] json = new String[1];
		for (int i = 0; i < json.length; i++) {
			json[i] = reader.readLine();
		}
		reader.close();
				
		// 0: call to getItems with no filter in the params element
		// 1: call to getItems with no params element
		// 2: call to getItems with filter "shoes"
		// 3: call to purchaseItem with name "socks" and amount 4
		// 4: call to purchaseItem with name 3 and amount "socks"
		// 5: call to purchaseItem with name "shoes" and amount 500
		// 6: call to purchaseItem with only one parameter
		// 7: call to purchase Item with no params element
		// 8: call to purchase Item with no arguments in the param element
		for (int i = 0; i < json.length; i++) {
			RequestParser parser = new RequestParser();
			System.out.print(i + ": ");
			System.out.println(parser.getResponse(json[i]) + "\n");
		}
		
		System.out.println("Test of RPCResponse.toJson method: ");
		// RPCResponseToJson();
		
	}

}
