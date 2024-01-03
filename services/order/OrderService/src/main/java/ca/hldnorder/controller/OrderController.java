package ca.hldnorder.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.*;

import ca.hldnorder.dto.persistent.Order;
import ca.hldnorder.repository.OrderRepository;

@RestController
@RequestMapping("/order")
public class OrderController {

	Logger logger = LoggerFactory.getLogger(OrderController.class);

	private final AmqpTemplate amqpTemplate;
	private final OrderRepository orderRepository;

	@Autowired
	public OrderController(AmqpTemplate amqpTemplate, OrderRepository orderRepository) {
		this.amqpTemplate = amqpTemplate;
		this.orderRepository = orderRepository;
	}

	@GetMapping("/ping")
	public String ping() {
		logger.info("ping()");
		return "Ping successful!\n";
	}

	// TODO: make this paged
	@GetMapping("/orders")
	public List<Order> getOrders() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
		String userId = oidcUser.getAttribute("sub");
		List<Order> orders = null;

		logger.info("getOrders() user {}", userId);

		orders = orderRepository.findByUserId(userId);

		logger.info("getOrders() got {} orders {}", orders.size(), orders);

		return orders;
	}
}
