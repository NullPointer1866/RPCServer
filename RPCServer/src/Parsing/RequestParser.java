package Parsing;
import java.util.ArrayList;
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

		if (methodName.equals("purchaseItem")) {
			response = purchaseItem();
		}
		else if (methodName.equals("getItems")) {
			response = getItems();
		}
		else
			response = new RPCResponse();

		return response.toJson();
	}

	// determines name of method to call and calls method in business logic
	public String parseMethodName() {
		// get the method name
		JsonParser parser = new JsonParser();
		JsonObject o = parser.parse(json).getAsJsonObject();

		String methodName = o.get("methodName").getAsString();
		return methodName;
	}

	private RPCResponse purchaseItem() {
		// turn json into an item, extract name
		RPCResponse jsonResponse = new RPCResponse();
		
		JsonParser parser = new JsonParser();
		JsonObject jobj = parser.parse(json).getAsJsonObject();
		JsonElement arguments = jobj.get("params");
		if (arguments == null || arguments.getAsJsonArray().size() != 2) {
			// 2: missing parameter
			jsonResponse.fault = "Error: Missing Parameters. Please include the name and amount (an integer) of the item you'd like to purchase.";
			jsonResponse.status = 2;
			return jsonResponse;
		}
		else {
			JsonArray params = jobj.get("params").getAsJsonArray();

			// now if we have an error with these, it's
			// status 1: parameter type issue
			// TODO: handle this possibility
			String itemName = params.get(0).getAsString();
			int count = params.get(1).getAsInt();
			Double price = BusinessLogic.purchaseItem(itemName, count);

			if (price < 0) {
				jsonResponse.fault = "Error: not enough items in stock";
				jsonResponse.status = 3; // precondition violation
			}
			else
				jsonResponse.addToResponse(price);

			return jsonResponse;
		}
	}

	private RPCResponse getItems() {		
		Gson gson = new Gson();
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

		Type itemType = new TypeToken<Item>() {}.getType();
		RPCResponse jsonResponse = new RPCResponse();
		for(Item item : coll) {
			//String j = gson.toJson(item, itemType);
			jsonResponse.addToResponse(item);
		}

		return jsonResponse;
	}

}
