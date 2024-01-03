package ca.testeshop.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

//
// Basket.API -> Model -> BasketCheckout.cs
//
public class BasketCheckout {
	
	public String City;
	public String Street;
	public String State;
	public String Country;
	public String ZipCode;
	public String CardNumber;
	public String CardHolderName;
	public String CardExpiration;
	public String CardSecurityNumber;
	public Integer CardTypeId;
	public String Buyer;
	public String RequestId;

	public BasketCheckout() {

	}
	
	public BasketCheckout(UserInfo userInfo) {
		ZonedDateTime cardExpiration;
		this.City = userInfo.address_city;
		this.Street = userInfo.address_street;
		this.State = userInfo.address_state;
		this.Country = userInfo.address_country;
		this.ZipCode = userInfo.address_zip_code;
		this.CardNumber = userInfo.card_number;
		this.CardHolderName = userInfo.card_holder;
		//this.CardExpiration = userInfo.card_expiration;
		//this.CardExpiration = "2025-01-01T05:00:00.000Z"; // where is this coming from? orderingdb.paymentmethods apparently.. but i think front end is just hardcoding it.. mapBasketInfoCheckout
		// this.order.cardexpiration = new Date(20 + this.newOrderForm.controls['expirationdate'].value.split('/')[1], this.newOrderForm.controls['expirationdate'].value.split('/')[0]);
		cardExpiration = ZonedDateTime.of(Integer.valueOf("20" + userInfo.card_expiration.split("/")[1]), Integer.valueOf(userInfo.card_expiration.split("/")[0]), 1, 0, 0, 0, 0, ZoneId.systemDefault());
		this.CardExpiration = cardExpiration.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneId.of("UTC")));
		this.CardSecurityNumber = userInfo.card_security_number;
		this.CardTypeId = 1; // not sure where/when f/e gets this either.. it's not in the getUserInfo response.. front end just hardcodes it in mapBasketInfoCheckout
		this.RequestId = UUID.randomUUID().toString();
	}
}
