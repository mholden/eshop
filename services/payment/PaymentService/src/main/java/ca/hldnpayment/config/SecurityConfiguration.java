package ca.hldnpayment.config;

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
            	.requestMatchers(
            		"/oauth2/**", 
        			"/login/**", 
        			"/payment/ping"
        		)
            	.permitAll()
            	.anyRequest()
            	.fullyAuthenticated()
                .and()
                .logout()
                .logoutSuccessUrl("http://localhost:8180/realms/quickstart/protocol/openid-connect/logout?redirect_uri=http://localhost:5124/")
                .and()
                .csrf().disable(); // without this, POSTs fail; TODO: revisit;

        return http.build();
    }
}