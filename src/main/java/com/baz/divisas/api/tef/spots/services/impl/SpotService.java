package com.baz.divisas.api.tef.spots.services.impl;

import java.nio.charset.StandardCharsets;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.baz.divisas.api.apigee.dto.TokenApigee;
import com.baz.divisas.api.tef.spots.dto.ReqSpotsTesoreria;
import com.baz.divisas.api.tef.spots.dto.ResSpotTesoreria;
import com.baz.divisas.general.config.PropApp;
import com.baz.divisas.general.util.LogApp;

import io.netty.resolver.DefaultAddressResolverGroup;
import reactor.netty.http.client.HttpClient;

/**
 * The Class SpotService.
 */
@Service
public class SpotService {
	/** The client. */
	@Resource
	private WebClient client;
	
	/** The Constant AUTHORIZATION. */
	private static final String AUTHORIZATION = "Authorization";
	
	/**
	 * Constructor de la clase.
	 */
	public SpotService() {
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
	public SpotService(WebClient client) {
		super();
		this.client = client;
	}
	
	/**
	 * Gets the token api.
	 *
	 * @return the token api
	 */
	public TokenApigee getTokenApi() {
		TokenApigee response = new TokenApigee();
		
		String encoding = Base64
				.encodeBase64String((PropApp.getConsumerKeyApi() + ":" + PropApp.getConsumerSecretApi())
						.getBytes(StandardCharsets.UTF_8));		
		StringBuilder sb = new StringBuilder();
		sb.append("grant_type=" + PropApp.getGrantTypeApi());
		LogApp.mensaje(PropApp.getUrlTokenApi() + " : Header :"+sb.toString());
		
		try {
			return client.post()
					.uri(PropApp.getUrlTokenApi())
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.header(AUTHORIZATION, "Basic " + encoding)
					.bodyValue(sb)
					.retrieve()
					.bodyToMono(TokenApigee.class).block();
		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrió un error al obtener el token de Apigee On Premise");
			response.setMensaje(e.getMessage());
		}
				
		return response;
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

	/**
	 * Consulta spot tesoreria.
	 *
	 * @param req the req
	 * @param accessToken the access token
	 * @return the res spot tesoreria
	 */
	public ResSpotTesoreria consultaSpotTesoreria(ReqSpotsTesoreria req, String accessToken) {
		ResSpotTesoreria response  = new ResSpotTesoreria();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add(AUTHORIZATION, "Bearer " + accessToken);

		try {
			return client.post()
					.uri(PropApp.getUrlTipoCambioTesoreria())
					.contentType(MediaType.APPLICATION_JSON)
					.headers(h -> h.addAll(headers))					
					.bodyValue(req)
					.retrieve()
					.bodyToMono(ResSpotTesoreria.class).block();
		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrió un error al consultar los tipos de cambio de tesoreria");
			response.setMensaje(e.getMessage());
		}
		
		return response;
	}
}
