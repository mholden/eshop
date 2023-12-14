package ca.hldnbasket.controller;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import ca.hldnbasket.dto.*;
import ca.hldnbasket.event.CheckoutEvent;
import ca.hldnbasket.repository.BasketRepository;
import ca.hldnbasket.service.IntegratedEventDesk;

@RestController
@RequestMapping("/basket")
public class BasketController {
	
	Logger logger = LoggerFactory.getLogger(BasketController.class); 
	
	private final AmqpTemplate amqpTemplate;
	private final BasketRepository basketRepository;
	
	@Autowired
	public BasketController(AmqpTemplate amqpTemplate, BasketRepository basketRepository) {    
		this.amqpTemplate = amqpTemplate;
		this.basketRepository = basketRepository;  
	}

	@GetMapping("/ping")
    public String ping() {
		logger.info("ping()");
		return "Ping successful!\n";
    }
	
	// TODO: move this to an identity service
	@GetMapping("/userInfo")
    public OidcUserInfo getUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
		String userId = oidcUser.getAttribute("sub");
		OidcUserInfo userInfo = oidcUser.getUserInfo();
		
		logger.info("getUserInfo() user {}", userId);
		
		userInfo = oidcUser.getUserInfo();
		
		logger.info("getUserInfo() userInfo {}", userInfo);
		
		return userInfo;
    }
	
	@GetMapping("/items")
	public List<BasketItem> getBasketItems() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
		String userId = oidcUser.getAttribute("sub");
		Basket basket;
		List<BasketItem> basketItems = null;

		logger.info("getBasketItems() user {}", userId);

		basket = basketRepository.findById(userId).orElse(null);
		if (basket != null) {
			basketItems = basket.getBasketItems();
		}
		if (basketItems == null) {
			basketItems = new LinkedList<BasketItem>();
		}

		logger.info("getBasketItems() got items " + basketItems);

		return basketItems;
	}
	
	@PostMapping("/items")
	@Transactional
	public void setBasketItems(@RequestBody List<BasketItem> basketItems) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
		String userId = oidcUser.getAttribute("sub");

		logger.info("setBasketItems() user {}", userId);

		// save the new basket
		for (BasketItem basketItem : basketItems) {
			basketItem.setUserId(userId);
		}

		logger.info("setBasketItems() saving new items " + basketItems);

		basketRepository.save(new Basket(userId, basketItems));
	}
	
	@PostMapping("/checkout")
	public void checkout() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
		String userId = oidcUser.getAttribute("sub");
		Basket basket;
		List<BasketItem> basketItems = null;
		
		logger.info("checkout() user {}", userId);
		
		basket = basketRepository.findById(userId).orElse(null);
		if (basket != null) {
			basketItems = basket.getBasketItems();
		}

		logger.info("checkout() basketItems " + basketItems);
		
		if (basketItems != null) {
			new IntegratedEventDesk(amqpTemplate).send(new CheckoutEvent(userId, basketItems));
		}
	}
}
