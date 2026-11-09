package com.baz.divisas.general.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.LogApp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@Order(1)
public class LoggingFilter implements Filter {

	@Autowired
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private static final List<String> EXCLUDE_PATHS = Arrays.asList("/api/llaves/generar",
																	"/api/llaves/encriptar",
																	"/api/core/reporteria/descarga-incidencias",
																	"/api/core/configuraciones/notificaciones",
																	"/actualizacion-masiva",
																	"/scim/v2/token",
																	"/v2/api-docs",
																	"/swagger-resources/",
																	"/swagger-ui.html",
																	"/configuration/",
																	"/webjars/",
																	"/public",
																	"/index.jsp",
																	"/api/tef/tablawscecostos/sucursales",
																	"/api/tef/spots",
																	"/api/core/reporteria/incidencias/descarga",
																	"/api/core/reporteria/operaciones/descarga",
																	"/api/digital/reporteria/operaciones/descarga",														
																	"/api/core/reporteria/ganadores-rascadito/descarga",
																	"/api/siex/configuraciones/campos-restringidos/descarga",
																	"/api/tarjetaregalo/",
																	"/api/core/reporteria/cupones-mas-salud/descarga",
																	"/api/core/beneficios/cupones/cupones-mas-salud/registro");
		
	public LoggingFilter() {
		super();
	}

	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {
			
			request.setAttribute("tiempoInicial", System.currentTimeMillis());
			
			ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest)request);
			ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);
			
			chain.doFilter(requestWrapper, responseWrapper);
       
			String path = requestWrapper.getServletPath();

			if(isPathLog(path)) {
				
				StringBuilder mensaje = new StringBuilder();
				
				// INFO Request
				String usuario = requestWrapper.getHeader(Constant.X_ID_USUARIO);
				
				String ip = requestWrapper.getRemoteAddr();				
				HttpMethod httpMethod = HttpMethod.valueOf(requestWrapper.getMethod());
				String requestBody = new String (requestWrapper.getContentAsByteArray(), UTF_8);
		
				mensaje.append(httpMethod + " Request: "+ parseBody(requestBody));			    
					
				// INFO Response
				HttpStatus responseStatus = HttpStatus.valueOf(responseWrapper.getStatus());

				String responseBody = IOUtils.toString(responseWrapper.getContentInputStream(), UTF_8);
			   
				mensaje.append(" Response: "+ responseStatus +" "+ parseBody(responseBody));
					
				long tiempoDuracion = getTiempoDuracion(requestWrapper);
				
				LogApp.mensajePeticion(usuario, ip, path, mensaje.toString(), tiempoDuracion);
			}

			responseWrapper.copyBodyToResponse();

		} 
		catch (RuntimeException e) {
			LogApp.mensaje("No fue posible loggear la peticion", e);
		}

	}
	
	private JsonNode parseBody(String body) {
		
		JsonNode json = null;
		
		try {
			json = objectMapper.readTree(body);
		}
		catch(JsonProcessingException jpe) {
			LogApp.mensaje("Error al parsear body: "+ body, jpe);
		}
		
		return json;
	}

	private static boolean isPathLog(String path) {
		
			
		for (String str : EXCLUDE_PATHS) {
			
			if(path.contains(str)) {
				return false;
			}						
		}
		
		return true;

		
	}
	
	private static long getTiempoDuracion(HttpServletRequest request) {
		
		long tiempoDuracion = 0;
		
		try {
			
			Object tiempoInicial = request.getAttribute("tiempoInicial");
			
			if(tiempoInicial != null) {
				tiempoDuracion = (System.currentTimeMillis() - (long) tiempoInicial) ;
			}
						
		}
		catch (RuntimeException e) {
			LogApp.excepcion(e, "Ocurrio un error al obtener el tiempo de duracion");
		}
		
		return tiempoDuracion;
	}

}
