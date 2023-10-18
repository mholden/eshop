package ca.hldncatalog.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import ca.hldncatalog.dto.CatalogItem;
import ca.hldncatalog.repository.CatalogItemRepository;

@RestController
@RequestMapping("/catalog")
public class CatalogController {
	
	@Autowired
    CatalogItemRepository catalogItemRepository;

	@GetMapping("/ping")
    public String ping() {
    	System.out.println("ping()");
    	return "Ping successful!\n";
    }
    
    @GetMapping("/items")
    public List<CatalogItem> getCatalogItems(@RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageIndex) {
    	List<CatalogItem> catalogItems = null;
    	Integer _pageIndex = 0;
    	
    	System.out.println("getCatalogItems() pageSize: " + pageSize + " pageIndex: " + pageIndex);
    	
    	if (pageSize != null) {
    		if (pageIndex != null) {
    			_pageIndex = pageIndex;
    		}
    		catalogItems = catalogItemRepository.findAll(PageRequest.of(_pageIndex, pageSize)).getContent();
    	} else {
    		catalogItems = catalogItemRepository.findAll();
    	}
    	
    	return catalogItems;
    }
}
