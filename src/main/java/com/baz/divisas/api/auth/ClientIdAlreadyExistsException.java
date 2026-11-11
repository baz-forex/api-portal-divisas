package com.baz.divisas.api.auth;

/**
 * Indica que ya existe un cliente API con el clientId solicitado.
 */
public class ClientIdAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ClientIdAlreadyExistsException(String clientId) {
		super(clientId);
	}

	public String getClientId() {
		return getMessage();
	}
}
