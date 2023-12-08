package ca.hldnnotification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import ca.hldnnotification.notification.Notification;

@Service
public class NotificationDesk {
	Logger logger = LoggerFactory.getLogger(NotificationDesk.class);
	
	private final AmqpTemplate amqpTemplate;
	private final SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	public NotificationDesk(AmqpTemplate amqpTemplate, SimpMessagingTemplate simpMessagingTemplate) {    
		this.amqpTemplate = amqpTemplate; 
		this.simpMessagingTemplate = simpMessagingTemplate;
	}
	
	public void sendUserNotification(Notification notification)
	{
		logger.info("sendUserNotification() notification {}", notification);
		simpMessagingTemplate.convertAndSend("/queue/" + notification.getUserId(), notification);
	}
}
