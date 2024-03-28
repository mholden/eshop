package ca.testeshop.utils;

//
// Ordering.API -> Application -> Queries -> OrderViewModel.cs
//
public class OrderItem {

	public String productname;
	public Integer units;
	public Double unitprice;
	public String pictureurl;

	OrderItem() {

	}

	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("productname: " + productname + " units: " + units + " unitprice: " + unitprice + " pictureurl: " + pictureurl);
		output.append("\n");
		return output.toString();
	}
}
