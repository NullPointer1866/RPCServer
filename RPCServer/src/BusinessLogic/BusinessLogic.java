package BusinessLogic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class BusinessLogic {

	private static Map<String, Item> database;

	public static synchronized Collection<Item> getItems(String filter) {
		if (filter == null)
			return database.values();
		filter = filter.toLowerCase();
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
 * @throws IOException 
 */
	public static synchronized double purchase(String name, int count) throws IOException {
    name = name.toLowerCase();
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
	private static synchronized void update() throws IOException {
		try(FileWriter fw = new FileWriter("src/flatFileDB.json")) {
			Gson gson = new Gson();
			
			String dbstring = gson.toJson(database);
			
			fw.write(dbstring);
			
		} catch (IOException e) {
			System.out.println("There was a problem updating the DB");
			e.printStackTrace();
		}
		
	}

	public static void initDB() throws FileNotFoundException {
		
		try(BufferedReader br = new BufferedReader(new FileReader("src/flatFileDB.json"))){
			
			Gson gson = new Gson();
			
			database = gson.fromJson(br, new TypeToken<HashMap<String, Item>>() {}.getType());
			System.out.println(database);
		} catch (IOException e) {
			
			System.out.println("There was a problem initing the DB");
			e.printStackTrace();
		}
		
	}
	
	
}
