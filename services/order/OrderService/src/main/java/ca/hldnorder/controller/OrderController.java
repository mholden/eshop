package ca.hldnorder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.hldnorder.repository.OrderItemRepository;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
    OrderItemRepository orderItemRepository;

	@GetMapping("/ping")
    public String ping() {
    	System.out.println("ping()");
    	return "Ping successful!\n";
    }
}
