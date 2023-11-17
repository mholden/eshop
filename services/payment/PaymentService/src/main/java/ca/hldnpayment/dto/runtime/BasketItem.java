package ca.hldnpayment.dto.runtime;

public class BasketItem {
    private int id;
    private String userId;
    private Integer catalogItemId; 

    public BasketItem(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public Integer getCatalogItemId() {
        return catalogItemId;
    }

    public void setCatalogItemId(Integer catalogItemId) {
        this.catalogItemId = catalogItemId;
    }
    
    public String toString() {
    	return String.format("id: %d userId: %s catalogItemId: %d", id, userId, catalogItemId);
    }
}
