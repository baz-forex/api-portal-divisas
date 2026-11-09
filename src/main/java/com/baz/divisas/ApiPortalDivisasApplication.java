package com.baz.divisas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Clase principal
 * SpringBoot
 * Api Portal Divisas
 * @author B813196
 * itoxtle
 */
@SpringBootApplication
public class ApiPortalDivisasApplication extends SpringBootServletInitializer{
	  
	/**
	 * Metodo configure
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ApiPortalDivisasApplication.class);
	}
	   
	/**
	 * Metodo main
	 * @param args parametros
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApiPortalDivisasApplication.class);
	}

}
