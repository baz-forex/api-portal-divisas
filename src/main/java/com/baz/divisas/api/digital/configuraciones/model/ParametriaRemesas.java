package com.baz.divisas.api.digital.configuraciones.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.baz.divisas.api.catalogos.model.Catalogo;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

/**
 * Clase PaisesRestringidos
 * 
 * @author 1027346 Martinez Yonathan
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParametriaRemesas {

	@ApiModelProperty(value="Beneficio Remesas activo", example="true", position=1, required=true)
	private boolean activo;
	
	@NotEmpty(message = "Requerido")
	@Pattern(regexp="[0-9]{1,5}", message="Debe ser numérico de longitud máxima 5")
	@ApiModelProperty(value="Monto mínimo", example="150", position=2, required=true)
	private String montoMinimoOperacion;
	
	@NotEmpty(message = "Requerido")
	@Pattern(regexp="[0-9]{1,3}", message="Debe ser numérico de longitud máxima 3")
	@ApiModelProperty(value="Vigencia beneficio", example="2", position=3, required=true)
	private String vigenciaBeneficio;
	
	@NotEmpty(message = "Requerido")
	@Pattern(regexp="^([0-9]{1,3}(\\.[0-9]{1,2})?)$", message="Valor invalido")
	@ApiModelProperty(value="Monto beneficio", example="\"0.30\"", position=4, required=true)
	private String montoBeneficio;
	
	@NotEmpty(message = "Requerido")
	@ApiModelProperty(value="Tipo beneficio", example="Remesas App", position=5, required=true)
	private String tipoBeneficio;
	
	@NotEmpty(message = "Requerido")
	@Pattern(regexp="[0-9]{1,5}", message="Debe ser numérico de longitud máxima 5")
	@ApiModelProperty(value="Monto sugerido", example="100", position=6, required=true)	
	private String montoSugerenciaUno;
	
	@NotEmpty(message = "Requerido")
	@Pattern(regexp="[0-9]{1,5}", message="Debe ser numérico de longitud máxima 5")
	@ApiModelProperty(value="Monto sugerido", example="200", position=7, required=true)
	private String montoSugerenciaDos;
	
	@NotNull(message = "Requerido")
	@Valid
	@ApiModelProperty(value="Tipo de cliente", position=8, required=true)
	private List<Catalogo> tipoCliente;
	
	@NotNull(message = "Requerido")
	@Valid
	@ApiModelProperty(value="Tipo de cuenta", position=9, required=true)	
	private List<Catalogo> tipoCuenta;
	
	@NotNull(message = "Requerido")
	@Valid
	@ApiModelProperty(value="Subproductos", position=10, required=true)
	private List<Catalogo> subProductos;

	/**
	 * Constructor de la clase
	 */
	public ParametriaRemesas() {
		super();
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
	 * @return the montoMinimoOperacion
	 */
	public String getMontoMinimoOperacion() {
		return montoMinimoOperacion;
	}

	/**
	 * @param montoMinimoOperacion the montoMinimoOperacion to set
	 */
	public void setMontoMinimoOperacion(String montoMinimoOperacion) {
		this.montoMinimoOperacion = montoMinimoOperacion;
	}

	/**
	 * @return the vigenciaBeneficio
	 */
	public String getVigenciaBeneficio() {
		return vigenciaBeneficio;
	}

	/**
	 * @param vigenciaBeneficio the vigenciaBeneficio to set
	 */
	public void setVigenciaBeneficio(String vigenciaBeneficio) {
		this.vigenciaBeneficio = vigenciaBeneficio;
	}

	/**
	 * @return the montoBeneficio
	 */
	public String getMontoBeneficio() {
		return montoBeneficio;
	}

	/**
	 * @param montoBeneficio the montoBeneficio to set
	 */
	public void setMontoBeneficio(String montoBeneficio) {
		this.montoBeneficio = montoBeneficio;
	}

	/**
	 * @return the tipoBeneficio
	 */
	public String getTipoBeneficio() {
		return tipoBeneficio;
	}

	/**
	 * @param tipoBeneficio the tipoBeneficio to set
	 */
	public void setTipoBeneficio(String tipoBeneficio) {
		this.tipoBeneficio = tipoBeneficio;
	}

	/**
	 * @return the montoSugerenciaUno
	 */
	public String getMontoSugerenciaUno() {
		return montoSugerenciaUno;
	}

	/**
	 * @param montoSugerenciaUno the montoSugerenciaUno to set
	 */
	public void setMontoSugerenciaUno(String montoSugerenciaUno) {
		this.montoSugerenciaUno = montoSugerenciaUno;
	}

	/**
	 * @return the montoSugerenciaDos
	 */
	public String getMontoSugerenciaDos() {
		return montoSugerenciaDos;
	}

	/**
	 * @param montoSugerenciaDos the montoSugerenciaDos to set
	 */
	public void setMontoSugerenciaDos(String montoSugerenciaDos) {
		this.montoSugerenciaDos = montoSugerenciaDos;
	}

	/**
	 * @return the tipoCliente
	 */
	public List<Catalogo> getTipoCliente() {
		return tipoCliente;
	}

	/**
	 * @param tipoCliente the tipoCliente to set
	 */
	public void setTipoCliente(List<Catalogo> tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	/**
	 * @return the tipoCuenta
	 */
	public List<Catalogo> getTipoCuenta() {
		return tipoCuenta;
	}

	/**
	 * @param tipoCuenta the tipoCuenta to set
	 */
	public void setTipoCuenta(List<Catalogo> tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	/**
	 * @return the subProductos
	 */
	public List<Catalogo> getSubProductos() {
		return subProductos;
	}

	/**
	 * @param subProductos the subProductos to set
	 */
	public void setSubProductos(List<Catalogo> subProductos) {
		this.subProductos = subProductos;
	}
	
}
