package ca.testeshop.utils;

//
// Ordering.API -> Application -> Queries -> OrderViewModel.cs
//
public class OrderSummary {

	public Integer ordernumber;
	public String date;
	public String status;
	public Double total;

	OrderSummary() {

	}

	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("ordernumber: " + ordernumber + " date: " + date + " status: " + status + " total: " + total);
		output.append("\n");
		return output.toString();
	}
}
