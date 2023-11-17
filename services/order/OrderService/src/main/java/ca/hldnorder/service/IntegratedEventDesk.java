package ca.hldnorder.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.hldnorder.event.CheckoutEvent;
import ca.hldnorder.event.IntegratedEvent;
import ca.hldnorder.event.OrderVerifiedEvent;
import ca.hldnorder.repository.OrderRepository;

@Service
public class IntegratedEventDesk {
	
	Logger logger = LoggerFactory.getLogger(IntegratedEventDesk.class);
	
	private final AmqpTemplate amqpTemplate;
	private final OrderRepository orderRepository;
	
	@Autowired
	public IntegratedEventDesk(AmqpTemplate amqpTemplate, OrderRepository orderRepository) {    
		this.amqpTemplate = amqpTemplate;
		this.orderRepository = orderRepository;  
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
	
	private void handleCheckoutEvent(CheckoutEvent checkoutEvent) throws Exception {
		logger.info("handleCheckoutEvent() checkoutEvent {}", checkoutEvent);
		new OrderDesk(amqpTemplate, orderRepository).createOrder(checkoutEvent.userId, checkoutEvent.basketItems);
	}
	
	private void handleOrderVerifiedEvent(OrderVerifiedEvent orderVerifiedEvent) throws Exception {
		logger.info("handleOrderVerifiedEvent() orderVerifiedEvent {}", orderVerifiedEvent);
		new OrderDesk(amqpTemplate, orderRepository).updateOrder(orderVerifiedEvent.getOrder());
	}
	
	@RabbitListener(queues = "${ca.hldn.order.rabbitmq.queue}")
	@Transactional
	public void receive(IntegratedEvent event) throws Exception {
		
		logger.info("receive() received event " + event.getEventType());
		
		switch (event.getEventType()) {
			case "CheckoutEvent":
				handleCheckoutEvent((CheckoutEvent)event);
				break;
			case "OrderVerifiedEvent":
				handleOrderVerifiedEvent((OrderVerifiedEvent)event);
				break;
			default:
				logger.info("receive() no handler for event of type " + event.getEventType());
				break;
		}
	}
}