package com.baz.divisas.general.dto;

import java.text.MessageFormat;

import org.springframework.http.HttpStatus;

import com.baz.divisas.general.model.Constant;

public enum EstatusResponse {
	

	OK(HttpStatus.OK, "Operación exitosa."),
	NO_AUTORIZADO(HttpStatus.UNAUTHORIZED, "Acceso denegado."),
	NO_PERMITIDO(HttpStatus.FORBIDDEN, "Acceso denegado."),
	PETICION_INCORRECTA(HttpStatus.BAD_REQUEST, "Petición no válida."),
	SIN_RESULTADOS(HttpStatus.NOT_FOUND, "No existe información."),
	CLIENT_ID_ALREADY_EXISTS(HttpStatus.CONFLICT, "El clientId ya existe en la base de datos."),
	ERROR_STATUS_USUARIO(HttpStatus.NOT_FOUND, "Usuario en estatus invalido."),
	NO_CONTENT(HttpStatus.NO_CONTENT,"Error al actualizar."), 
	ERROR_INTERNO(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error al procesar la peticion.");
	

	private HttpStatus statusHttp;	
	private String mensaje;
	
	EstatusResponse(HttpStatus status, String mensaje) {
		this.statusHttp = status;
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

	public String getCodigo() {
		return MessageFormat.format(Constant.API_FORMAT_CODIGO, this.statusHttp.value());
	}
	
	public String getInfo() {
		return MessageFormat.format(Constant.API_FORMAT_INFO, this.statusHttp.value());
	}

	public HttpStatus getStatusHttp() {
		return statusHttp;
	}
	
}
