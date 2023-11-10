package ca.hldncatalog.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {
	
	@Autowired
	RabbitMQPublisher rabbitMQPublisher;
	
	@RabbitListener(queues = "${ca.hldn.catalog.rabbitmq.queue}")
	public void recieve(String message) {
		
		System.out.println("recieve() received message: " + message);
		
		switch (message.split(":")[0]) {
			case "CheckoutEvent":
				rabbitMQPublisher.send("CheckoutEventResponse:" + message.split(":")[1]);
				break;
			default:
				System.out.println("receive() no handler for message type " + message.split(":")[0]);
				break;
		}
	}
}