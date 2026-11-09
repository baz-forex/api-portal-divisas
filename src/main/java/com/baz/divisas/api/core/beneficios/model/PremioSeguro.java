package com.baz.divisas.api.core.beneficios.model;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.bson.types.ObjectId;

import com.baz.divisas.general.model.Actualizar;
import com.baz.divisas.general.model.Registrar;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * PremioSeguro
 */
public class PremioSeguro {

	@BsonId
	@BsonRepresentation(BsonType.OBJECT_ID)
	@JsonIgnore
	private String id;	
	
	@BsonProperty("nombre_seguro")
	@NotEmpty(message = "Requerido", groups= {Registrar.class, Actualizar.class})
	@ApiModelProperty(value="Nombre", example="Clientes", position=1, required=true)
	private String nombre;	
	
	@NotNull(message = "Requerido", groups= {Registrar.class, Actualizar.class})
	@Pattern(regexp="^(activo|desactivado)$", message="Estatus invalido",
			 groups= {Registrar.class, Actualizar.class})
	@ApiModelProperty(value="Estatus", example="desactivado", position=2, required=true)
	private String estatus;
	
	@BsonProperty("tipo_operacion")
	@NotNull(message = "Requerido", groups= {Registrar.class, Actualizar.class})
	@Size(min=1, message="Se requiere mínimo un tipo de operacion", groups={Registrar.class, Actualizar.class})
	@ApiModelProperty(value="Tipo de Operación", position=3, required=true)
	private List<BeanCatalogo> tipoOperacion;
	
	@BsonProperty("tipo_cliente")
	@NotNull(message = "Requerido", groups= {Registrar.class, Actualizar.class})
	@Size(min=1, message="Se requiere mínimo un tipo de cliente", groups={Registrar.class, Actualizar.class})
	@ApiModelProperty(value="Tipo de Cliente", position=4, required=true)
	private List<BeanCatalogo> tipoCliente;
	
	@NotEmpty(message = "Requerido", groups= {Registrar.class, Actualizar.class})
	@Pattern(regexp="^(USD|CAD|EUR|GBP)$", message="Moneda invalida",
	 groups= {Registrar.class, Actualizar.class})
	@ApiModelProperty(value="Moneda", example="USD", position=5, required=true)
	private String moneda;
	
	@NotNull(message = "Requerido", groups= {Registrar.class, Actualizar.class})
	@Size(min=1, message="Se requiere mínimo un dia", groups={Registrar.class, Actualizar.class})
	@ApiModelProperty(value="Tipo de Operación", example="[\"LUNES\",\"VIERNES\"]", position=6, required=true)
	private List<String> dias;
	
	@BsonProperty("hora_inicio")
	@NotEmpty(message = "Requerido", groups= {Registrar.class, Actualizar.class})
	@ApiModelProperty(value="Hora de Inicio", example="00:00", position=7, required=true)
	private String horaInicio;
	
	@BsonProperty("hora_fin")
	@NotEmpty(message = "Requerido", groups= {Registrar.class, Actualizar.class})
	@ApiModelProperty(value="Hora de Fin", example="23:59", position=8, required=true)
	private String horaFin;
	
	@BsonProperty("mensaje_cajero")
	@NotNull(message = "Requerido", groups= {Registrar.class, Actualizar.class})
	@ApiModelProperty(value="Mensajes Cajero", position=11, required=true)
	private BeanMensaje mensajeCajero;
	
	@BsonProperty("mensaje_ticket")
	@NotNull(message = "Requerido", groups= {Registrar.class, Actualizar.class})
	@ApiModelProperty(value="Mensaje Ticket", position=12, required=true)
	private BeanMensajeConDetalle mensajeTicket;
	
	@NotNull(message = "Requerido", groups= {Registrar.class, Actualizar.class})
	@ApiModelProperty(value="Regiones", position=10, required=true)
	private SegmentoSucursal regiones;
	
	@BsonProperty("monto_minimo_entrega")
	@Min(value=1, message="Debe ser mayor a 0", groups= {Registrar.class, Actualizar.class})
	@ApiModelProperty(value="Monto mínimo", example="20", position=9, required=true)
	private int montoMinimo;
	
	/**
	 * Constructo de la clase
	 */
	public PremioSeguro() {
		super();
		this.id = ObjectId.get().toHexString();
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
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the estatus
	 */
	public String getEstatus() {
		return estatus;
	}
	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	/**
	 * @return the tipoOperacion
	 */
	public List<BeanCatalogo> getTipoOperacion() {
		return tipoOperacion;
	}
	/**
	 * @param tipoOperacion the tipoOperacion to set
	 */
	public void setTipoOperacion(List<BeanCatalogo> tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}
	/**
	 * @return the tipoCliente
	 */
	public List<BeanCatalogo> getTipoCliente() {
		return tipoCliente;
	}
	/**
	 * @param tipoCliente the tipoCliente to set
	 */
	public void setTipoCliente(List<BeanCatalogo> tipoCliente) {
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
	 * @return the dias
	 */
	public List<String> getDias() {
		return dias;
	}
	/**
	 * @param dias the dias to set
	 */
	public void setDias(List<String> dias) {
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
	 * @return the regiones
	 */
	public SegmentoSucursal getRegiones() {
		return regiones;
	}
	/**
	 * @param regiones the regiones to set
	 */
	public void setRegiones(SegmentoSucursal regiones) {
		this.regiones = regiones;
	}
	/**
	 * @return the montoMinimo
	 */
	public int getMontoMinimo() {
		return montoMinimo;
	}
	/**
	 * @param montoMinimo the montoMinimo to set
	 */
	public void setMontoMinimo(int montoMinimo) {
		this.montoMinimo = montoMinimo;
	}
	
}
