package ca.hldncatalog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.hldncatalog.event.*;
import ca.hldncatalog.repository.CatalogItemRepository;

@Service
public class IntegratedEventDesk {
	
	Logger logger = LoggerFactory.getLogger(IntegratedEventDesk.class);
	
	private final AmqpTemplate amqpTemplate;
	private final CatalogItemRepository catalogItemRepository;
	
	@Autowired
	public IntegratedEventDesk(AmqpTemplate amqpTemplate, CatalogItemRepository catalogItemRepository) {    
		this.amqpTemplate = amqpTemplate; 
		this.catalogItemRepository = catalogItemRepository;
	}
	
	/*
	 * Sending:
	 */

	private String exchange = "ca.hldn.eshop.exchange";
	
	public void send(IntegratedEvent event) {
		logger.info("send() sending event " + event.getEventType());
		amqpTemplate.convertAndSend(exchange, event.getEventType(), event);
	}
	
	/*
	 * Receiving:
	 */
	
	private void handleOrderInitiatedEvent(OrderInitiatedEvent orderInitiatedEvent) {
		logger.info("handleOrderInitiatedEvent() handleOrderInitiatedEvent {}", orderInitiatedEvent);
		new CatalogDesk(amqpTemplate, catalogItemRepository).verifyOrder(orderInitiatedEvent.getOrder());
	}
	
	@RabbitListener(queues = "${ca.hldn.catalog.rabbitmq.queue}")
	public void receive(IntegratedEvent event) {
		
		logger.info("receive() received event " + event.getEventType());
		
		switch (event.getEventType()) {
			case "OrderInitiatedEvent":
				handleOrderInitiatedEvent((OrderInitiatedEvent)event);
				break;
			default:
				logger.info("receive() no handler for event of type " + event.getEventType());
				break;
		}
	}
}