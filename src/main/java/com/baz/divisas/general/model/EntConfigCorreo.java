package com.baz.divisas.general.model;

public class EntConfigCorreo {
	private String ipServidor;
	private String servidor;
	private String remitente;
			
	public EntConfigCorreo(String ipServidor, String servidor, String remitente) {
		super();
		this.ipServidor = ipServidor;
		this.servidor = servidor;
		this.remitente = remitente;
	}

	public String getIpServidor() {
		return ipServidor;
	}

	public String getServidor() {
		return servidor;
	}

	public String getRemitente() {
		return remitente;
	}

	public void setIpServidor(String ipServidor) {
		this.ipServidor = ipServidor;
	}

	public void setServidor(String servidor) {
		this.servidor = servidor;
	}

	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}
}
