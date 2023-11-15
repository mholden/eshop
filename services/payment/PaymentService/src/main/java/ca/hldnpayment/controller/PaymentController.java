package ca.hldnpayment.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import ca.hldnpayment.dto.Payment;
import ca.hldnpayment.repository.PaymentRepository;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
    PaymentRepository paymentRepository;

	@GetMapping("/ping")
    public String ping() {
    	System.out.println("ping()");
    	return "Ping successful!\n";
    }
}
