package ca.testeshop.utils;

// ca.hldncatalog.dto.CatalogItem
public class CatalogItem {

	public Integer id;
	public String name;
	public Integer price;
	public String imageId;

	public CatalogItem() {

	}
	
	public CatalogItem(String name, Integer price, String imageId) {
		this.name = name;
		this.price = price;
		this.imageId = imageId;
	}

	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("id: " + id + " name: " + name + " price: " + price + " imageId: " + imageId);
		output.append("\n");
		return output.toString();
	}
}
