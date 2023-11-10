package ca.hldncatalog.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQPublisher {
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${ca.hldn.catalog.rabbitmq.exchange}")
	private String exchange;
	
	public void send(String message) {
		System.out.println("send() sending message " + message);
		rabbitTemplate.convertAndSend(exchange, message.split(":")[0], message);
	}
}