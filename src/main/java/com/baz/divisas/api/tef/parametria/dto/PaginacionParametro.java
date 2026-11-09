package com.baz.divisas.api.tef.parametria.dto;

/**
 * The Class PaginacionParametro.
 */
public class PaginacionParametro {

	/** The pagina. */
	private int pagina;
	
	/** The registros pagina. */
	private int registrosPagina;

	/**
	 * Gets the pagina.
	 *
	 * @return the pagina
	 */
	public int getPagina() {
		return pagina;
	}

	/**
	 * Sets the pagina.
	 *
	 * @param pagina the new pagina
	 */
	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	/**
	 * Gets the registros pagina.
	 *
	 * @return the registros pagina
	 */
	public int getRegistrosPagina() {
		return registrosPagina;
	}

	/**
	 * Sets the registros pagina.
	 *
	 * @param registrosPagina the new registros pagina
	 */
	public void setRegistrosPagina(int registrosPagina) {
		this.registrosPagina = registrosPagina;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "PaginacionParametro [pagina=" + pagina + ", registrosPagina=" + registrosPagina + "]";
	}
	
}
