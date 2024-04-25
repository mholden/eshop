package ca.hldnbasket.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.hldnbasket.dto.persistent.Basket;
import ca.hldnbasket.dto.runtime.Order;
import ca.hldnbasket.event.CheckoutSuccessfulEvent;
import ca.hldnbasket.repository.BasketRepository;

@Service
public class BasketDesk {
	Logger logger = LoggerFactory.getLogger(BasketDesk.class);
	
	private final AmqpTemplate amqpTemplate;
	private final BasketRepository basketRepository;
	
	@Autowired
	public BasketDesk(AmqpTemplate amqpTemplate, BasketRepository basketRepository) {    
		this.amqpTemplate = amqpTemplate; 
		this.basketRepository = basketRepository;
	}
	
	private void markBasketCheckoutAsSuccessful(Basket basket) {
		
		logger.info("markBasketCheckoutAsSuccessful() user {}", basket.getUserId());
		
		basket.getBasketItems().clear();
		basketRepository.save(basket);
		
		new IntegratedEventDesk(amqpTemplate, basketRepository).send(new CheckoutSuccessfulEvent(basket));
	}
	
	// updates the basket associated with this order
	public void updateBasket(Order order) throws Exception {
		Basket basket;
		
		logger.info("updateBasket(Order) user {} order {}", order.getUserId(), order);
		
		basket = basketRepository.findById(order.getUserId()).orElse(null);
		
		switch (order.getState()) {
			case VERIFIED:
				markBasketCheckoutAsSuccessful(basket);
				break;
			default:
				throw new Exception("unexpected order state " + order.getState());
		}
	}
}
