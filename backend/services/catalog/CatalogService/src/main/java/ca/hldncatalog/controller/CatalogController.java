package ca.hldncatalog.controller;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import ca.hldncatalog.dto.persistent.CatalogItem;
import ca.hldncatalog.repository.CatalogItemRepository;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

	Logger logger = LoggerFactory.getLogger(CatalogController.class);

	private final AmqpTemplate amqpTemplate;
	private final CatalogItemRepository catalogItemRepository;

	@Autowired
	public CatalogController(AmqpTemplate amqpTemplate, CatalogItemRepository catalogItemRepository) {
		this.amqpTemplate = amqpTemplate;
		this.catalogItemRepository = catalogItemRepository;
	}

	@GetMapping("/ping")
	public String ping() {
		logger.info("ping()");
		return "Ping successful!\n";
	}

	@GetMapping("/items")
	public List<CatalogItem> getCatalogItems(@RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer pageIndex) throws Exception {
		List<CatalogItem> catalogItems = null;
		Integer _pageIndex = 0;

		logger.info("getCatalogItems() pageSize: " + pageSize + " pageIndex: " + pageIndex);

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
	
	@GetMapping("/item")
	public CatalogItem getCatalogItem(@RequestParam Integer catalogItemId) throws Exception {
		CatalogItem catalogItem = null;
		
		logger.info("getCatalogItem() catalogItemId {}", catalogItemId);
		
		catalogItem = catalogItemRepository.findById(catalogItemId).get();
		
		logger.info("getCatalogItem() got catalogItem {}", catalogItem);
		
		return catalogItem;
	}
	
	@PostMapping("/item")
	@Transactional
	public void createCatalogItem(@RequestBody CatalogItem catalogItem) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Jwt jwt = (Jwt)authentication.getPrincipal();
		String userId = jwt.getSubject();

		logger.info("createCatalogItem() user {} catalogItem {}", userId, catalogItem);
		
		catalogItemRepository.save(catalogItem);
	}
}
