package com.baz.divisas.api.tef.general.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.baz.divisas.api.tef.general.LimitesOnUpdateStatus;
import com.baz.divisas.api.tef.general.LimitesOnUpsert;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Clase Limites
 * 
 * @author 1027346 ymartinezb
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Limites {

	@NotNull(message = "Requerido", groups= {LimitesOnUpdateStatus.class})
	private boolean activo;
	@NotNull(message = "Requerido", groups= {LimitesOnUpsert.class})
	private MontosLimite diario;
	@NotNull(message = "Requerido", groups= {LimitesOnUpsert.class})
	private MontosLimite mensual;
	
	@NotBlank(message = "Requerido no vacios", groups= {LimitesOnUpdateStatus.class, LimitesOnUpsert.class})
	@NotNull(message = "Requerido", groups= {LimitesOnUpdateStatus.class, LimitesOnUpsert.class})
	private String codigoDivisa;
	@NotBlank(message = "Requerido no vacios", groups= {LimitesOnUpdateStatus.class, LimitesOnUpsert.class})
	@NotNull(message = "Requerido", groups= {LimitesOnUpdateStatus.class, LimitesOnUpsert.class})
	private String idTipoOperacion;
	
	private String idBitacoraAlta;
	private String idBitacoraActualizacion;
	
	/**
	 * Constructor de la clase
	 */
	public Limites() {
		super();
	}
	
	/**
	 * Constructor de la clase
	 * 
	 * @param activo - estatus
	 * @param diario - Limite Diario
	 * @param mensual - Limite Mensual
	 */
	public Limites(boolean activo, MontosLimite diario, MontosLimite mensual) {
		super();
		this.activo = activo;
		this.diario = diario;
		this.mensual = mensual;
	}
	
	public Limites(String idTipoOperacion, String codigoDivisa, MontosLimite diario, MontosLimite mensual,boolean activo) {
		super();
		this.idTipoOperacion = idTipoOperacion;
		this.codigoDivisa = codigoDivisa;
		this.diario = diario;
		this.mensual = mensual;
		this.activo = activo;
	}
	
	public Limites(String codigoDivisa, String idTipoOperacion, MontosLimite diario, MontosLimite mensual,
			boolean activo, String idBitacoraAlta) {
		super();
		this.codigoDivisa = codigoDivisa;
		this.idTipoOperacion = idTipoOperacion;
		this.diario = diario;
		this.mensual = mensual;
		this.activo = activo;
		this.idBitacoraAlta = idBitacoraAlta;
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
	 * @return the diario
	 */
	public MontosLimite getDiario() {
		return diario;
	}
	
	/**
	 * @param diario the diario to set
	 */
	public void setDiario(MontosLimite diario) {
		this.diario = diario;
	}
	
	/**
	 * @return the mensual
	 */
	public MontosLimite getMensual() {
		return mensual;
	}
	
	/**
	 * @param mensual the mensual to set
	 */
	public void setMensual(MontosLimite mensual) {
		this.mensual = mensual;
	}

	/**
	 * @return the codigoDivisa
	 */
	public String getCodigoDivisa() {
		return codigoDivisa;
	}

	/**
	 * @param codigoDivisa the codigoDivisa to set
	 */
	public void setCodigoDivisa(String codigoDivisa) {
		this.codigoDivisa = codigoDivisa;
	}

	/**
	 * @return the idTipoOperacion
	 */
	public String getIdTipoOperacion() {
		return idTipoOperacion;
	}

	/**
	 * @param idTipoOperacion the idTipoOperacion to set
	 */
	public void setIdTipoOperacion(String idTipoOperacion) {
		this.idTipoOperacion = idTipoOperacion;
	}

	/**
	 * @return the idBitacoraAlta
	 */
	public String getIdBitacoraAlta() {
		return idBitacoraAlta;
	}

	/**
	 * @param idBitacoraAlta the idBitacoraAlta to set
	 */
	public void setIdBitacoraAlta(String idBitacoraAlta) {
		this.idBitacoraAlta = idBitacoraAlta;
	}

	/**
	 * @return the idBitacoraActualizacion
	 */
	public String getIdBitacoraActualizacion() {
		return idBitacoraActualizacion;
	}

	/**
	 * @param idBitacoraActualizacion the idBitacoraActualizacion to set
	 */
	public void setIdBitacoraActualizacion(String idBitacoraActualizacion) {
		this.idBitacoraActualizacion = idBitacoraActualizacion;
	}

}
