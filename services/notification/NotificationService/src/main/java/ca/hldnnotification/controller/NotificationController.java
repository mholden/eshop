package ca.hldnnotification.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {
	
	Logger logger = LoggerFactory.getLogger(NotificationController.class); 
	
	private final AmqpTemplate amqpTemplate;
	
	@Autowired
	public NotificationController(AmqpTemplate amqpTemplate) {    
		this.amqpTemplate = amqpTemplate;
	}

	@GetMapping("/ping")
    public String ping() {
    	logger.info("ping()");
    	return "Ping successful!\n";
    }
}
