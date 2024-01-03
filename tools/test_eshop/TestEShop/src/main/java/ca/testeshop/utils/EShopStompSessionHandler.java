package ca.testeshop.utils;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;

public class EShopStompSessionHandler extends StompSessionHandlerAdapter {

	public String userId;
	public BlockingQueue<Notification> notifications;

	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		session.subscribe("/queue/" + userId, this);
	}

	@Override
	public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
		System.out.println("handleException() got an exception " + exception);
	}

	@Override
	public Type getPayloadType(StompHeaders headers) {
		return Notification.class;
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		Notification notification = (Notification) payload;
		// System.out.println("handleFrame() user " + userId + " got notification " +
		// notification);
		notifications.add(notification); // will throw an exception if queue is full
	}
}
