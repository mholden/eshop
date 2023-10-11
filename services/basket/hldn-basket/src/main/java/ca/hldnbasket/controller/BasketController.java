package ca.hldnbasket.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import ca.hldnbasket.dto.BasketItem;
import ca.hldnbasket.repository.BasketItemRepository;

@RestController
@RequestMapping("/basket")
public class BasketController {
	
	@Autowired
    BasketItemRepository basketItemRepository;

	@GetMapping("/ping")
    public String ping() {
		System.out.println("ping()");
		return "Ping successful!\n";
    }
}
