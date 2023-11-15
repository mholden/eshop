package ca.hldnpayment.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.hldnpayment.event.IntegrationEvent;

@Service
public class RabbitMQConsumer {
	
	@Autowired
	RabbitMQPublisher rabbitMQPublisher;
	
	@RabbitListener(queues = "${ca.hldn.payment.rabbitmq.queue}")
	public void recieve(IntegrationEvent event) {
		
		System.out.println("recieve() received event " + event.getEventType());
		
		switch (event.getEventType()) {
			case "CheckoutEvent":
				System.out.println("receive() handling CheckoutEvent: " + event);
				break;
			default:
				System.out.println("receive() no handler for message of type " + event.getEventType());
				break;
		}
	}
}