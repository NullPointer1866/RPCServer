package Parsing;
import java.util.Collection;
import java.lang.reflect.*;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import BusinessLogic.BusinessLogic;
import BusinessLogic.Item;

public class RequestParser {

	private String json;
	
	public RequestParser(String jsonString) {
		json = jsonString;
	}
	
	public String getResponse() {
		String method = parseMethodName();
		String response = callMethod(method);
		
		return response;
	}

	
	private String callMethod(String methodName) {
		RPCResponse response;
		
		if (methodName == "purchaseItem") {
			response = purchaseItem();
		}
		else if (methodName == "getItems") {
			response = getItems();
		}
		else
			response = new RPCResponse();
		
		return response.toJson();
	}

	// determines name of method to call and calls method in business logic
	public String parseMethodName() {
		// get the method name
		JsonObject jobj = new Gson().fromJson(json, JsonObject.class);
		String methodName = jobj.get("methodName").getAsString();
		return methodName;
	}
	
	private RPCResponse purchaseItem() {
		// turn json into an item, extract name
		// wait this isn't what we want. We want to extract the parameters
		JsonObject jobj = new Gson().fromJson(json, JsonObject.class);
		JsonArray params = jobj.get("params").getAsJsonArray();
		String itemName = params.get(0).getAsString();
		int count = params.get(1).getAsInt();
		Double price = BusinessLogic.purchaseItem(itemName, count);

		
		RPCResponse jsonResponse = new RPCResponse();
		if (price < 0) {
			jsonResponse.fault = "Error: not enough items in stock";
			jsonResponse.status = 3; // precondition violation
		}
		else
			jsonResponse.addToResponse(price.toString());
		
		return jsonResponse;
	}
	
	private RPCResponse getItems() {		
		Gson gson = new Gson();
		JsonObject jobj = gson.fromJson(json, JsonObject.class);
		// TODO: figure out if this will be problematic if there is no filter / params is blank
		JsonArray params = jobj.get("params").getAsJsonArray();
		String filter = params.get(0).getAsString();
		
		// get items, turn them into JSONarray
		Collection<Item> coll = BusinessLogic.getItems(filter);
		JsonArray arr = new JsonArray();
		Type itemType = new TypeToken<Item>() {}.getType();
		for(Item item : coll) {
			JsonElement j = gson.toJsonTree(item, itemType);
			arr.add(j);
		}
		
		RPCResponse jsonResponse = new RPCResponse();
		jsonResponse.response = arr;

		return jsonResponse;
	}
	
}
