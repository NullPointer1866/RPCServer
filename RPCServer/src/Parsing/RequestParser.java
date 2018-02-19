package Parsing;

import java.util.Collection;
import com.google.gson.*;
import BusinessLogic.BusinessLogic;
import BusinessLogic.Item;

public class RequestParser {

	private String json;

	
	public RequestParser() {	}

	/**
	 * Calls and returns the method specified in jsonString
	 * @param jsonString the client request
	 * @return the JSON payload to be sent back to the client
	 */
	public String getResponse(String jsonString) {
		json = jsonString;
		String method = parseMethodName();
		String response = callMethod(method);

		return response;
	}

/**
 * Calls the method given by methodName in business logic
 * @param methodName
 * @return the JSON payload to be sent back to the client
 */
	private String callMethod(String methodName) {
		RPCResponse response;

		if (methodName.equals("purchase")) {
			response = purchase();
		}
		else if (methodName.equals("getItems")) {
			response = getItems();
		}
		else
			response = new RPCResponse();

		return response.toJson();
	}

	/** Determines name of method to call
	 * 
	 * @return the name of the method to call
	 */
	public String parseMethodName() {
		// get the method name
		JsonParser parser = new JsonParser();
		JsonObject o = parser.parse(json).getAsJsonObject();

		String methodName = o.get("methodName").getAsString();
		return methodName;
	}

	private RPCResponse purchase() {
		// turn json into an item, extract name
		RPCResponse jsonResponse = new RPCResponse();
		
		JsonParser parser = new JsonParser();
		JsonObject jobj = parser.parse(json).getAsJsonObject();
		JsonElement arguments = jobj.get("params");
		if (arguments == null || arguments.getAsJsonArray().size() != 2) {
			// 2: missing parameter
			jsonResponse.fault = "Error: Incorrect Parameters. Please include the name and amount (an integer) of the item you would like to purchase.";
			jsonResponse.status = 2;
			return jsonResponse;
		}
		else {
			JsonArray params = jobj.get("params").getAsJsonArray();
			try {
				String itemName = params.get(0).getAsString();
				int count = params.get(1).getAsInt();
				Double price = BusinessLogic.purchase(itemName, count);

				if (price < 0) {
					jsonResponse.fault = "Error: Precondition violation. Incorrect item name or not enough items in stock";
					jsonResponse.status = 3; // precondition violation
				}
				else
					jsonResponse.addToResponse(price);

			}
			catch (NumberFormatException e) {
				jsonResponse.fault = "Error: Parameter type issue. Please include the name and amount (an integer) of the item you would like to purchase.";
				jsonResponse.status = 1; // param type issue
				
			}
			
			return jsonResponse;			
		}
	}

	private RPCResponse getItems() {		
		JsonParser parser = new JsonParser();
		JsonObject jobj = parser.parse(json).getAsJsonObject();

		JsonElement arguments = jobj.get("params");
		String filter;
		if (arguments == null || arguments.getAsJsonArray().size() == 0) {
			filter = null;
		}
		else {
			JsonArray params = jobj.get("params").getAsJsonArray();
			filter = params.get(0).getAsString();
		}

		Collection<Item> coll = BusinessLogic.getItems(filter);

		RPCResponse jsonResponse = new RPCResponse();
		for(Item item : coll) {
			jsonResponse.addToResponse(item);
		}

		return jsonResponse;
	}

}
