/**
 * 
 */
package com.baz.divisas.api.core.beneficios.model;

/**
 * @author B86960
 *
 */
public class BeanEntrega {
	private boolean conLimite;
	private int tipo;
	private String descripcion;
	private int limiteEntregas;
	private String periodoEntregas;
	private int rangoMontoInicio;
	private int rangoMontoFin;
	private int operacionesAcumuladas;
	private int intervaloVentanilla;
	private Boolean landingPage;
	private int intervaloLandingPage;
	private double monto;
	private double montoLandingPage;
	private int maximoOperaciones;
	private String periodoAcumulado;
	private int cantidadPeriodoAcumulado;
	private String tipoAcumulado;
	/**
	 * Constructor
	 */
	public BeanEntrega() {
		super();
	}
	
	/**
	 * @return the tipo
	 */
	public int getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
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
	 * @return the limiteEntregas
	 */
	public int getLimiteEntregas() {
		return limiteEntregas;
	}
	/**
	 * @param limiteEntregas the limiteEntregas to set
	 */
	public void setLimiteEntregas(int limiteEntregas) {
		this.limiteEntregas = limiteEntregas;
	}
	/**
	 * @return the periodoEntregas
	 */
	public String getPeriodoEntregas() {
		return periodoEntregas;
	}
	/**
	 * @param periodoEntregas the periodoEntregas to set
	 */
	public void setPeriodoEntregas(String periodoEntregas) {
		this.periodoEntregas = periodoEntregas;
	}
	/**
	 * @return the rangoMontoInicio
	 */
	public int getRangoMontoInicio() {
		return rangoMontoInicio;
	}
	/**
	 * @param rangoMontoInicio the rangoMontoInicio to set
	 */
	public void setRangoMontoInicio(int rangoMontoInicio) {
		this.rangoMontoInicio = rangoMontoInicio;
	}
	/**
	 * @return the rangoMontoFin
	 */
	public int getRangoMontoFin() {
		return rangoMontoFin;
	}
	/**
	 * @param rangoMontoFin the rangoMontoFin to set
	 */
	public void setRangoMontoFin(int rangoMontoFin) {
		this.rangoMontoFin = rangoMontoFin;
	}
	/**
	 * @return the operacionesAcumuladas
	 */
	public int getOperacionesAcumuladas() {
		return operacionesAcumuladas;
	}
	/**
	 * @param operacionesAcumuladas the operacionesAcumuladas to set
	 */
	public void setOperacionesAcumuladas(int operacionesAcumuladas) {
		this.operacionesAcumuladas = operacionesAcumuladas;
	}
	/**
	 * @return the intervaloVentanilla
	 */
	public int getIntervaloVentanilla() {
		return intervaloVentanilla;
	}
	/**
	 * @param intervaloVentanilla the intervaloVentanilla to set
	 */
	public void setIntervaloVentanilla(int intervaloVentanilla) {
		this.intervaloVentanilla = intervaloVentanilla;
	}
	/**
	 * @return the landingPage
	 */
	public Boolean getLandingPage() {
		return landingPage;
	}
	/**
	 * @param landingPage the landingPage to set
	 */
	public void setLandingPage(Boolean landingPage) {
		this.landingPage = landingPage;
	}
	/**
	 * @return the intervalolandingPage
	 */
	public int getIntervaloLandingPage() {
		return intervaloLandingPage;
	}
	/**
	 * @param intervalolandingPage the intervalolandingPage to set
	 */
	public void setIntervaloLandingPage(int intervaloLandingPage) {
		this.intervaloLandingPage = intervaloLandingPage;
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
	 * @return the montoLandingPage
	 */
	public double getMontoLandingPage() {
		return montoLandingPage;
	}
	/**
	 * @param montoLandingPage the montoLandingPage to set
	 */
	public void setMontoLandingPage(double montoLandingPage) {
		this.montoLandingPage = montoLandingPage;
	}

	public boolean isConLimite() {
		return conLimite;
	}

	public void setConLimite(boolean conLimite) {
		this.conLimite = conLimite;
	}

	/**
	 * @return the maximoOperaciones
	 */
	public int getMaximoOperaciones() {
		return maximoOperaciones;
	}

	/**
	 * @param maximoOperaciones the maximoOperaciones to set
	 */
	public void setMaximoOperaciones(int maximoOperaciones) {
		this.maximoOperaciones = maximoOperaciones;
	}

	/**
	 * @return the periodoAcumulado
	 */
	public String getPeriodoAcumulado() {
		return periodoAcumulado;
	}

	/**
	 * @param periodoAcumulado the periodoAcumulado to set
	 */
	public void setPeriodoAcumulado(String periodoAcumulado) {
		this.periodoAcumulado = periodoAcumulado;
	}

	/**
	 * @return the cantidadPeriodoAcumulado
	 */
	public int getCantidadPeriodoAcumulado() {
		return cantidadPeriodoAcumulado;
	}

	/**
	 * @param cantidadPeriodoAcumulado the cantidadPeriodoAcumulado to set
	 */
	public void setCantidadPeriodoAcumulado(int cantidadPeriodoAcumulado) {
		this.cantidadPeriodoAcumulado = cantidadPeriodoAcumulado;
	}

	/**
	 * @return the tipoAcumulado
	 */
	public String getTipoAcumulado() {
		return tipoAcumulado;
	}

	/**
	 * @param tipoAcumulado the tipoAcumulado to set
	 */
	public void setTipoAcumulado(String tipoAcumulado) {
		this.tipoAcumulado = tipoAcumulado;
	}
	
}
