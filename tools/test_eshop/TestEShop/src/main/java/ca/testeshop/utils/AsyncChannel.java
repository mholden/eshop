package ca.testeshop.utils;

import java.time.Duration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class AsyncChannel {
	private EShopStompSessionHandler stompSessionHandler;
	private BlockingQueue<Notification> notifications = new ArrayBlockingQueue<>(50);

	public AsyncChannel(String url, String userId) {
		WebSocketClient client = new StandardWebSocketClient();
		WebSocketStompClient stompClient = new WebSocketStompClient(client);

		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		stompSessionHandler = new EShopStompSessionHandler();
		stompSessionHandler.userId = userId;
		stompSessionHandler.notifications = notifications;
		stompClient.connect(url, stompSessionHandler);
	}
	
	public void waitFor(String notificationType, Duration duration) throws Exception {
		Notification notification = null;
		Long millisLeft, pollStart;
		millisLeft = duration.getSeconds() * 1000;
		while (millisLeft > 0 && 
				(notification == null || !notification.notificationType.equals(notificationType))) {
			pollStart = System.currentTimeMillis();
			notification = notifications.poll(millisLeft, TimeUnit.MILLISECONDS);
			millisLeft -= System.currentTimeMillis() - pollStart;
			//System.out.println("waitFor() got notification " + notification + " millisLeft " + millisLeft);
		}
		TestUtils.failIf(notification == null || !notification.notificationType.equals(notificationType), null);
	}
}
