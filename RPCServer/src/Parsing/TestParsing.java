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
		
		BusinessLogic db = new BusinessLogic();
		
		BufferedReader reader = new BufferedReader(new FileReader(new File("requestExample.json")));
		String[] json = new String[3];
		for (int i = 0; i < json.length; i++) {
			json[i] = reader.readLine();
		}
		
				
		// 0: call to getItems with no filter
		// 1: call to getItems with filter "cool"
		// 2: call to purchaseItem with name "cool" and amount 4
		for (int i = 0; i < json.length; i++) {
			RequestParser parser = new RequestParser(json[i]);
			System.out.print(i + ": ");
			System.out.println(parser.getResponse());
		}
		
		
//		RPCResponseToJson();
		
	}

}
