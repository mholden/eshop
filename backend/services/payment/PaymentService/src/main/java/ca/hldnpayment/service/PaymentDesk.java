package ca.hldnpayment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.hldnpayment.dto.persistent.Payment;
import ca.hldnpayment.dto.runtime.Order;
import ca.hldnpayment.dto.runtime.Order.OrderState;
import ca.hldnpayment.event.OrderPaymentSucceededEvent;
import ca.hldnpayment.repository.PaymentRepository;

@Service
public class PaymentDesk {
	Logger logger = LoggerFactory.getLogger(PaymentDesk.class);
	
	private final AmqpTemplate amqpTemplate;
	private final PaymentRepository paymentRepository;

	@Autowired
	public PaymentDesk(AmqpTemplate amqpTemplate, PaymentRepository paymentRepository) {   
		this.amqpTemplate = amqpTemplate;
		this.paymentRepository = paymentRepository;  
	}
	
	public void payForOrder(Order order) throws Exception {
		
		logger.info("payForOrder() order {}", order.getId());
		
		if (!order.getState().equals(OrderState.VERIFIED)) {
			throw new Exception("order must be in verified state");
		}
		
		// TODO: wire in a payment provider like stripe
		
		paymentRepository.save(new Payment(order));
		
		order.setState(OrderState.PAYMENT_SUCCEEDED);
		
		new IntegratedEventDesk(amqpTemplate, paymentRepository).send(new OrderPaymentSucceededEvent(order));
	}
}
