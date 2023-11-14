package ca.hldncatalog.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ca.hldncatalog.event.IntegrationEvent;

@Service
public class RabbitMQPublisher {
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${ca.hldn.catalog.rabbitmq.exchange}")
	private String exchange;
	
	public void send(IntegrationEvent event) {
		System.out.println("send() sending event " + event.getEventType());
		rabbitTemplate.convertAndSend(exchange, event.getEventType(), event);
	}
}