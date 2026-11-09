package com.baz.divisas.api.siex.configuraciones.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonId;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Clase RestriccionCampo.
 *
 * @author B813196
 * itoxtle
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CampoRestringido {

	/** The id. */
	@BsonId
	private String id;
	
	/** The descripcion. */
	private String descripcion;

	private List<String> restricciones = new ArrayList<>();
	
	/** The empieza con. */
	private Restriccion empiezaCon;
	
	/** The termina con. */
	private Restriccion terminaCon;
	
	/** The contiene. */
	private Restriccion contiene;
	
	/** The igual A. */
	private Restriccion igualA;
	
	/**
	 * Constructor de la clase.
	 */
	public CampoRestringido() {
		super();

	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the descripcion.
	 *
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	
	/**
	 * Sets the descripcion.
	 *
	 * @param descripcion the new descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the restricciones
	 */
	public List<String> getRestricciones() {
		return new ArrayList<>(restricciones);
	}

	/**
	 * @param restricciones the restricciones to set
	 */
	public void setRestricciones(List<String> restricciones) {
		this.restricciones = new ArrayList<>(restricciones);
	}
	
	/**
	 * Gets the empieza con.
	 *
	 * @return the empieza con
	 */
	public Restriccion getEmpiezaCon() {
		return empiezaCon;
	}

	/**
	 * Sets the empieza con.
	 *
	 * @param empiezaCon the new empieza con
	 */
	public void setEmpiezaCon(Restriccion empiezaCon) {
		this.empiezaCon = empiezaCon;
	}

	/**
	 * Gets the termina con.
	 *
	 * @return the termina con
	 */
	public Restriccion getTerminaCon() {
		return terminaCon;
	}

	/**
	 * Sets the termina con.
	 *
	 * @param terminaCon the new termina con
	 */
	public void setTerminaCon(Restriccion terminaCon) {
		this.terminaCon = terminaCon;
	}

	/**
	 * Gets the contiene.
	 *
	 * @return the contiene
	 */
	public Restriccion getContiene() {
		return contiene;
	}

	/**
	 * Sets the contiene.
	 *
	 * @param contiene the new contiene
	 */
	public void setContiene(Restriccion contiene) {
		this.contiene = contiene;
	}

	/**
	 * Gets the igual A.
	 *
	 * @return the igual A
	 */
	public Restriccion getIgualA() {
		return igualA;
	}

	/**
	 * Sets the igual A.
	 *
	 * @param igualA the new igual A
	 */
	public void setIgualA(Restriccion igualA) {
		this.igualA = igualA;
	}
	
}
