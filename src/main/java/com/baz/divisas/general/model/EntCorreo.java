package com.baz.divisas.general.model;


public class EntCorreo {

	private int formatoCorreo;    
	private String destinatarios = ""; 
	private String asunto = "";
	private String mensaje = "";
	/**
	 * @param formatoCorreo
	 * @param destinatarios
	 * @param asunto
	 * @param mensaje
	 */
	public EntCorreo(int formatoCorreo, String destinatarios, String asunto, String mensaje) {
		super();
		this.formatoCorreo = formatoCorreo;
		this.destinatarios = destinatarios;
		this.asunto = asunto;
		this.mensaje = mensaje;
	}
	/**
	 * @return the formatoCorreo
	 */
	public int getFormatoCorreo() {
		return formatoCorreo;
	}
	/**
	 * @param formatoCorreo the formatoCorreo to set
	 */
	public void setFormatoCorreo(int formatoCorreo) {
		this.formatoCorreo = formatoCorreo;
	}
	/**
	 * @return the destinatarios
	 */
	public String getDestinatarios() {
		return destinatarios;
	}
	/**
	 * @param destinatarios the destinatarios to set
	 */
	public void setDestinatarios(String destinatarios) {
		this.destinatarios = destinatarios;
	}
	/**
	 * @return the asunto
	 */
	public String getAsunto() {
		return asunto;
	}
	/**
	 * @param asunto the asunto to set
	 */
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}
	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
