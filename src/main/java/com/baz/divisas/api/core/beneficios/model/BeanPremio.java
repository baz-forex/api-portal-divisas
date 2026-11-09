/**
 * 
 */
package com.baz.divisas.api.core.beneficios.model;

import java.util.ArrayList;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;


/**
 * @author B86960
 *
 */
public class BeanPremio {

	@BsonId
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String id;
	private String descripcion;
	private int tipoBeneficio;
	private String descripcionTipoBeneficio;
	private Double valor;
	private boolean estatus;
	private ArrayList<BeanCatalogo> tipoOperacion;
	private ArrayList<BeanCatalogo> tipoCliente;
	private String moneda;
	private int mesesAntiguedad;
	private ArrayList<String> dias;
	private String horaInicio;
	private String horaFin;
	private ArrayList<String> genero;
	private ArrayList<BeanRangoEdades> rangoEdades;
	private ArrayList<BeanCatalogo> actividadEconomica;
	private BeanMensaje mensajeCajero;
	private BeanMensajeConDetalle mensajeTicket;
	private int prioridadPremio;
	private BeanSegmentoSucursal segmentoSucursal;
	private BeanEntrega entrega;
	private ArrayList<String> perfilCliente;
	private int rangoInicial;
	private int rangoFinal;
	
	/*
	private ArrayList<String> beneficios
	private ArrayList<String> promociones
	private BeanAniversario aniversario
	private ArrayList<BeanSucursales> sucursales
	private int entregarEnvisitaNo*/
	/**
	 * Constructor
	 */
	public BeanPremio() {
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
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the tipoBeneficio
	 */
	public int getTipoBeneficio() {
		return tipoBeneficio;
	}

	/**
	 * @param tipoBeneficio the tipoBeneficio to set
	 */
	public void setTipoBeneficio(int tipoBeneficio) {
		this.tipoBeneficio = tipoBeneficio;
	}

	/**
	 * @return the descripcionTipoBeneficio
	 */
	public String getDescripcionTipoBeneficio() {
		return descripcionTipoBeneficio;
	}

	/**
	 * @param descripcionTipoBeneficio the descripcionTipoBeneficio to set
	 */
	public void setDescripcionTipoBeneficio(String descripcionTipoBeneficio) {
		this.descripcionTipoBeneficio = descripcionTipoBeneficio;
	}

	/**
	 * @return the valor
	 */
	public Double getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(Double valor) {
		this.valor = valor;
	}

	/**
	 * @return the estatus
	 */
	public boolean getEstatus() {
		return estatus;
	}

	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(boolean estatus) {
		this.estatus = estatus;
	}

	/**
	 * @return the tipoOperacion
	 */
	public ArrayList<BeanCatalogo> getTipoOperacion() {
		return tipoOperacion;
	}

	/**
	 * @param tipoOperacion the tipoOperacion to set
	 */
	public void setTipoOperacion(ArrayList<BeanCatalogo> tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	/**
	 * @return the tipoCliente
	 */
	public ArrayList<BeanCatalogo> getTipoCliente() {
		return tipoCliente;
	}

	/**
	 * @param tipoCliente the tipoCliente to set
	 */
	public void setTipoCliente(ArrayList<BeanCatalogo> tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	/**
	 * @return the moneda
	 */
	public String getMoneda() {
		return moneda;
	}

	/**
	 * @param moneda the moneda to set
	 */
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	/**
	 * @return the mesesAntiguedad
	 */
	public int getMesesAntiguedad() {
		return mesesAntiguedad;
	}

	/**
	 * @param mesesAntiguedad the mesesAntiguedad to set
	 */
	public void setMesesAntiguedad(int mesesAntiguedad) {
		this.mesesAntiguedad = mesesAntiguedad;
	}

	/**
	 * @return the dias
	 */
	public ArrayList<String> getDias() {
		return dias;
	}

	/**
	 * @param dias the dias to set
	 */
	public void setDias(ArrayList<String> dias) {
		this.dias = dias;
	}

	/**
	 * @return the horaInicio
	 */
	public String getHoraInicio() {
		return horaInicio;
	}

	/**
	 * @param horaInicio the horaInicio to set
	 */
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	/**
	 * @return the horaFin
	 */
	public String getHoraFin() {
		return horaFin;
	}

	/**
	 * @param horaFin the horaFin to set
	 */
	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	/**
	 * @return the genero
	 */
	public ArrayList<String> getGenero() {
		return genero;
	}

	/**
	 * @param genero the genero to set
	 */
	public void setGenero(ArrayList<String> genero) {
		this.genero = genero;
	}

	/**
	 * @return the rangoEdades
	 */
	public ArrayList<BeanRangoEdades> getRangoEdades() {
		return rangoEdades;
	}

	/**
	 * @param rangoEdades the rangoEdades to set
	 */
	public void setRangoEdades(ArrayList<BeanRangoEdades> rangoEdades) {
		this.rangoEdades = rangoEdades;
	}

	/**
	 * @return the actividadEconomica
	 */
	public ArrayList<BeanCatalogo> getActividadEconomica() {
		return actividadEconomica;
	}

	/**
	 * @param actividadEconomica the actividadEconomica to set
	 */
	public void setActividadEconomica(ArrayList<BeanCatalogo> actividadEconomica) {
		this.actividadEconomica = actividadEconomica;
	}

	/**
	 * @return the mensajeCajero
	 */
	public BeanMensaje getMensajeCajero() {
		return mensajeCajero;
	}

	/**
	 * @param mensajeCajero the mensajeCajero to set
	 */
	public void setMensajeCajero(BeanMensaje mensajeCajero) {
		this.mensajeCajero = mensajeCajero;
	}

	/**
	 * @return the mensajeTicket
	 */
	public BeanMensajeConDetalle getMensajeTicket() {
		return mensajeTicket;
	}

	/**
	 * @param mensajeTicket the mensajeTicket to set
	 */
	public void setMensajeTicket(BeanMensajeConDetalle mensajeTicket) {
		this.mensajeTicket = mensajeTicket;
	}

	/**
	 * @return the prioridadPremio
	 */
	public int getPrioridadPremio() {
		return prioridadPremio;
	}

	/**
	 * @param prioridadPremio the prioridadPremio to set
	 */
	public void setPrioridadPremio(int prioridadPremio) {
		this.prioridadPremio = prioridadPremio;
	}

	/**
	 * @return the segmentoSucursal
	 */
	public BeanSegmentoSucursal getSegmentoSucursal() {
		return segmentoSucursal;
	}

	/**
	 * @param segmentoSucursal the segmentoSucursal to set
	 */
	public void setSegmentoSucursal(BeanSegmentoSucursal segmentoSucursal) {
		this.segmentoSucursal = segmentoSucursal;
	}

	/**
	 * @return the entrega
	 */
	public BeanEntrega getEntrega() {
		return entrega;
	}

	/**
	 * @param entrega the entrega to set
	 */
	public void setEntrega(BeanEntrega entrega) {
		this.entrega = entrega;
	}

	/**
	 * @return the perfilCliente
	 */
	public ArrayList<String> getPerfilCliente() {
		return perfilCliente;
	}

	/**
	 * @param perfilCliente the perfilCliente to set
	 */
	public void setPerfilCliente(ArrayList<String> perfilCliente) {
		this.perfilCliente = perfilCliente;
	}

	/**
	 * @return the rangoInicial
	 */
	public int getRangoInicial() {
		return rangoInicial;
	}

	/**
	 * @param rangoInicial the rangoInicial to set
	 */
	public void setRangoInicial(int rangoInicial) {
		this.rangoInicial = rangoInicial;
	}

	/**
	 * @return the rangoFinal
	 */
	public int getRangoFinal() {
		return rangoFinal;
	}

	/**
	 * @param rangoFinal the rangoFinal to set
	 */
	public void setRangoFinal(int rangoFinal) {
		this.rangoFinal = rangoFinal;
	}
	
}
