package ca.hldnbasket.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.*;

import ca.hldnbasket.dto.Basket;
import ca.hldnbasket.dto.BasketItem;
import ca.hldnbasket.repository.BasketItemRepository;
import ca.hldnbasket.service.RabbitMQPublisher;

@RestController
@RequestMapping("/basket")
public class BasketController {
	
	@Autowired
    BasketItemRepository basketItemRepository;
	
	@Autowired
	RabbitMQPublisher rabbitMQPublisher;

	@GetMapping("/ping")
    public String ping() {
		System.out.println("ping()");
		return "Ping successful!\n";
    }
	
	@GetMapping("/items")
	public List<BasketItem> getBasketItems() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
		String userId = oidcUser.getAttribute("sub");
		List<BasketItem> basketItems = null;

		System.out.println("getBasketItems() user " + userId);

		basketItems = basketItemRepository.findByUserId(userId);

		System.out.println("getBasketItems() got items " + basketItems);

		return basketItems;
	}
	
	@PostMapping("/items")
	public void setBasketItems(@RequestBody List<BasketItem> basketItems) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
		String userId = oidcUser.getAttribute("sub");

		System.out.println("setBasketItems() user " + userId);

		// delete all existing
		basketItemRepository.deleteAll(basketItemRepository.findByUserId(userId));

		// save the new basket
		for (BasketItem basketItem : basketItems) {
			basketItem.setUserId(userId);
		}

		System.out.println("setBasketItems() saving new items " + basketItems);

		basketItemRepository.saveAll(basketItems);
	}
	
	@PostMapping("/checkout")
	public void checkout() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
		String userId = oidcUser.getAttribute("sub");
		
		System.out.println("checkout() user " + userId);
		
		rabbitMQPublisher.send("CheckoutEvent:" + userId);
	}
}
