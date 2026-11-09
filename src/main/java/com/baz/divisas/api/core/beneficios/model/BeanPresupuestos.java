/**
 * 
 */
package com.baz.divisas.api.core.beneficios.model;

/**
 * @author B86960
 *
 */
public class BeanPresupuestos {

	private String idDivisa;
	private int limitePresupuesto;
	private int presupuestoActual;
	private Boolean estatus;
	/**
	 * Constructor
	 */
	public BeanPresupuestos() {
		super();
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
	 * @return the limitePresupuesto
	 */
	public int getLimitePresupuesto() {
		return limitePresupuesto;
	}
	/**
	 * @param limitePresupuesto the limitePresupuesto to set
	 */
	public void setLimitePresupuesto(int limitePresupuesto) {
		this.limitePresupuesto = limitePresupuesto;
	}
	/**
	 * @return the presupuestoActual
	 */
	public int getPresupuestoActual() {
		return presupuestoActual;
	}
	/**
	 * @param presupuestoActual the presupuestoActual to set
	 */
	public void setPresupuestoActual(int presupuestoActual) {
		this.presupuestoActual = presupuestoActual;
	}
	/**
	 * @return the estatus
	 */
	public Boolean getEstatus() {
		return estatus;
	}
	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}
	
}
