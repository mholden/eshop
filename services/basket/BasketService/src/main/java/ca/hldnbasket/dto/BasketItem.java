package ca.hldnbasket.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "basketitem")
public class BasketItem {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Integer userId;
    private Integer catalogItemId; 

    public BasketItem(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public Integer getCatalogItemId() {
        return catalogItemId;
    }

    public void setCatalogItemId(Integer catalogItemId) {
        this.catalogItemId = catalogItemId;
    }
    
    public String toString() {
    	return String.format("id: %d userId: %d catalogItemId: %d", id, userId, catalogItemId);
    }
}