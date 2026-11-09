package com.baz.divisas.api.auth.model;

import org.bson.codecs.pojo.annotations.BsonId;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Modelo de Client para consumo del Api
 * @author B813196
 * itoxtle
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthClient {

	@BsonId
	private String clientId;
	private String clientSecret;

	/**
	 * Constructor de la clase
	 */
	public AuthClient() {
		super();
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

}
