package ca.testeshop.utils;

// ca.hldncatalog.dto.CatalogItem
public class CatalogItem {

	public Integer id;
	public String name;
	public Integer price;

	CatalogItem() {

	}

	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("id: " + id + " name: " + name + " price: " + price);
		output.append("\n");
		return output.toString();
	}
}
