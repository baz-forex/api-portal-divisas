package com.baz.divisas.api.tef.sucursales.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.baz.divisas.api.tef.sucursales.model.Divisa;

/**
 * The Class ReqActSucursal.
 */
public class ReqActSucursal {

	/** The id sucursal. */
	@NotEmpty(message = "No puede ser vacío")
	@NotNull(message = "Requerida")
	private String idsucursal;
	
	/** The divisas. */
	@NotEmpty(message = "No puede ser vacío")
	@NotNull(message = "Requerida")
	private List<Divisa> divisas;
	
	/** The tc olp. */
	private Divisa tcOlp;
	
	/** The region sucursal archivo. */
	private String regionSucursalArchivo;
	
	/** The nombre sucursal archivo. */
	private String nombreSucursalArchivo;
	
	/** The tipo sucursal archivo. */
	private String tipoSucursalArchivo;
	
	/** The municipio sucursal archivo. */
	private String municipioSucursalArchivo;
	
	/** The estado sucursal archivo. */
	private String estadoSucursalArchivo;
	
	/** The calle sucursal archivo. */
	private String calleSucursalArchivo;
	
	/** The numero sucursal archivo. */
	private String numeroSucursalArchivo;
	
	/** The cp sucursal archivo. */
	private String cpSucursalArchivo;
	
	/** The numero region archivo. */
	private String numeroRegionArchivo;
	
	
	/**
	 * Instantiates a new req act sucursal.
	 */
	public ReqActSucursal() {
		super();
	}

	/**
	 * Gets the idsucursal.
	 *
	 * @return the idsucursal
	 */
	public String getIdsucursal() {
		return idsucursal;
	}

	/**
	 * Sets the idsucursal.
	 *
	 * @param idsucursal the new idsucursal
	 */
	public void setIdsucursal(String idsucursal) {
		this.idsucursal = idsucursal;
	}

	/**
	 * Gets the divisas.
	 *
	 * @return the divisas
	 */
	public List<Divisa> getDivisas() {
		return divisas;
	}

	/**
	 * Sets the divisas.
	 *
	 * @param divisas the new divisas
	 */
	public void setDivisas(List<Divisa> divisas) {
		this.divisas = divisas;
	}

	/**
	 * Gets the region sucursal archivo.
	 *
	 * @return the region sucursal archivo
	 */
	public String getRegionSucursalArchivo() {
		return regionSucursalArchivo;
	}

	/**
	 * Sets the region sucursal archivo.
	 *
	 * @param regionSucursalArchivo the new region sucursal archivo
	 */
	public void setRegionSucursalArchivo(String regionSucursalArchivo) {
		this.regionSucursalArchivo = regionSucursalArchivo;
	}

	/**
	 * Gets the nombre sucursal archivo.
	 *
	 * @return the nombre sucursal archivo
	 */
	public String getNombreSucursalArchivo() {
		return nombreSucursalArchivo;
	}

	/**
	 * Sets the nombre sucursal archivo.
	 *
	 * @param nombreSucursalArchivo the new nombre sucursal archivo
	 */
	public void setNombreSucursalArchivo(String nombreSucursalArchivo) {
		this.nombreSucursalArchivo = nombreSucursalArchivo;
	}

	/**
	 * Gets the tipo sucursal archivo.
	 *
	 * @return the tipo sucursal archivo
	 */
	public String getTipoSucursalArchivo() {
		return tipoSucursalArchivo;
	}

	/**
	 * Sets the tipo sucursal archivo.
	 *
	 * @param tipoSucursalArchivo the new tipo sucursal archivo
	 */
	public void setTipoSucursalArchivo(String tipoSucursalArchivo) {
		this.tipoSucursalArchivo = tipoSucursalArchivo;
	}

	/**
	 * Gets the municipio sucursal archivo.
	 *
	 * @return the municipio sucursal archivo
	 */
	public String getMunicipioSucursalArchivo() {
		return municipioSucursalArchivo;
	}

	/**
	 * Sets the municipio sucursal archivo.
	 *
	 * @param municipioSucursalArchivo the new municipio sucursal archivo
	 */
	public void setMunicipioSucursalArchivo(String municipioSucursalArchivo) {
		this.municipioSucursalArchivo = municipioSucursalArchivo;
	}

	/**
	 * Gets the estado sucursal archivo.
	 *
	 * @return the estado sucursal archivo
	 */
	public String getEstadoSucursalArchivo() {
		return estadoSucursalArchivo;
	}

	/**
	 * Sets the estado sucursal archivo.
	 *
	 * @param estadoSucursalArchivo the new estado sucursal archivo
	 */
	public void setEstadoSucursalArchivo(String estadoSucursalArchivo) {
		this.estadoSucursalArchivo = estadoSucursalArchivo;
	}

	/**
	 * Gets the calle sucursal archivo.
	 *
	 * @return the calle sucursal archivo
	 */
	public String getCalleSucursalArchivo() {
		return calleSucursalArchivo;
	}

	/**
	 * Sets the calle sucursal archivo.
	 *
	 * @param calleSucursalArchivo the new calle sucursal archivo
	 */
	public void setCalleSucursalArchivo(String calleSucursalArchivo) {
		this.calleSucursalArchivo = calleSucursalArchivo;
	}

	/**
	 * Gets the numero sucursal archivo.
	 *
	 * @return the numero sucursal archivo
	 */
	public String getNumeroSucursalArchivo() {
		return numeroSucursalArchivo;
	}

	/**
	 * Sets the numero sucursal archivo.
	 *
	 * @param numeroSucursalArchivo the new numero sucursal archivo
	 */
	public void setNumeroSucursalArchivo(String numeroSucursalArchivo) {
		this.numeroSucursalArchivo = numeroSucursalArchivo;
	}

	/**
	 * Gets the cp sucursal archivo.
	 *
	 * @return the cp sucursal archivo
	 */
	public String getCpSucursalArchivo() {
		return cpSucursalArchivo;
	}

	/**
	 * Sets the cp sucursal archivo.
	 *
	 * @param cpSucursalArchivo the new cp sucursal archivo
	 */
	public void setCpSucursalArchivo(String cpSucursalArchivo) {
		this.cpSucursalArchivo = cpSucursalArchivo;
	}

	/**
	 * Gets the numero region archivo.
	 *
	 * @return the numero region archivo
	 */
	public String getNumeroRegionArchivo() {
		return numeroRegionArchivo;
	}

	/**
	 * Sets the numero region archivo.
	 *
	 * @param numeroRegionArchivo the new numero region archivo
	 */
	public void setNumeroRegionArchivo(String numeroRegionArchivo) {
		this.numeroRegionArchivo = numeroRegionArchivo;
	}

	/**
	 * Gets the tc olp.
	 *
	 * @return the tc olp
	 */
	public Divisa getTcOlp() {
		return tcOlp;
	}

	/**
	 * Sets the tc olp.
	 *
	 * @param tcOlp the new tc olp
	 */
	public void setTcOlp(Divisa tcOlp) {
		this.tcOlp = tcOlp;
	}

	
}
