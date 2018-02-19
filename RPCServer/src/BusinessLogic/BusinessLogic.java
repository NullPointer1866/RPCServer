package BusinessLogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class BusinessLogic {

	private static Map<String, Item> database;
	
	public BusinessLogic() {
		database = null;
		ForTesting_DeleteLater();
	}
	
	/**
	 * Initialize the database and put a few items in it just for testing
	 */
	private static void ForTesting_DeleteLater() {
		database = new HashMap<String, Item>();
		Item socks = new Item("socks", 12, 2.5);
		database.put("socks", socks);
		
		Item shoes = new Item("shoes", 5, 15.0);
		database.put("shoes", shoes);
	}
	

	public static Collection<Item> getItems(String filter) {
		if (filter == null)
			return database.values();
		
		Collection<Item> items = new ArrayList<Item>();
		
		for (Entry<String, Item> item : database.entrySet()) {
			if (item.getKey().contains(filter)) {
				items.add(item.getValue());
			}
		}
		
		return items;
	}
	
/**
 * Purchases count of the specified item and decreases the stock accordingly
 * @param name The name of the item the client would like to purchase
 * @param count The amount of the item the client would like to purchase
 * @return The price of the purchase or -1 if the parameters are invalid.
 */
	public static double purchaseItem(String name, int count) {
		Item item = database.remove(name);
		if (item == null) {
			return -1;
		}
		int current = item.getStock();
		if (current < count) {
			database.put(name, item);
			return -1;
		}
		
		item.decreaseQuantity(count);
		double price = item.getPrice() * count;
		database.put(name, item);
		
		update();
		return price;
	}

	//update the database every time we change anything
	// for now, update will just write our dictionary back to the flatfile, but if we want to get fancy, we can implement a cooler method
	private static void update() {
		// TODO fill out method
		// TODO determine if this method should be private / where it would be called
		
	}
}
