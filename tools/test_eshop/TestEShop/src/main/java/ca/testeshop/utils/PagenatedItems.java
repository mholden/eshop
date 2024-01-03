package ca.testeshop.utils;

import java.util.*;

//
// Catalog.API -> ViewModel -> PagenatedItemsViewModel.cs
//
public class PagenatedItems<T> {

	public Integer pageIndex;
	public Integer pageSize;
	public Long count;
	public List<T> data;

	PagenatedItems() {

	}

	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("pageIndex: " + pageIndex + " pageSize: " + pageSize + " count: " + count);
		output.append("\n");
		output.append("data:");
		output.append("\n");
		if (data != null) {
			for (T item : data) {
				output.append(item);
			}
		}
		return output.toString();
	}
}
