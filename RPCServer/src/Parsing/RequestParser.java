package Parsing;
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
public class RequestParser {

	/**
	 * Constructor
	 */
	public RequestParser() {
		
	}
	
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
	public String parseMethod(String jsonToParse) {
		return jsonToParse;
	}
	
	/**
	 * Builds the JSON response to getItems given
	 * the response from the business logic package
	 * 
	 * @param jsonToBuild - The string to build into a 
	 * JSON object
	 * 
	 * @return the JSON object but as a string
	 */
	public String buildGetResponse(String jsonToBuild) {
		/**
		 * This method will need to change to accept a JSON
		 * object and turn it into a string, I just haven't
		 * looked at the JSON libraries yet
		 */
		
		return jsonToBuild;
	}
	
	/**
	 * Does the same as buildGetResponse but 
	 * builds response for Purchase method
	 * 
	 * @param jsonToBuild
	 * 
	 * @return the JSON object but as a string
	 */
	public String buildPurchaseResponse(String jsonToBuild) {
		/**
		 * Same comments as above method
		 */
		
		return jsonToBuild;
	}
	
	/**
	 * Does the same as the previous two
	 * methods but for an error occuring
	 * 
	 * @param jsonToBuild
	 * 
	 * @return the JSON object but as a string
	 */
	public String buildErrorResponse(String jsonToBuild) {
		/**
		 * Same comments as above method
		 */
		
		return jsonToBuild;
	}
}
