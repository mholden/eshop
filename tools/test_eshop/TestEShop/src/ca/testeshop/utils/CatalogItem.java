package ca.testeshop.utils;

//
// Catalog.API -> Model -> CatalogItem.cs
//
public class CatalogItem {
	
	public Integer id;
    public String name;
    public String description;
    public Double price;
    public String pictureFileName;
    public String pictureUri;
    public Integer catalogTypeId;
    //public CatalogType catalogType;
    public Integer catalogBrandId;
    //public CatalogBrand catalogBrand;
    public Integer availableStock;
    public Integer restockThreshold;
    public Integer maxStockThreshold;
    public Boolean onReorder;
	
	CatalogItem() {
		
	}

	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("id: " + id + " name: " + name + " description: " + description + " price: " + price + " catalogTypeId: " + catalogTypeId + " catalogBrandId: " + catalogBrandId);
		output.append("\n");
		return output.toString();
	}
}
