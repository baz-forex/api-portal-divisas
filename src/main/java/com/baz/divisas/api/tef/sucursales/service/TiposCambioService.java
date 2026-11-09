package com.baz.divisas.api.tef.sucursales.service;

import java.nio.charset.StandardCharsets;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.baz.divisas.api.apigee.dto.ResultadoApi;
import com.baz.divisas.api.apigee.dto.TokenApigee;
import com.baz.divisas.api.apigee.model.ApiLlaves;
import com.baz.divisas.api.tef.sucursales.model.TiposCambioDiv;
import com.baz.divisas.general.config.PropApp;
import com.baz.divisas.general.util.LogApp;

import io.netty.resolver.DefaultAddressResolverGroup;
import reactor.netty.http.client.HttpClient;

/**
 * The Class TiposCambioService.
 */
public class TiposCambioService {

	/** The client. */
	@Resource
	private WebClient client;
	
	/** The Constant AUTHORIZATION. */
	private static final String AUTHORIZATION = "Authorization";
	
	/**
	 * Constructor de la clase.
	 */
	public TiposCambioService() {
		super();
		HttpClient httpClient = HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
		this.client = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
	}
	
	/**
	 * Constructor de la clase.
	 *
	 * @param client - WebClient
	 */
	public TiposCambioService(WebClient client) {
		super();
		this.client = client;
	}
	
	/**
	 * Gets the client.
	 *
	 * @return the client
	 */
	public WebClient getClient() {
		return client;
	}

	/**
	 * Sets the client.
	 *
	 * @param client the new client
	 */
	public void setClient(WebClient client) {
		this.client = client;
	}
	
//	/**
//	 * Gets the token api.
//	 *
//	 * @return the token api
//	 */
//	public TokenApigee getTokenApiAWS() {
//		TokenApigee response = new TokenApigee();
//		
//		String encoding = Base64
//				.encodeBase64String((PropApp.getConsumerKeyApiAWS() + ":" + PropApp.getConsumerSecretApiAWS())
//						.getBytes(StandardCharsets.UTF_8));		
//		StringBuilder sb = new StringBuilder();
//		sb.append("grant_type=" + PropApp.getGrantTypeApi());
//		LogApp.mensaje(PropApp.getUrlTokenApiAWSINT() + " : Header :"+sb.toString());
//		
//		try {
//			return client.post()
//					.uri(PropApp.getUrlTokenApiAWSINT())
//					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
//					.header(AUTHORIZATION, "Basic " + encoding)
//					.bodyValue(sb)
//					.retrieve()
//					.bodyToMono(TokenApigee.class).block();
//		} 
//		catch (RuntimeException e) {
//			LogApp.excepcion(new Exception(e), "Ocurrió un error al obtener el token de Apigee AWS INT");
//			response.setMensaje(e.getMessage());
//		}
//				
//		return response;
//	}
	
	
	
//	/**
//	 * Registra tipos cambio.
//	 *
//	 * @param request the request
//	 * @param accessToken the access token
//	 * @return the resultado api
//	 */
//	public ResultadoApi registraTiposCambio(TiposCambioMM request,String accessToken) {
//		ResultadoApi response = new ResultadoApi();
//		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
//		headers.add(AUTHORIZATION, "Bearer " + accessToken);
//		headers.add("x-latitud","12.110328");
//		headers.add("x-longitud","-10.001270");
//		
//		try {
//			return client.post()
//					.uri(PropApp.getUrlRegistraTiposCambioMM())
//					.contentType(MediaType.APPLICATION_JSON)
//					.headers(h -> h.addAll(headers))					
//					.bodyValue(request)
//					.retrieve()
//					.bodyToMono(ResultadoApi.class).block();
//		} 
//		catch (RuntimeException e) {
//			LogApp.excepcion(new Exception(e), "Ocurrió un error al registrar el tipo de cambio en MM");
//			response.setMensaje(e.getMessage());
//		}
//		
//		return response;
//	}
	
	
	
	
	/**
	 * Gets the token api.
	 *
	 * @return the token api
	 */
	public TokenApigee getTokenApiAWSInterno() {
		TokenApigee response = new TokenApigee();
		
		String encoding = Base64
				.encodeBase64String((PropApp.getConsumerKeyApiDivisas() + ":" + PropApp.getConsumerSecretApiDivisas())
						.getBytes(StandardCharsets.UTF_8));		
		StringBuilder sb = new StringBuilder();
		sb.append("grant_type=" + PropApp.getGrantTypeApi());
		LogApp.mensaje(PropApp.getUrlTokenApiAWSINT() + " : Header :"+sb.toString());
		
		try {
			return client.post()
					.uri(PropApp.getUrlTokenApiAWSINT())
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.header(AUTHORIZATION, "Basic " + encoding)
					.bodyValue(sb)
					.retrieve()
					.bodyToMono(TokenApigee.class).block();
		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrió un error al obtener el token de Apigee AWS INT");
			response.setMensaje(e.getMessage());
		}
				
		return response;
	}
	
	

	/**
	 * Gets the llaves divisas.
	 *
	 * @param accessToken the access token
	 * @return the llaves divisas
	 */
	public ApiLlaves getLlavesDivisas(String accessToken) {
		ApiLlaves response = new ApiLlaves();
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add(AUTHORIZATION, "Bearer " + accessToken);
 		LogApp.mensaje(PropApp.getUrlApiSeguridadDivisas() +" : Headers :"+ headers);
		
 		try {
			return client.get()
					.uri(PropApp.getUrlApiSeguridadDivisas())
					.headers(h -> h.addAll(headers))
					.retrieve()
					.bodyToMono(ApiLlaves.class).block();
		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrió un error al obtener las llaves de Divisas en AWS");
			response.setMensaje(e.getMessage());
		}

		return response;
	}
	
	

	/**
	 * Registra tipos cambio divisas.
	 *
	 * @param request the request
	 * @param accessToken the access token
	 * @param idAcceso the id acceso
	 * @return the resultado api
	 */
	public ResultadoApi registraTiposCambioDivisas(TiposCambioDiv request,String accessToken,String idAcceso) {
		ResultadoApi response = new ResultadoApi();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add(AUTHORIZATION, "Bearer " + accessToken);
		headers.add("x-id-acceso",idAcceso);
		LogApp.mensaje(PropApp.getUrlRegistraTiposCambioDivisas() +" : Headers :"+ headers);
		try {
			return client.post()
					.uri(PropApp.getUrlRegistraTiposCambioDivisas())
					.contentType(MediaType.APPLICATION_JSON)
					.headers(h -> h.addAll(headers))					
					.bodyValue(request)
					.retrieve()
					.bodyToMono(ResultadoApi.class).block();
		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrió un error al registrar el tipo de cambio en Divisas");
			response.setMensaje(e.getMessage());
		}
		
		return response;
	}
}
