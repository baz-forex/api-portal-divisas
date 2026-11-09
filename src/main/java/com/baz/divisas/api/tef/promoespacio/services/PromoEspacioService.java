/**
 * 
 */
package com.baz.divisas.api.tef.promoespacio.services;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.bson.Document;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.baz.divisas.api.tef.promoespacio.model.ResponsePromoEspacio;
import com.baz.divisas.api.tef.promoespacio.model.ResponseToken;
import com.baz.divisas.general.config.PropPromoEspacio;
import com.baz.divisas.general.util.LogApp;
import com.google.gson.Gson;

/**
 * The Class PromoEspacioService.
 *
 * @author B86960
 */
@Service
public class PromoEspacioService {

	/** The client. */
	@Resource
	private WebClient client;
	
	/**
	 * Constructor de la clase.
	 */
	public PromoEspacioService() {
		super();
		this.client = WebClient.create();
	}
	
	/**
	 * Constructor de la clase.
	 *
	 * @param client - WebClient
	 */
	public PromoEspacioService(WebClient client) {
		super();
		this.client = client;
	}
	
	/**
	 * Gets the token.
	 *
	 * @param requestKey the request key
	 * @param password the password
	 * @return the token
	 */
	public ResponseToken getToken(String requestKey, String password) {
		ResponseToken response = new ResponseToken();
	        Map<String, String> body = Map.of(
	                "client_id", PropPromoEspacio.getClientId(),
	                "password", password
	        );

		try {
			return client.post()
					.uri(PropPromoEspacio.getUrlTokenV2())
					.header("x-request-key", requestKey)
					.bodyValue(body)
					.retrieve()
					.bodyToMono(ResponseToken.class).block();
		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrio un error al obtener el token de PromoEspacio");
		}
				
		return response;
	}
	/**
	 * Put sucursales.
	 *
	 * @param token the token
	 * @param sucursalesIgualesPromo the sucursales iguales promo
	 * @return the response promo espacio
	 */
	public ResponsePromoEspacio putSucursales(String token, List<Document> sucursalesIgualesPromo) {
		ResponsePromoEspacio response = new ResponsePromoEspacio();
		try {
			return client.put()
					.uri(PropPromoEspacio.getUrlTablerosV2())
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", "Bearer "+token)
					.header("x-client-name", PropPromoEspacio.getClienteName())
					.body(BodyInserters.fromValue(new Gson().toJson(sucursalesIgualesPromo)))
					.retrieve()
					.bodyToMono(ResponsePromoEspacio.class).block();
		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrio un error al enviar las sucursales a PromoEspacio");
		}
		return response;
	}	
	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public ResponseToken getToken() {
		ResponseToken response = new ResponseToken();
		try {
			return client.post()
					.uri(PropPromoEspacio.getUrlToken())
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.body(BodyInserters.fromFormData("idUsuario",PropPromoEspacio.getAuthUsuario())
							.with("contrasena", PropPromoEspacio.getAuthClave()))
					.retrieve()
					.bodyToMono(ResponseToken.class).block();
		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrio un error al obtener el token de PromoEspacio");
			response.setMensaje(e.getMessage());
		}
				
		return response;
	}

	
	/**
	 * Put sucursales.
	 *
	 * @param token the token
	 * @param sucursalesIgualesPromo the sucursales iguales promo
	 * @return the response promo espacio
	 */
	public ResponsePromoEspacio putSucursalesAnterior(String token, List<Document> sucursalesIgualesPromo) {
		ResponsePromoEspacio response = new ResponsePromoEspacio();
		try {
			return client.put()
					.uri(PropPromoEspacio.getUrlTableros())
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", token)
					.body(BodyInserters.fromValue(new Gson().toJson(sucursalesIgualesPromo)))
					.retrieve()
					.bodyToMono(ResponsePromoEspacio.class).block();
		} 
		catch (RuntimeException e) {
			LogApp.excepcion(new Exception(e), "Ocurrio un error al enviar las sucursales a PromoEspacio");
			response.setMensaje(e.getMessage());
		}
		return response;
	}

}
