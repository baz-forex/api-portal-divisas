package com.baz.divisas.api.tef.sucursales.model;

import java.util.List;

public class ResponseSucursalesMasivas {

	private String sucursalesCargadas = "";
	private List<String> sucursalesNoCargadas;

	public ResponseSucursalesMasivas() {
		super();
	}
	
	public ResponseSucursalesMasivas(String sucursalesCargadas,List<String> sucursalesNoCargadas) {
		this.sucursalesCargadas = sucursalesCargadas;
		this.sucursalesNoCargadas = sucursalesNoCargadas;
	}
	
	public String getSucursalesCargadas() {
		return sucursalesCargadas;
	}

	public void setSucursalesCargadas(String sucursalesCargadas) {
		this.sucursalesCargadas = sucursalesCargadas;
	}

	public List<String> getSucursalesNoCargadas() {
		return sucursalesNoCargadas;
	}

	public void setSucursalesNoCargadas(List<String> sucursalesNoCargadas) {
		this.sucursalesNoCargadas = sucursalesNoCargadas;
	}
}
