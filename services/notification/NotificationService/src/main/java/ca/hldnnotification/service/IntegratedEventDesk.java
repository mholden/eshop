package ca.hldnnotification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import ca.hldnnotification.event.*;
import ca.hldnnotification.notification.OrderPaymentSucceededNotification;
import ca.hldnnotification.notification.OrderVerifiedNotification;

@Service
public class IntegratedEventDesk {

	Logger logger = LoggerFactory.getLogger(IntegratedEventDesk.class);

	private final AmqpTemplate amqpTemplate;
	private final SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	public IntegratedEventDesk(AmqpTemplate amqpTemplate, SimpMessagingTemplate simpMessagingTemplate) {
		this.amqpTemplate = amqpTemplate;
		this.simpMessagingTemplate = simpMessagingTemplate;
	}

	/*
	 * Sending:
	 */

	private String exchange = "ca.hldn.eshop.exchange";

	public void send(IntegratedEvent event) {
		logger.info("send() sending event " + event.getEventType());
		amqpTemplate.convertAndSend(exchange, event.getEventType(), event);
	}

	/*
	 * Receiving:
	 */

	private void handleOrderVerifiedEvent(OrderVerifiedEvent orderVerifiedEvent) throws Exception {
		logger.info("handleOrderVerifiedEvent() orderVerifiedEvent {}", orderVerifiedEvent);
		new NotificationDesk(amqpTemplate, simpMessagingTemplate).sendUserNotification(new OrderVerifiedNotification(orderVerifiedEvent.getOrder()));
	}

	private void handleOrderPaymentSucceededEvent(OrderPaymentSucceededEvent orderPaymentSucceededEvent) throws Exception {
		logger.info("handleOrderPaymentSucceededEvent() orderPaymentSucceededEvent {}", orderPaymentSucceededEvent);
		new NotificationDesk(amqpTemplate, simpMessagingTemplate).sendUserNotification(new OrderPaymentSucceededNotification(orderPaymentSucceededEvent.getOrder()));
	}

	@RabbitListener(queues = "${ca.hldn.notification.rabbitmq.queue}")
	public void receive(IntegratedEvent event) throws Exception {

		logger.info("receive() received event " + event.getEventType());

		switch (event.getEventType()) {
		case "OrderVerifiedEvent":
			handleOrderVerifiedEvent((OrderVerifiedEvent) event);
			break;
		case "OrderPaymentSucceededEvent":
			handleOrderPaymentSucceededEvent((OrderPaymentSucceededEvent) event);
			break;
		default:
			logger.info("receive() no handler for event of type " + event.getEventType());
			break;
		}
	}
}