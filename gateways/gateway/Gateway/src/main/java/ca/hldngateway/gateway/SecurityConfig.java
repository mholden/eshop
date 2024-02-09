package ca.hldngateway.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter.Mode;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
			ReactiveClientRegistrationRepository clientRegistrationRepository) {
		// Authenticate through configured OpenID Provider
		http.oauth2Login();
		// Also logout at the OpenID Connect provider
		http.logout(logout -> logout.logoutSuccessHandler(new OidcClientInitiatedServerLogoutSuccessHandler(
				clientRegistrationRepository)));
		http.authorizeExchange()
		.pathMatchers(
			HttpMethod.GET, "/catalog/items/**"
		).permitAll()
		.pathMatchers(
			HttpMethod.GET, "/content/**"
		).permitAll()
		.pathMatchers(
			"/notification/**" // TODO: revisit websocket security
		).permitAll()
		.anyExchange().authenticated(); // Require authentication for all other requests
		// Allow showing /home within a frame
		http.headers().frameOptions().mode(Mode.SAMEORIGIN);
		// Disable CSRF in the gateway to prevent conflicts with proxied service CSRF
		http.csrf().disable();
		return http.build();
	}

}