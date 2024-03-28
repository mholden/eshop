package ca.hldnpayment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.hldnpayment.event.IntegratedEvent;
import ca.hldnpayment.event.OrderPaymentInitiatedEvent;
import ca.hldnpayment.repository.PaymentRepository;

@Service
public class IntegratedEventDesk {
	
	Logger logger = LoggerFactory.getLogger(IntegratedEventDesk.class);
	
	private final AmqpTemplate amqpTemplate;
	private final PaymentRepository paymentRepository;
	
	@Autowired
	public IntegratedEventDesk(AmqpTemplate amqpTemplate, PaymentRepository paymentRepository) {    
		this.amqpTemplate = amqpTemplate;
		this.paymentRepository = paymentRepository;  
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
	
	private void handleOrderPaymentInitiatedEvent(OrderPaymentInitiatedEvent orderPaymentInitiatedEvent) throws Exception {
		logger.info("handleOrderPaymentInitiatedEvent() orderPaymentInitiatedEvent {}", orderPaymentInitiatedEvent);
		new PaymentDesk(amqpTemplate, paymentRepository).payForOrder(orderPaymentInitiatedEvent.getOrder());
	}
	
	@RabbitListener(queues = "${ca.hldn.payment.rabbitmq.queue}")
	@Transactional
	public void receive(IntegratedEvent event) throws Exception {
		
		logger.info("receive() received event " + event.getEventType());
		
		switch (event.getEventType()) {
			case "OrderPaymentInitiatedEvent":
				handleOrderPaymentInitiatedEvent((OrderPaymentInitiatedEvent)event);
				break;
			default:
				logger.info("receive() no handler for event of type " + event.getEventType());
				break;
		}
	}
}