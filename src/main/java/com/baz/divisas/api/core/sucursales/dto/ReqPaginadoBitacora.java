package com.baz.divisas.api.core.sucursales.dto;

/**
 * Clase ReqPaginado
 * @author 1096526
 *
 */
public class ReqPaginadoBitacora {
	private int elementosPagina;
	private int pagina;
	private String id;
	
	/**
	 * Constructor de la clase
	 */
	public ReqPaginadoBitacora() {
		super();		
	}

	/**
	 * @return the elementosPagina
	 */
	public int getElementosPagina() {
		return elementosPagina;
	}

	/**
	 * @param elementosPagina the elementosPagina to set
	 */
	public void setElementosPagina(int elementosPagina) {
		this.elementosPagina = elementosPagina;
	}

	/**
	 * @return the pagina
	 */
	public int getPagina() {
		return pagina;
	}

	/**
	 * @param pagina the pagina to set
	 */
	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
}
