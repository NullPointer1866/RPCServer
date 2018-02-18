package Parsing;

public class TestParsing {

	public static void TestRPCResponse() {
		RPCResponse resp = new RPCResponse();
		resp.addToResponse("hello");
		resp.addToResponse("world");
		
		System.out.println(resp.toJson());		
	}
	
	public static void main(String[] args) {
		
		TestRPCResponse();
	}

}
