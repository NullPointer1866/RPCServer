package Parsing;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import jdk.nashorn.internal.parser.JSONParser;

public class RPCResponse {
	double version;
	int status;
	JsonArray response;
	Object fault;

	public RPCResponse() {
		version = 1.0;
		status = 0;
	}
	
	public void addToResponse(String resp) {
		if (response == null)
			response = new JsonArray();
		Gson gson=new Gson();
		JsonElement element=gson.fromJson(resp,JsonElement.class);
		response.add(element);
	}
	
}
