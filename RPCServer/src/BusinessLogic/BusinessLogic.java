package BusinessLogic;

import java.util.Collection;
import java.util.Map;

public class BusinessLogic {

	private static Map<String, Item> database;
	
	public BusinessLogic() {
		database = null;
	}
	
	public static Collection<Item> getItems(String filter) {
		// the first time we call this we will read in our items from our database
		if (filter == null)
			return database.values();
		// TODO: otherwise, return all values that have "filter" in the name
		
		return null;
		
	}
	
	public static double purchaseItem(String name, int count) {
		Item item = database.remove(name);
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
		// TODO Auto-generated method stub
		
	}
}
