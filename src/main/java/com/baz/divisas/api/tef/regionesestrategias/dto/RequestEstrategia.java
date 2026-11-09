package com.baz.divisas.api.tef.regionesestrategias.dto;

import javax.validation.constraints.NotNull;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import com.baz.divisas.general.model.Actualizar;
import com.baz.divisas.general.model.Registrar;

/**
 * RequestEstrategia.
 */
public class RequestEstrategia {

	@BsonRepresentation(BsonType.OBJECT_ID)
	@NotNull(message = "Requerido", groups = {Registrar.class,Actualizar.class})
	private String idRegion;
	@NotNull(message = "Requerido", groups = {Registrar.class,Actualizar.class})
	private String tipoOperacion;
	@NotNull(message = "Requerido", groups = {Registrar.class,Actualizar.class})
	private String idDivisa;
	@NotNull(message = "Requerido", groups = {Registrar.class})
	private double monto;
	@NotNull(message = "Requerido", groups = {Registrar.class,Actualizar.class} )
	private boolean activo;
	@NotNull(message = "Requerido", groups = {Actualizar.class} )
	private boolean activoEstrategia;

	/**
	 * Constructor de la clase
	 */
	public RequestEstrategia() {
		super();
	}

	/**
	 * @return the idRegion
	 */
	public String getIdRegion() {
		return idRegion;
	}
	/**
	 * @param idRegion the idRegion to set
	 */
	public void setIdRegion(String idRegion) {
		this.idRegion = idRegion;
	}
	/**
	 * @return the tipoOperacion
	 */
	public String getTipoOperacion() {
		return tipoOperacion;
	}
	/**
	 * @param tipoOperacion the tipoOperacion to set
	 */
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}
	/**
	 * @return the idDivisa
	 */
	public String getIdDivisa() {
		return idDivisa;
	}
	/**
	 * @param idDivisa the idDivisa to set
	 */
	public void setIdDivisa(String idDivisa) {
		this.idDivisa = idDivisa;
	}
	/**
	 * @return the monto
	 */
	public double getMonto() {
		return monto;
	}
	/**
	 * @param monto the monto to set
	 */
	public void setMonto(double monto) {
		this.monto = monto;
	}
	/**
	 * @return the activo
	 */
	public boolean isActivo() {
		return activo;
	}
	/**
	 * @param activo the activo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	/**
	 * @return the activoEstrategia
	 */
	public boolean isActivoEstrategia() {
		return activoEstrategia;
	}
	/**
	 * @param activoEstrategia the activoEstrategia to set
	 */
	public void setActivoEstrategia(boolean activoEstrategia) {
		this.activoEstrategia = activoEstrategia;
	}

}
