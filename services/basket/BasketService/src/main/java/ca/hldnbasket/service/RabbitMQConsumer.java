package ca.hldnbasket.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {
	
	@RabbitListener(queues = "${ca.hldn.basket.rabbitmq.queue}")
	public void recieve(String message) {
		
		System.out.println("recieve() received message: " + message);
		
		switch (message.split(":")[0]) {
			case "CheckoutEvent":
				System.out.println("receive() handling CheckoutEvent data: " + message.split(":")[1]);
				break;
			case "CheckoutEventResponse":
				System.out.println("receive() handling CheckoutEventResponse data: " + message.split(":")[1]);
				break;
			default:
				System.out.println("receive() no handler for message type " + message.split(":")[0]);
				break;
		}
	}
}