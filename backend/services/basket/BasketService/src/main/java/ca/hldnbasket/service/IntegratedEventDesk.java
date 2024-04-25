package ca.hldnbasket.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.hldnbasket.event.*;
import ca.hldnbasket.event.OrderVerifiedEvent;
import ca.hldnbasket.repository.BasketRepository;

@Service
public class IntegratedEventDesk {
	
	Logger logger = LoggerFactory.getLogger(IntegratedEventDesk.class);
	
	private final AmqpTemplate amqpTemplate;
	private final BasketRepository basketRepository;

	@Autowired
	public IntegratedEventDesk(AmqpTemplate amqpTemplate, BasketRepository basketRepository) {
		this.amqpTemplate = amqpTemplate;
		this.basketRepository = basketRepository;
	}
	
	/*
	 * Sending:
	 */

	private String exchange = "ca.hldn.eshop.exchange";
	
	public void send(IntegratedEvent event) {
		logger.info("send() sending event {}", event.getEventType());
		amqpTemplate.convertAndSend(exchange, event.getEventType(), event);
	}
	
	/*
	 * Receiving:
	 */
	
	private void handleOrderVerifiedEvent(OrderVerifiedEvent orderVerifiedEvent) throws Exception {
		logger.info("handleOrderVerifiedEvent() orderVerifiedEvent {}", orderVerifiedEvent);
		new BasketDesk(amqpTemplate, basketRepository).updateBasket(orderVerifiedEvent.getOrder());
	}
	
	@RabbitListener(queues = "${ca.hldn.basket.rabbitmq.queue}")
	@Transactional
	public void receive(IntegratedEvent event) throws Exception {
		
		logger.info("receive() received event " + event.getEventType());
		
		switch (event.getEventType()) {
			case "OrderVerifiedEvent":
				handleOrderVerifiedEvent((OrderVerifiedEvent)event);
				break;
			default:
				logger.info("receive() no handler for event of type {}", event.getEventType());
				break;
		}
	}
}