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
		String response;
		if (methodName == "purchaseItem") {
			response = purchaseItem();
		}
		else if (methodName == "getItems") {
			response = getItems();
		}
		else
			response = null;
		return response;			
	}

	private String purchaseItem() {
		// turn json into an item, extract name
		// wait this isn't what we want. We want to extract the parameters
		JsonObject jobj = new Gson().fromJson(json, JsonObject.class);
		String itemName = jobj.get("name").getAsString();
		int count = jobj.get("count").getAsInt();
		Double price = BusinessLogic.purchaseItem(itemName, count);
		String response;
		if (price < 0)
			response = "Error: not enough items in stock";
		else
			response = price.toString();

		
		return response;
	}
	
	private String getItems() {
		JsonObject jobj = new Gson().fromJson(json, JsonObject.class);
		String filter = jobj.get("filter").getAsString();
		// get items, turn them into JSON
		Collection<Item> dict = BusinessLogic.getItems(filter);
		Type collType = new TypeToken<Collection<Item>>() {}.getType();
		String response = new Gson().toJson(dict, collType);
		// TODO: check string form
		return response;
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
