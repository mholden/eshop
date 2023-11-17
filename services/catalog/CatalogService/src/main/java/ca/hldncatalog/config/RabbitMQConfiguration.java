package ca.hldncatalog.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ca.hldncatalog.event.*;

@Configuration
public class RabbitMQConfiguration {

	@Value("${ca.hldn.catalog.rabbitmq.exchange}")
	String exchange;
	
	@Value("${ca.hldn.catalog.rabbitmq.queue}")
	String queueName;

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}

	@Bean
	Binding orderInitiatedEventBinding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(OrderInitiatedEvent.class.getSimpleName());
	}
	
	@Bean
	Binding checkoutEventBinding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(CheckoutEvent.class.getSimpleName());
	}
	
	@Bean
	public DefaultClassMapper getClassMapper() {
		DefaultClassMapper classMapper = new DefaultClassMapper();
		Map<String, Class<?>> map = new HashMap<>();
		map.put("ca.hldnbasket.event.CheckoutEvent", CheckoutEvent.class);
		map.put("ca.hldnorder.event.OrderInitiatedEvent", OrderInitiatedEvent.class);
		classMapper.setIdClassMapping(map);
		return classMapper;
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		Jackson2JsonMessageConverter messageConverter =  new Jackson2JsonMessageConverter();
		messageConverter.setClassMapper(getClassMapper());
		return messageConverter;
	}

	@Bean
	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
}
