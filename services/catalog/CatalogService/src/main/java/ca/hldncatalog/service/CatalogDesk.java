package ca.hldncatalog.service;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.hldncatalog.dto.runtime.Order;
import ca.hldncatalog.dto.runtime.Order.OrderState;
import ca.hldncatalog.event.OrderVerifiedEvent;
import ca.hldncatalog.repository.CatalogItemRepository;

@Service
public class CatalogDesk {
	Logger logger = LoggerFactory.getLogger(CatalogDesk.class);
	
	private final AmqpTemplate amqpTemplate;
	private final CatalogItemRepository catalogItemRepository;
	
	@Autowired
	public CatalogDesk(AmqpTemplate amqpTemplate, CatalogItemRepository catalogItemRepository) {    
		this.amqpTemplate = amqpTemplate; 
		this.catalogItemRepository = catalogItemRepository;
	}
	
	public void verifyOrder(Order order)
	{
		logger.info("verifyOrder() order {}", order);
		
		// TODO: actually verify the inventory. loop through order items
		
		order.setState(OrderState.VERIFIED);
		new IntegratedEventDesk(amqpTemplate, catalogItemRepository).send(new OrderVerifiedEvent(order));
	}
}
