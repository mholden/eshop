package ca.hldnorder.service;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.hldnorder.dto.persistent.Order;
import ca.hldnorder.dto.persistent.Order.OrderState;
import ca.hldnorder.dto.runtime.BasketItem;
import ca.hldnorder.event.OrderInitiatedEvent;
import ca.hldnorder.event.OrderPaymentInitiatedEvent;
import ca.hldnorder.repository.OrderRepository;

@Service
public class OrderDesk {
	Logger logger = LoggerFactory.getLogger(OrderDesk.class);
	
	private final AmqpTemplate amqpTemplate;
	private final OrderRepository orderRepository;

	@Autowired
	public OrderDesk(AmqpTemplate amqpTemplate, OrderRepository orderRepository) {   
		this.amqpTemplate = amqpTemplate;
		this.orderRepository = orderRepository;  
	}
	
	public void createOrder(String userId, List<BasketItem> basketItems) {
		Order order;
		
		logger.info("createOrder() userId {} basketItems {}", userId, basketItems);
		
		// TODO: create and save the order items from the basket items
		
		order = new Order();
		order.setUserId(userId);
		order.setCreationTime(new Timestamp(System.currentTimeMillis()));
		order.setState(OrderState.INITIATED);
		order = orderRepository.save(order);
		
		new IntegratedEventDesk(amqpTemplate, orderRepository).send(new OrderInitiatedEvent(order));
	}
	
	private void updateInitiatedOrder(Order existingOrder, Order order) throws Exception {
		
		logger.info("updateInitiatedOrder() existingOrder {}", existingOrder);
		
		if (!order.getState().equals(OrderState.VERIFIED)) {
			throw new Exception("initiated order can only move to verified state");
		}
		
		existingOrder.setState(order.getState());
		existingOrder = orderRepository.save(existingOrder);
		
		// initiate payment
		new IntegratedEventDesk(amqpTemplate, orderRepository).send(new OrderPaymentInitiatedEvent(existingOrder));
	}
	
	// TODO: update order state and initiate payment
	public void updateOrder(Order order) throws Exception {
		Order existingOrder;
		
		logger.info("updateOrder() order {}", order.getId());
		
		existingOrder = orderRepository.getReferenceById(order.getId());
		switch (existingOrder.getState()) {
			case INITIATED:
				updateInitiatedOrder(existingOrder, order);
				break;
			default:
				throw new Exception("updatint order in state " + existingOrder.getState() + " not yet supported");
		}
	}
}
