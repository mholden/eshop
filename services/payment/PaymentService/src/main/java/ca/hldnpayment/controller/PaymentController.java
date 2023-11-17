package ca.hldnpayment.controller;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import ca.hldnpayment.dto.persistent.Payment;
import ca.hldnpayment.repository.PaymentRepository;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	Logger logger = LoggerFactory.getLogger(PaymentController.class);
	
	@Autowired
    PaymentRepository paymentRepository;

	@GetMapping("/ping")
    public String ping() {
    	logger.info("ping()");
    	return "Ping successful!\n";
    }
}
