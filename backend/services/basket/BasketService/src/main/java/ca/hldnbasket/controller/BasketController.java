package ca.hldnbasket.controller;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
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
	public Map<String, Object> getUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Jwt jwt = (Jwt)authentication.getPrincipal();
		String userId = jwt.getSubject();

		logger.info("getUserInfo() user {}", userId);
		
		/*
		for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
			logger.info("getUserInfo() user has grantedAuthority (ie. role) {}", grantedAuthority.getAuthority());
		}
		*/

		return jwt.getClaims();
	}

	@GetMapping("/items")
	public List<BasketItem> getBasketItems() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Jwt jwt = (Jwt)authentication.getPrincipal();
		String userId = jwt.getSubject();
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
	public List<BasketItem> setBasketItems(@RequestBody List<BasketItem> basketItems) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Jwt jwt = (Jwt)authentication.getPrincipal();
		String userId = jwt.getSubject();

		logger.info("setBasketItems() user {}", userId);

		// save the new basket
		for (BasketItem basketItem : basketItems) {
			basketItem.setUserId(userId);
		}

		logger.info("setBasketItems() saving new items " + basketItems);

		basketRepository.save(new Basket(userId, basketItems));
		
		return basketItems;
	}

	@PostMapping("/checkout")
	public void checkout() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Jwt jwt = (Jwt)authentication.getPrincipal();
		String userId = jwt.getSubject();
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
