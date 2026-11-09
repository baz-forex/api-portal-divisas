package com.baz.divisas.api.tef.sucursales.model;

import com.google.gson.annotations.SerializedName;

/**
 * The Class SucursalReporte.
 */
public class SucursalReporte {

	/** The id. */
	@SerializedName("_id")
	private String id;
	
	/** The fecha ultimo cambio. */
	private String fechaUltimoCambio;	
	
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
	
	/** The divisa usd. */
	private String divisaUsd;
	
	/** The tc sucursal compra usd. */
	private double tcSucursalCompraUsd;
	
	/** The estrategia compra usd. */
	private double estrategiaCompraUsd;
	
	/** The tc sucursal venta usd. */
	private double tcSucursalVentaUsd;
	
	/** The estrategia venta usd. */
	private double estrategiaVentaUsd;
	
	/** The divisa cad. */
	private String divisaCad;
	
	/** The tc sucursal compra cad. */
	private double tcSucursalCompraCad;
	
	/** The estrategia compra cad. */
	private double estrategiaCompraCad;
	
	/** The tc sucursal venta cad. */
	private double tcSucursalVentaCad;
	
	/** The estrategia venta cad. */
	private double estrategiaVentaCad;
	
	/** The divisa eur. */
	private String divisaEur;
	
	/** The tc sucursal compra eur. */
	private double tcSucursalCompraEur;
	
	/** The estrategia compra eur. */
	private double estrategiaCompraEur;
	
	/** The tc sucursal venta eur. */
	private double tcSucursalVentaEur;
	
	/** The estrategia venta eur. */
	private double estrategiaVentaEur;
	
	/** The divisa eur. */
	private String divisaGbp;
	
	/** The tc sucursal compra eur. */
	private double tcSucursalCompraGbp;
	
	/** The estrategia compra eur. */
	private double estrategiaCompraGbp;
	
	/** The tc sucursal venta eur. */
	private double tcSucursalVentaGbp;
	
	/** The estrategia venta eur. */
	private double estrategiaVentaGbp;
	
	/** The divisa eur. */
	private String divisaOlp;
	
	/** The tc sucursal compra eur. */
	private double tcSucursalCompraOlp;
	
	/** The estrategia compra eur. */
	private double estrategiaCompraOlp;
	
	/** The tc sucursal venta eur. */
	private double tcSucursalVentaOlp;
	
	/** The estrategia venta eur. */
	private double estrategiaVentaOlp;
	
	/**
	 * Instantiates a new sucursal reporte.
	 */
	public SucursalReporte() {
		super();
	}

	/**
	 * Gets the fecha ultimo cambio.
	 *
	 * @return the fecha ultimo cambio
	 */
	public String getFechaUltimoCambio() {
		return fechaUltimoCambio;
	}

	/**
	 * Sets the fecha ultimo cambio.
	 *
	 * @param fechaUltimoCambio the new fecha ultimo cambio
	 */
	public void setFechaUltimoCambio(String fechaUltimoCambio) {
		this.fechaUltimoCambio = fechaUltimoCambio;
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
	 * Gets the divisa usd.
	 *
	 * @return the divisa usd
	 */
	public String getDivisaUsd() {
		return divisaUsd;
	}

	/**
	 * Sets the divisa usd.
	 *
	 * @param divisaUsd the new divisa usd
	 */
	public void setDivisaUsd(String divisaUsd) {
		this.divisaUsd = divisaUsd;
	}

	/**
	 * Gets the tc sucursal compra usd.
	 *
	 * @return the tc sucursal compra usd
	 */
	public double getTcSucursalCompraUsd() {
		return tcSucursalCompraUsd;
	}

	/**
	 * Sets the tc sucursal compra usd.
	 *
	 * @param tcSucursalCompraUsd the new tc sucursal compra usd
	 */
	public void setTcSucursalCompraUsd(double tcSucursalCompraUsd) {
		this.tcSucursalCompraUsd = tcSucursalCompraUsd;
	}

	/**
	 * Gets the estrategia compra usd.
	 *
	 * @return the estrategia compra usd
	 */
	public double getEstrategiaCompraUsd() {
		return estrategiaCompraUsd;
	}

	/**
	 * Sets the estrategia compra usd.
	 *
	 * @param estrategiaCompraUsd the new estrategia compra usd
	 */
	public void setEstrategiaCompraUsd(double estrategiaCompraUsd) {
		this.estrategiaCompraUsd = estrategiaCompraUsd;
	}

	/**
	 * Gets the tc sucursal venta usd.
	 *
	 * @return the tc sucursal venta usd
	 */
	public double getTcSucursalVentaUsd() {
		return tcSucursalVentaUsd;
	}

	/**
	 * Sets the tc sucursal venta usd.
	 *
	 * @param tcSucursalVentaUsd the new tc sucursal venta usd
	 */
	public void setTcSucursalVentaUsd(double tcSucursalVentaUsd) {
		this.tcSucursalVentaUsd = tcSucursalVentaUsd;
	}

	/**
	 * Gets the estrategia venta usd.
	 *
	 * @return the estrategia venta usd
	 */
	public double getEstrategiaVentaUsd() {
		return estrategiaVentaUsd;
	}

	/**
	 * Sets the estrategia venta usd.
	 *
	 * @param estrategiaVentaUsd the new estrategia venta usd
	 */
	public void setEstrategiaVentaUsd(double estrategiaVentaUsd) {
		this.estrategiaVentaUsd = estrategiaVentaUsd;
	}

	/**
	 * Gets the divisa cad.
	 *
	 * @return the divisa cad
	 */
	public String getDivisaCad() {
		return divisaCad;
	}

	/**
	 * Sets the divisa cad.
	 *
	 * @param divisaCad the new divisa cad
	 */
	public void setDivisaCad(String divisaCad) {
		this.divisaCad = divisaCad;
	}

	/**
	 * Gets the tc sucursal compra cad.
	 *
	 * @return the tc sucursal compra cad
	 */
	public double getTcSucursalCompraCad() {
		return tcSucursalCompraCad;
	}

	/**
	 * Sets the tc sucursal compra cad.
	 *
	 * @param tcSucursalCompraCad the new tc sucursal compra cad
	 */
	public void setTcSucursalCompraCad(double tcSucursalCompraCad) {
		this.tcSucursalCompraCad = tcSucursalCompraCad;
	}

	/**
	 * Gets the estrategia compra cad.
	 *
	 * @return the estrategia compra cad
	 */
	public double getEstrategiaCompraCad() {
		return estrategiaCompraCad;
	}

	/**
	 * Sets the estrategia compra cad.
	 *
	 * @param estrategiaCompraCad the new estrategia compra cad
	 */
	public void setEstrategiaCompraCad(double estrategiaCompraCad) {
		this.estrategiaCompraCad = estrategiaCompraCad;
	}

	/**
	 * Gets the tc sucursal venta cad.
	 *
	 * @return the tc sucursal venta cad
	 */
	public double getTcSucursalVentaCad() {
		return tcSucursalVentaCad;
	}

	/**
	 * Sets the tc sucursal venta cad.
	 *
	 * @param tcSucursalVentaCad the new tc sucursal venta cad
	 */
	public void setTcSucursalVentaCad(double tcSucursalVentaCad) {
		this.tcSucursalVentaCad = tcSucursalVentaCad;
	}

	/**
	 * Gets the estrategia venta cad.
	 *
	 * @return the estrategia venta cad
	 */
	public double getEstrategiaVentaCad() {
		return estrategiaVentaCad;
	}

	/**
	 * Sets the estrategia venta cad.
	 *
	 * @param estrategiaVentaCad the new estrategia venta cad
	 */
	public void setEstrategiaVentaCad(double estrategiaVentaCad) {
		this.estrategiaVentaCad = estrategiaVentaCad;
	}

	/**
	 * Gets the divisa eur.
	 *
	 * @return the divisa eur
	 */
	public String getDivisaEur() {
		return divisaEur;
	}

	/**
	 * Sets the divisa eur.
	 *
	 * @param divisaEur the new divisa eur
	 */
	public void setDivisaEur(String divisaEur) {
		this.divisaEur = divisaEur;
	}

	/**
	 * Gets the tc sucursal compra eur.
	 *
	 * @return the tc sucursal compra eur
	 */
	public double getTcSucursalCompraEur() {
		return tcSucursalCompraEur;
	}

	/**
	 * Sets the tc sucursal compra eur.
	 *
	 * @param tcSucursalCompraEur the new tc sucursal compra eur
	 */
	public void setTcSucursalCompraEur(double tcSucursalCompraEur) {
		this.tcSucursalCompraEur = tcSucursalCompraEur;
	}

	/**
	 * Gets the estrategia compra eur.
	 *
	 * @return the estrategia compra eur
	 */
	public double getEstrategiaCompraEur() {
		return estrategiaCompraEur;
	}

	/**
	 * Sets the estrategia compra eur.
	 *
	 * @param estrategiaCompraEur the new estrategia compra eur
	 */
	public void setEstrategiaCompraEur(double estrategiaCompraEur) {
		this.estrategiaCompraEur = estrategiaCompraEur;
	}

	/**
	 * Gets the tc sucursal venta eur.
	 *
	 * @return the tc sucursal venta eur
	 */
	public double getTcSucursalVentaEur() {
		return tcSucursalVentaEur;
	}

	/**
	 * Sets the tc sucursal venta eur.
	 *
	 * @param tcSucursalVentaEur the new tc sucursal venta eur
	 */
	public void setTcSucursalVentaEur(double tcSucursalVentaEur) {
		this.tcSucursalVentaEur = tcSucursalVentaEur;
	}

	/**
	 * Gets the estrategia venta eur.
	 *
	 * @return the estrategia venta eur
	 */
	public double getEstrategiaVentaEur() {
		return estrategiaVentaEur;
	}

	/**
	 * Sets the estrategia venta eur.
	 *
	 * @param estrategiaVentaEur the new estrategia venta eur
	 */
	public void setEstrategiaVentaEur(double estrategiaVentaEur) {
		this.estrategiaVentaEur = estrategiaVentaEur;
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
	 * Gets the divisa olp.
	 *
	 * @return the divisa olp
	 */
	public String getDivisaOlp() {
		return divisaOlp;
	}

	/**
	 * Sets the divisa olp.
	 *
	 * @param divisaOlp the new divisa olp
	 */
	public void setDivisaOlp(String divisaOlp) {
		this.divisaOlp = divisaOlp;
	}

	/**
	 * Gets the tc sucursal compra olp.
	 *
	 * @return the tc sucursal compra olp
	 */
	public double getTcSucursalCompraOlp() {
		return tcSucursalCompraOlp;
	}

	/**
	 * Sets the tc sucursal compra olp.
	 *
	 * @param tcSucursalCompraOlp the new tc sucursal compra olp
	 */
	public void setTcSucursalCompraOlp(double tcSucursalCompraOlp) {
		this.tcSucursalCompraOlp = tcSucursalCompraOlp;
	}

	/**
	 * Gets the estrategia compra olp.
	 *
	 * @return the estrategia compra olp
	 */
	public double getEstrategiaCompraOlp() {
		return estrategiaCompraOlp;
	}

	/**
	 * Sets the estrategia compra olp.
	 *
	 * @param estrategiaCompraOlp the new estrategia compra olp
	 */
	public void setEstrategiaCompraOlp(double estrategiaCompraOlp) {
		this.estrategiaCompraOlp = estrategiaCompraOlp;
	}

	/**
	 * Gets the tc sucursal venta olp.
	 *
	 * @return the tc sucursal venta olp
	 */
	public double getTcSucursalVentaOlp() {
		return tcSucursalVentaOlp;
	}

	/**
	 * Sets the tc sucursal venta olp.
	 *
	 * @param tcSucursalVentaOlp the new tc sucursal venta olp
	 */
	public void setTcSucursalVentaOlp(double tcSucursalVentaOlp) {
		this.tcSucursalVentaOlp = tcSucursalVentaOlp;
	}

	/**
	 * Gets the estrategia venta olp.
	 *
	 * @return the estrategia venta olp
	 */
	public double getEstrategiaVentaOlp() {
		return estrategiaVentaOlp;
	}

	/**
	 * Sets the estrategia venta olp.
	 *
	 * @param estrategiaVentaOlp the new estrategia venta olp
	 */
	public void setEstrategiaVentaOlp(double estrategiaVentaOlp) {
		this.estrategiaVentaOlp = estrategiaVentaOlp;
	}

	public String getDivisaGbp() {
		return divisaGbp;
	}

	public void setDivisaGbp(String divisaGbp) {
		this.divisaGbp = divisaGbp;
	}

	public double getTcSucursalCompraGbp() {
		return tcSucursalCompraGbp;
	}

	public void setTcSucursalCompraGbp(double tcSucursalCompraGbp) {
		this.tcSucursalCompraGbp = tcSucursalCompraGbp;
	}

	public double getEstrategiaCompraGbp() {
		return estrategiaCompraGbp;
	}

	public void setEstrategiaCompraGbp(double estrategiaCompraGbp) {
		this.estrategiaCompraGbp = estrategiaCompraGbp;
	}

	public double getTcSucursalVentaGbp() {
		return tcSucursalVentaGbp;
	}

	public void setTcSucursalVentaGbp(double tcSucursalVentaGbp) {
		this.tcSucursalVentaGbp = tcSucursalVentaGbp;
	}

	public double getEstrategiaVentaGbp() {
		return estrategiaVentaGbp;
	}

	public void setEstrategiaVentaGbp(double estrategiaVentaGbp) {
		this.estrategiaVentaGbp = estrategiaVentaGbp;
	}	
	
	
}
