package Parsing;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

public class RPCResponse {
	double version;
	int status;
	ArrayList<Object> response;
	Object fault;

	public RPCResponse() {
		version = 1.0;
		status = 0;
	}
	
	public void addToResponse(Object resp) {
		if (response == null)
			response = new ArrayList<Object>();
		Gson gson=new Gson();
		
		response.add(resp);
	}
	
	public String toJson() {
		Type rpctype = new TypeToken<RPCResponse>() {}.getType();
		return new Gson().toJson(this, rpctype);
	}
	
}
