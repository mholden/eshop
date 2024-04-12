package ca.hldngateway.gateway;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter.Mode;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;


@Configuration
public class SecurityConfig {
	
	
	@Bean
	SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
			ReactiveClientRegistrationRepository clientRegistrationRepository) {
		// Validate tokens through configured OpenID Provider
		//http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
		// Require authentication for no requests
		http.authorizeExchange().anyExchange().permitAll();
		// Allow showing pages within a frame
		http.headers().frameOptions().mode(Mode.SAMEORIGIN);
		http.cors();
		return http.build();
	}
	
	/*
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	*/

	/*
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
			HttpMethod.GET, "/catalog/item/**"
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
		http.cors();
		return http.build();
	}
	
	/*
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// Validate tokens through configured OpenID Provider
		http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
		// Require authentication for all requests
		http.authorizeHttpRequests().anyRequest().authenticated();
		// Allow showing pages within a frame
		http.headers().frameOptions().sameOrigin();
		http.cors();
		return http.build();
	}
	

	private JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		// Convert realm_access.roles claims to granted authorities, for use in access decisions
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
		return jwtAuthenticationConverter;
	}

	@Bean
	JwtDecoder jwtDecoderByIssuerUri(OAuth2ResourceServerProperties properties) {
		String issuerUri = properties.getJwt().getIssuerUri();
		NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromIssuerLocation(issuerUri);
		// Use preferred_username from claims as authentication name, instead of UUID subject
		//jwtDecoder.setClaimSetConverter(new UsernameSubClaimAdapter());
		return jwtDecoder;
	}
	
	
	*/

}

/*
//As per: https://docs.spring.io/spring-security/site/docs/5.2.x/reference/html5/#oauth2resourceserver-jwt-claimsetmapping-rename
class UsernameSubClaimAdapter implements Converter<Map<String, Object>, Map<String, Object>> {

	private final MappedJwtClaimSetConverter delegate = MappedJwtClaimSetConverter.withDefaults(Collections.emptyMap());

	@Override
	public Map<String, Object> convert(Map<String, Object> claims) {
		Map<String, Object> convertedClaims = this.delegate.convert(claims);
		String username = (String) convertedClaims.get("preferred_username");
		convertedClaims.put("sub", username);
		return convertedClaims;
	}

}

class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

	@Override
	@SuppressWarnings("unchecked")
	public Collection<GrantedAuthority> convert(final Jwt jwt) {
		final Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
		return ((List<String>) realmAccess.get("roles")).stream()
				.map(roleName -> "ROLE_" + roleName)
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

}
*/
