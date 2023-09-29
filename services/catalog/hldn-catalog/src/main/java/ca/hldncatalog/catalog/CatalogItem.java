package ca.hldncatalog.catalog;

import jakarta.persistence.*;

@Entity
@Table(name = "catalogitem")
public class CatalogItem {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Integer price; // in cents

    public CatalogItem(){

    }

    public CatalogItem(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
    
    public String toString() {
    	return String.format("id: %d name %s price %d.%d", id, name, price / 100, price % 100);
    }
}
