package com.baz.divisas.general.dto;

/**
 * Paginacion.
 */
public class Paginacion {

	/** The pagina. */
	private int pagina;
	
	/** The total registros por pagina. */
	private int registrosPorPagina;
	
	/** The total registros. */
	private long totalRegistros;
	
	/** The total paginas. */
	private long totalPaginas;
	
	
	/**
	 * Instantiates a new paginacion.
	 */
	public Paginacion() {
		super();
	}

	/**
	 * Instantiates a new paginacion response.
	 *
	 * @param pagina the pagina
	 * @param registrosPorPagina the registros por pagina
	 * @param totalRegistros the total registros
	 */
	public Paginacion(int pagina, int registrosPorPagina, long totalRegistros) {
		super();
		this.pagina = pagina;
		this.registrosPorPagina = registrosPorPagina;
		this.totalRegistros = totalRegistros;
		this.totalPaginas = (int) Math.ceil((double) totalRegistros / registrosPorPagina);
	}

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
	 * Gets the registros por pagina.
	 *
	 * @return the registros por pagina
	 */
	public int getRegistrosPorPagina() {
		return registrosPorPagina;
	}

	/**
	 * Sets the registros por pagina.
	 *
	 * @param registrosPorPagina the new registros por pagina
	 */
	public void setRegistrosPorPagina(int registrosPorPagina) {
		this.registrosPorPagina = registrosPorPagina;
	}

	/**
	 * Gets the total registros.
	 *
	 * @return the total registros
	 */
	public long getTotalRegistros() {
		return totalRegistros;
	}

	/**
	 * Sets the total registros.
	 *
	 * @param totalRegistros the new total registros
	 */
	public void setTotalRegistros(long totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	/**
	 * Gets the total paginas.
	 *
	 * @return the total paginas
	 */
	public long getTotalPaginas() {
		return totalPaginas;
	}

	/**
	 * Sets the total paginas.
	 *
	 * @param totalPaginas the new total paginas
	 */
	public void setTotalPaginas(long totalPaginas) {
		this.totalPaginas = totalPaginas;
	}
	
}
