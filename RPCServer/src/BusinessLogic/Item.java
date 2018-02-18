package BusinessLogic;

public class Item {

	private String name;
	private int stock;
	private double price;

	public Item(String name, int stock, double price) {
		this.name = name;
		this.stock = stock;
		this.price = price;
	}
	
	public int getStock() {
		return stock;
	}
	
	public double getPrice() {
		return price;
	}
	
	/// decreases the quantity of the Item by amount
	// returns the updated quantity.
	protected int decreaseQuantity(int amount) {
		stock -= amount;
		return stock;
	}
	
	
	
}
