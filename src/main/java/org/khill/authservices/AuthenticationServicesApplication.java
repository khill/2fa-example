/**
 * 
 */
package org.khill.authservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Spring boot application class
 * 
 * @author khill
 *
 */
@SpringBootApplication
public class AuthenticationServicesApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AuthenticationServicesApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServicesApplication.class, args);
	}
	
}
