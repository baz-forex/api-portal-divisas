package com.baz.divisas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase principal
 * SpringBoot
 * Api Portal Divisas
 * @author B813196
 * itoxtle
 */
@SpringBootApplication
public class ApiPortalDivisasApplication extends SpringBootServletInitializer{

	private static final Logger LOGGER = LogManager.getLogger(ApiPortalDivisasApplication.class);
	  
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
		ConfigurableApplicationContext context = SpringApplication.run(ApiPortalDivisasApplication.class, args);
		String port = context.getEnvironment().getProperty("local.server.port",
				context.getEnvironment().getProperty("server.port", "8080"));
		String contextPath = context.getEnvironment().getProperty("server.servlet.context-path", "");
		String baseUrl = "http://localhost:" + port + contextPath;

		LOGGER.info("Microservice running on {}", baseUrl);
		LOGGER.info("Swagger is running on {}/swagger-ui.html", baseUrl);
	}

}
