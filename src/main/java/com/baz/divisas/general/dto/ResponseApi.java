package com.baz.divisas.general.dto;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseApi {

	@ApiModelProperty(value = "Codigo", position = 1)
	private String codigo;
	@ApiModelProperty(value = "Mensaje", position = 2)
	private String mensaje;	
	@ApiModelProperty(value = "Folio", position = 3)
	private String folio;
	@ApiModelProperty(value = "Info", position = 4)
	private String info;
	@ApiModelProperty(value = "Detalles", position = 5)
	private List<String> detalles = new ArrayList<>();	
	@ApiModelProperty(value = "Resultado", position = 6)
	private Object resultado;
	
	@JsonIgnore
	private HttpStatus httpStatus;
	
	public ResponseApi(EstatusResponse estatus) {
		super();
		this.httpStatus = estatus.getStatusHttp();
		this.codigo = estatus.getCodigo();
		this.mensaje = estatus.getMensaje();
		this.folio = UUID.randomUUID().toString();
	}

	public ResponseApi(EstatusResponse estatus, Object resultado) {
		super();
		this.httpStatus = estatus.getStatusHttp();
		this.codigo = estatus.getCodigo();
		this.mensaje = estatus.getMensaje();
		this.folio = UUID.randomUUID().toString();
		this.resultado = resultado;
	}
	
	public ResponseApi(EstatusResponse estatus, List<String> detalles) {
		super();
		this.httpStatus = estatus.getStatusHttp();
		this.codigo = estatus.getCodigo();
		this.mensaje = estatus.getMensaje();
		this.folio = UUID.randomUUID().toString();
		this.info = estatus.getInfo();
		this.detalles = Collections.unmodifiableList(detalles);
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public List<String> getDetalles() {
		return Collections.unmodifiableList(detalles);
	}

	public void setDetalles(List<String> detalles) {
		this.detalles = Collections.unmodifiableList(detalles);
	}

	public Object getResultado() {
		return resultado;
	}

	public void setResultado(Object resultado) {
		this.resultado = resultado;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	@Override
	public String toString() {
		return "ResponseApi [codigo=" + codigo + ", mensaje=" + mensaje + ", folio=" + folio + ", info=" + info
				+ ", detalles=" + detalles + ", resultado=" + resultado + ", httpStatus=" + httpStatus + "]";
	}

}
