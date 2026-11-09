package com.baz.divisas.api.digital.configuraciones.model;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModelProperty;

/**
 * Clase PaisesRestringidos
 * 
 * @author 1027346 Martinez Yonathan
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParametriaOperacion {

	@NotEmpty(message = "Requerido")
	@ApiModelProperty(value="Descripcion producto", example="Cambia tus dólares", position=1, required=true)
	private String descripcion;
	
	@SerializedName("compra_activa")
	@ApiModelProperty(value="Compra activa", example="true", position=2, required=true)
	private boolean compraActiva;

	@SerializedName("venta_activa")
	@ApiModelProperty(value="Venta activa", example="true", position=3, required=true)
	private boolean ventaActiva;

	@SerializedName("geolocalizacion_precios")
	@ApiModelProperty(value="Geolocalizacion activa", example="true", position=4, required=true)
	private boolean geolocalizacionPrecios;
	
	@SerializedName("vigencia_en_dias")
	@NotNull(message = "Requerido")
	@Pattern(regexp="[0-9]{1,3}", message="Debe ser numérico de longitud máxima 3")
	@ApiModelProperty(value="Dias de vigencia", example="3", position=5, required=true)
	private String vigenciaEnDias;

	@NotNull(message = "Requerido")
	@ApiModelProperty(value="Montos de operación", position=6, required=true)
	@Valid
	private RangoMontos montos;

	@SerializedName("operaciones_permitidas")
	@NotNull(message = "Requerido")
	@ApiModelProperty(value="Operaciones permitidas", position=7, required=true)
	@Valid
	private OperacionesPermitidas operacionesPermitidas;

	@SerializedName("operaciones_canceladas")
	@NotNull(message = "Requerido")
	@ApiModelProperty(value="Operaciones canceladas", position=8, required=true)
	@Valid
	private OperacionesCanceladas operacionesCanceladas;
	
	@NotNull(message = "Requerido")
	@ApiModelProperty(value="Fluctuación", position=9, required=true)
	@Valid
	private Monto fluctuacion;

	/**
	 * Constructor de la clase
	 */
	public ParametriaOperacion() {
		super();
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
	 * @return the compraActiva
	 */
	public boolean isCompraActiva() {
		return compraActiva;
	}

	/**
	 * @param compraActiva the compraActiva to set
	 */
	public void setCompraActiva(boolean compraActiva) {
		this.compraActiva = compraActiva;
	}

	/**
	 * @return the ventaActiva
	 */
	public boolean isVentaActiva() {
		return ventaActiva;
	}

	/**
	 * @param ventaActiva the ventaActiva to set
	 */
	public void setVentaActiva(boolean ventaActiva) {
		this.ventaActiva = ventaActiva;
	}

	/**
	 * @return the geolocalizacionPrecios
	 */
	public boolean isGeolocalizacionPrecios() {
		return geolocalizacionPrecios;
	}

	/**
	 * @param geolocalizacionPrecios the geolocalizacionPrecios to set
	 */
	public void setGeolocalizacionPrecios(boolean geolocalizacionPrecios) {
		this.geolocalizacionPrecios = geolocalizacionPrecios;
	}

	/**
	 * @return the vigenciaEnDias
	 */
	public String getVigenciaEnDias() {
		return vigenciaEnDias;
	}

	/**
	 * @param vigenciaEnDias the vigenciaEnDias to set
	 */
	public void setVigenciaEnDias(String vigenciaEnDias) {
		this.vigenciaEnDias = vigenciaEnDias;
	}

	/**
	 * @return the montos
	 */
	public RangoMontos getMontos() {
		return montos;
	}

	/**
	 * @param montos the montos to set
	 */
	public void setMontos(RangoMontos montos) {
		this.montos = montos;
	}

	/**
	 * @return the operacionesPermitidas
	 */
	public OperacionesPermitidas getOperacionesPermitidas() {
		return operacionesPermitidas;
	}

	/**
	 * @param operacionesPermitidas the operacionesPermitidas to set
	 */
	public void setOperacionesPermitidas(OperacionesPermitidas operacionesPermitidas) {
		this.operacionesPermitidas = operacionesPermitidas;
	}

	/**
	 * @return the operacionesCanceladas
	 */
	public OperacionesCanceladas getOperacionesCanceladas() {
		return operacionesCanceladas;
	}

	/**
	 * @param operacionesCanceladas the operacionesCanceladas to set
	 */
	public void setOperacionesCanceladas(OperacionesCanceladas operacionesCanceladas) {
		this.operacionesCanceladas = operacionesCanceladas;
	}

	/**
	 * @return the fluctuacion
	 */
	public Monto getFluctuacion() {
		return fluctuacion;
	}

	/**
	 * @param fluctuacion the fluctuacion to set
	 */
	public void setFluctuacion(Monto fluctuacion) {
		this.fluctuacion = fluctuacion;
	}

}
