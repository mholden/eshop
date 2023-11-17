package ca.hldnorder.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.hldnorder.repository.OrderItemRepository;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	Logger logger = LoggerFactory.getLogger(OrderController.class); 
	
	@Autowired
    OrderItemRepository orderItemRepository;

	@GetMapping("/ping")
    public String ping() {
    	logger.info("ping()");
    	return "Ping successful!\n";
    }
}
