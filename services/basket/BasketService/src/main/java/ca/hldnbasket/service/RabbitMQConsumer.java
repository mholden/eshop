package ca.hldnbasket.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import ca.hldnbasket.event.IntegrationEvent;

@Service
public class RabbitMQConsumer {
	
	@RabbitListener(queues = "${ca.hldn.basket.rabbitmq.queue}")
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