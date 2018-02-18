package Parsing;
import java.util.Collection;
import java.util.Dictionary;
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

/**
 * 
 * Handles the parsing of JSON responses 
 * either turning strings into JSON or 
 * vice-versa
 * 
 * build methods may be able to be replaced
 * by a single method if turning JSON into String
 * is trivial.
 *
 * If these methods are declared static, it may help
 * with asynchronous access. Static methods can be
 * declared "synchronized" and only one thread can
 * acess that method at a time, holds true for entire class 
 * type rather than instance.
 */
	
	
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
		
		Gson g = new Gson();
		return g.toJson(response);
	}

	private RPCResponse purchaseItem() {
		// turn json into an item, extract name
		// wait this isn't what we want. We want to extract the parameters
		JsonObject jobj = new Gson().fromJson(json, JsonObject.class);
		JsonArray params = jobj.get("params").getAsJsonArray();
		String itemName = params.get(0).getAsString();
		int count = params.get(1).getAsInt();
		Double price = BusinessLogic.purchaseItem(itemName, count);
		String response;
		
		RPCResponse jsonResponse = new RPCResponse();
		JsonArray arr = new JsonArray();
		
		
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
		for(Item item : coll) {
			JsonElement j = gson.toJsonTree(item);
			arr.add(j);
		}
		
		RPCResponse jsonResponse = new RPCResponse();
		jsonResponse.response = arr;

		return jsonResponse;
	}
	
	// determines name of method to call and calls method in business logic
    	/**
	 * Parses JSON entity sent by client 
	 * Determines what method they are calling and calls it
	 * Should eventually return the JSON response back to the server
	 * (Return chain comes out through this method)
	 * 
	 * @param jsonToParse - The string representation of a 
	 * JSON object to parse
	 * 
	 * @return the JSON response to the method query 
	 * formatted as a string for entity packing
	 */
	public String parseMethodName() {
		// get the method name
		JsonObject jobj = new Gson().fromJson(json, JsonObject.class);
		String methodName = jobj.get("methodName").getAsString();
		return methodName;
	}
}
