package com.baz.divisas.api.core.clientes.dto;

/**
 * Clase ResponseMW
 * @author B813196
 * itoxtle
 */
public class RespMiddleware {

	private String resultado;
	private String codigo;
	private String mensaje;
	
	/**
	 * Constructor de la clase
	 */
	public RespMiddleware() {
		super();
	}
	
	/**
	 * Constrictor de la clase
	 * @param resultado - Resultado
	 * @param codigo - Codigo
	 * @param mensaje - Mensaje
	 */
	public RespMiddleware(String resultado, String codigo, String mensaje) {
		super();
		this.resultado = resultado;
		this.codigo = codigo;
		this.mensaje = mensaje;
	}

	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
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

	@Override
	public String toString() {
		return "ResponseMW [resultado=" + resultado + ", codigo=" + codigo + ", mensaje=" + mensaje + "]";
	}

}
