package ca.hldnbasket.controller;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import ca.hldnbasket.dto.BasketItem;
import ca.hldnbasket.event.CheckoutEvent;
import ca.hldnbasket.repository.BasketItemRepository;
import ca.hldnbasket.service.IntegratedEventDesk;

@RestController
@RequestMapping("/basket")
public class BasketController {
	
	Logger logger = LoggerFactory.getLogger(BasketController.class); 
	
	private final AmqpTemplate amqpTemplate;
	private final BasketItemRepository basketItemRepository;
	
	@Autowired
	public BasketController(AmqpTemplate amqpTemplate, BasketItemRepository basketItemRepository) {    
		this.amqpTemplate = amqpTemplate;
		this.basketItemRepository = basketItemRepository;  
	}

	@GetMapping("/ping")
    public String ping() {
		logger.info("ping()");
		return "Ping successful!\n";
    }
	
	@GetMapping("/items")
	public List<BasketItem> getBasketItems() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
		String userId = oidcUser.getAttribute("sub");
		List<BasketItem> basketItems = null;

		logger.info("getBasketItems() user " + userId);

		basketItems = basketItemRepository.findByUserId(userId);

		logger.info("getBasketItems() got items " + basketItems);

		return basketItems;
	}
	
	@PostMapping("/items")
	@Transactional
	public void setBasketItems(@RequestBody List<BasketItem> basketItems) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
		String userId = oidcUser.getAttribute("sub");

		logger.info("setBasketItems() user " + userId);

		// delete all existing
		basketItemRepository.deleteAll(basketItemRepository.findByUserId(userId));

		// save the new basket
		for (BasketItem basketItem : basketItems) {
			basketItem.setUserId(userId);
		}

		logger.info("setBasketItems() saving new items " + basketItems);

		basketItemRepository.saveAll(basketItems);
	}
	
	@PostMapping("/checkout")
	public void checkout() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
		String userId = oidcUser.getAttribute("sub");
		List<BasketItem> basketItems = null;
		
		logger.info("checkout() user " + userId);
		
		basketItems = basketItemRepository.findByUserId(userId);

		logger.info("checkout() basketItems " + basketItems);
		
		new IntegratedEventDesk(amqpTemplate).send(new CheckoutEvent(userId, basketItems));
	}
}
