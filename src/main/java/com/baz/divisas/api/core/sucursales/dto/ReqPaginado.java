package com.baz.divisas.api.core.sucursales.dto;

/**
 * Clase ReqPaginado
 * @author 1096526
 *
 */
public class ReqPaginado {
	private int elementosPagina;
	private int pagina;
	
	/**
	 * Constructor de la clase
	 */
	public ReqPaginado() {
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
	
}
