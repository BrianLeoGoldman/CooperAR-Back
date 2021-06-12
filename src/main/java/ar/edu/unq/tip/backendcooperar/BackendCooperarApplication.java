package ar.edu.unq.tip.backendcooperar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BackendCooperarApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendCooperarApplication.class, args);
	}

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.GET, "/user/login").permitAll()
					.antMatchers(HttpMethod.PUT, "/user/**").permitAll()
					.antMatchers(HttpMethod.DELETE, "/user/**").permitAll()
					.antMatchers(HttpMethod.PUT, "/project/**").permitAll()
					.antMatchers(HttpMethod.DELETE, "/project/**").permitAll()
					.antMatchers(HttpMethod.POST, "/project/**").permitAll()
					.antMatchers(HttpMethod.PUT, "/task/**").permitAll()
					.antMatchers(HttpMethod.PUT, "/task/assign/**").permitAll()
					.antMatchers(HttpMethod.DELETE, "/task/**").permitAll()
					.antMatchers(HttpMethod.POST, "/task/**").permitAll()
					.anyRequest().authenticated();
			http.cors();
		}

		@Bean
		CorsConfigurationSource corsConfigurationSource()
		{
			CorsConfiguration configuration = new CorsConfiguration();

			//configuration.setAllowedOrigins(Arrays.asList("https://example.com"));
			configuration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
			configuration.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Response-Type"));
			configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
			configuration.addAllowedHeader("Authorization");

			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			source.registerCorsConfiguration("/**", configuration);
			return source;
		}
	}


}
