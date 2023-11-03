package ca.hldnbasket.dto;

import java.util.List;

public class Basket {
    private List<BasketItem> basketItems;

    public Basket() {

    }
    
    public Basket(List<BasketItem> basketItems) {
    	this.basketItems = basketItems;
    }
    
    public  List<BasketItem> getBasketItems() {
        return basketItems;
    }

    public void setBasketItems(List<BasketItem> basketItems) {
        this.basketItems = basketItems;
    }
    
    public String toString() {
    	return String.format("basketItems: {}", basketItems);
    }
}
