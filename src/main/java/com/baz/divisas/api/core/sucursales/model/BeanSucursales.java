package com.baz.divisas.api.core.sucursales.model;

import org.bson.Document;
import org.bson.codecs.pojo.annotations.BsonId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Clase BeanSucursales
 * 
 * @author 1096526
 *
 */
public class BeanSucursales {

	@BsonId
	private String id;
	private int sucursal;
	private String direccion;
	private Document topesCaja;
	private String gerente;
	private String fechaApertura;
	@JsonProperty("region")
	private String nomRegion;
	@JsonProperty("territorio")
	private String nomTerritorio;
	private String appYGeolocalizacion;
	private String contactoGerente;
	private String contactoSucursal;
	private String implementacionDeSistemas;
	private String implementadorJr;
	private String implementadorSr;
	private String presupuesto;
	private String regional;
	private String regionalizacionLimites;
	private String territorial;
	private String tiempoPorTransaccion;
	private boolean certificar;
	@JsonIgnore
	private String nomCalle;
	@JsonIgnore
	private String numeroExterior;	
	private String correoGerente;

	/**
	 * Constructor de la clase
	 */
	public BeanSucursales() {
		super();
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

	/**
	 * @return the sucursal
	 */
	public int getSucursal() {
		return sucursal;
	}

	/**
	 * @param sucursal the sucursal to set
	 */
	public void setSucursal(int sucursal) {
		this.sucursal = sucursal;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the topesCaja
	 */
	public Document getTopesCaja() {
		return topesCaja;
	}

	/**
	 * @param topesCaja the topesCaja to set
	 */
	public void setTopesCaja(Document topesCaja) {
		this.topesCaja = topesCaja;
	}

	/**
	 * @return the gerente
	 */
	public String getGerente() {
		return gerente;
	}

	/**
	 * @param gerente the gerente to set
	 */
	public void setGerente(String gerente) {
		this.gerente = gerente;
	}

	/**
	 * @return the fechaApertura
	 */
	public String getFechaApertura() {
		return fechaApertura;
	}

	/**
	 * @param fechaApertura the fechaApertura to set
	 */
	public void setFechaApertura(String fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	/**
	 * @return the nomRegion
	 */
	public String getNomRegion() {
		return nomRegion;
	}

	/**
	 * @param nomRegion the nomRegion to set
	 */
	public void setNomRegion(String nomRegion) {
		this.nomRegion = nomRegion;
	}

	/**
	 * @return the nomTerritorio
	 */
	public String getNomTerritorio() {
		return nomTerritorio;
	}

	/**
	 * @param nomTerritorio the nomTerritorio to set
	 */
	public void setNomTerritorio(String nomTerritorio) {
		this.nomTerritorio = nomTerritorio;
	}

	/**
	 * @return the appYGeolocalizacion
	 */
	public String getAppYGeolocalizacion() {
		return appYGeolocalizacion;
	}

	/**
	 * @param appYGeolocalizacion the appYGeolocalizacion to set
	 */
	public void setAppYGeolocalizacion(String appYGeolocalizacion) {
		this.appYGeolocalizacion = appYGeolocalizacion;
	}

	/**
	 * @return the contactoGerente
	 */
	public String getContactoGerente() {
		return contactoGerente;
	}

	/**
	 * @param contactoGerente the contactoGerente to set
	 */
	public void setContactoGerente(String contactoGerente) {
		this.contactoGerente = contactoGerente;
	}

	/**
	 * @return the contactoSucursal
	 */
	public String getContactoSucursal() {
		return contactoSucursal;
	}

	/**
	 * @param contactoSucursal the contactoSucursal to set
	 */
	public void setContactoSucursal(String contactoSucursal) {
		this.contactoSucursal = contactoSucursal;
	}

	/**
	 * @return the implementacionDeSistemas
	 */
	public String getImplementacionDeSistemas() {
		return implementacionDeSistemas;
	}

	/**
	 * @param implementacionDeSistemas the implementacionDeSistemas to set
	 */
	public void setImplementacionDeSistemas(String implementacionDeSistemas) {
		this.implementacionDeSistemas = implementacionDeSistemas;
	}

	/**
	 * @return the implementadorJr
	 */
	public String getImplementadorJr() {
		return implementadorJr;
	}

	/**
	 * @param implementadorJr the implementadorJr to set
	 */
	public void setImplementadorJr(String implementadorJr) {
		this.implementadorJr = implementadorJr;
	}

	/**
	 * @return the implementadorSr
	 */
	public String getImplementadorSr() {
		return implementadorSr;
	}

	/**
	 * @param implementadorSr the implementadorSr to set
	 */
	public void setImplementadorSr(String implementadorSr) {
		this.implementadorSr = implementadorSr;
	}

	/**
	 * @return the presupuesto
	 */
	public String getPresupuesto() {
		return presupuesto;
	}

	/**
	 * @param presupuesto the presupuesto to set
	 */
	public void setPresupuesto(String presupuesto) {
		this.presupuesto = presupuesto;
	}

	/**
	 * @return the regional
	 */
	public String getRegional() {
		return regional;
	}

	/**
	 * @param regional the regional to set
	 */
	public void setRegional(String regional) {
		this.regional = regional;
	}

	/**
	 * @return the regionalizacionLimites
	 */
	public String getRegionalizacionLimites() {
		return regionalizacionLimites;
	}

	/**
	 * @param regionalizacionLimites the regionalizacionLimites to set
	 */
	public void setRegionalizacionLimites(String regionalizacionLimites) {
		this.regionalizacionLimites = regionalizacionLimites;
	}

	/**
	 * @return the territorial
	 */
	public String getTerritorial() {
		return territorial;
	}

	/**
	 * @param territorial the territorial to set
	 */
	public void setTerritorial(String territorial) {
		this.territorial = territorial;
	}

	/**
	 * @return the tiempoPorTransaccion
	 */
	public String getTiempoPorTransaccion() {
		return tiempoPorTransaccion;
	}

	/**
	 * @param tiempoPorTransaccion the tiempoPorTransaccion to set
	 */
	public void setTiempoPorTransaccion(String tiempoPorTransaccion) {
		this.tiempoPorTransaccion = tiempoPorTransaccion;
	}

	/**
	 * @return the certificar
	 */
	public boolean isCertificar() {
		return certificar;
	}

	/**
	 * @param certificar the certificar to set
	 */
	public void setCertificar(boolean certificar) {
		this.certificar = certificar;
	}

	/**
	 * @return the nomCalle
	 */
	public String getNomCalle() {
		return nomCalle;
	}

	/**
	 * @param nomCalle the nomCalle to set
	 */
	public void setNomCalle(String nomCalle) {
		this.nomCalle = nomCalle;
	}

	/**
	 * @return the numeroExterior
	 */
	public String getNumeroExterior() {
		return numeroExterior;
	}

	/**
	 * @param numeroExterior the numeroExterior to set
	 */
	public void setNumeroExterior(String numeroExterior) {
		this.numeroExterior = numeroExterior;
	}

	/**
	 * @return the correoGerente
	 */
	public String getCorreoGerente() {
		return correoGerente;
	}

	/**
	 * @param correoGerente the correoGerente to set
	 */
	public void setCorreoGerente(String correoGerente) {
		this.correoGerente = correoGerente;
	}

}
