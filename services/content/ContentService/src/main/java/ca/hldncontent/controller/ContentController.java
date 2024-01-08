package ca.hldncontent.controller;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import ca.hldncontent.dto.runtime.Content;

@RestController
@RequestMapping("/content")
public class ContentController {

	Logger logger = LoggerFactory.getLogger(ContentController.class);
	
	@Value("${ca.hldn.content.directory}")
	String contentDirectory;

	private final AmqpTemplate amqpTemplate;

	@Autowired
	public ContentController(AmqpTemplate amqpTemplate) {
		this.amqpTemplate = amqpTemplate;
	}

	@GetMapping("/ping")
	public String ping() {
		logger.info("ping()");
		return "Ping successful!\n";
	}
	
	@PostMapping
	public String uploadContent(@RequestBody Content content) throws Exception {
		/* TODO: secure
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
		String userId = oidcUser.getAttribute("sub");
		*/
		String userId = null;
		String contentId;

		logger.info("uploadContent() user {} content {}", userId, content);

		contentId = UUID.randomUUID().toString(); // TODO: this is unsafe; need unique id generator
		Files.write(Paths.get(contentDirectory + "/" + contentId), content.getData());

		return contentId;
	}
	
	@GetMapping
	public Content downloadContent(@RequestParam String contentId) throws Exception {
		/* TODO: secure
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
		String userId = oidcUser.getAttribute("sub");
		*/
		String userId = null;

		logger.info("downloadContent() user {} contentId {}", userId, contentId);

		return new Content(contentId, Files.readAllBytes(Paths.get(contentDirectory + "/" + contentId)));
	}
}
