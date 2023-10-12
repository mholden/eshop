package ca.hldncatalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .oauth2Client()
                    .and()
                .oauth2Login()
                .tokenEndpoint()
                    .and()
                .userInfoEndpoint();

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);

        http
                .authorizeHttpRequests()
                            .requestMatchers("/catalog/unauthenticated", "/oauth2/**", "/login/**").permitAll()
                            .anyRequest()
                                .fullyAuthenticated()
                .and()
                    .logout()
                    .logoutSuccessUrl("http://catalog-service:8180/realms/quickstart/protocol/openid-connect/logout?redirect_uri=http://catalog-service:5121/");
                    //.logoutSuccessUrl("http://localhost:8180/realms/quickstart/protocol/openid-connect/logout?redirect_uri=http://localhost:5121/");

        return http.build();
    }
}