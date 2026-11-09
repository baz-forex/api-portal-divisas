/**
 * 
 */
package com.baz.divisas.api.core.beneficios.model;

import java.util.ArrayList;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * Bean de campañas
 * @author B86960
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BeanCampania {

	@BsonId
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String id;
	@NotNull(message = "nombre es requerido para registro")
    @NotBlank(message = "nombre no tiene ninguna informacion")
	private String nombre;
	private String fechaRegistro;
	@NotNull(message = "fechaInicio es requerido para registro")
    @NotBlank(message = "fechaInicio no tiene ninguna informacion")
	private String fechaInicio;
	@NotNull(message = "fechaFin es requerido para registro")
    @NotBlank(message = "fechaFin no tiene ninguna informacion")
	private String fechaFin;
	private String estatus;
	private int prioridad;
	private Boolean acumulado;
	private Boolean unica;
	private Boolean permanente;
	private Boolean afectaInventario;
	private BeanPresupuestos presupuestos;
	private ArrayList<BeanPremio> premios;
	/**
	 * Constructor
	 */
	public BeanCampania() {
		super();
		premios = new ArrayList<>();
	}
	/**
	 * @return the _id
	 */ 
	public String getId() {
		return id;
	}
	/**
	 * @param _id the _id to set
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
	 * @return the fechaRegistro
	 */
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	/**
	 * @return the fechaInicio
	 */
	public String getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 * @return the fechaFin
	 */
	public String getFechaFin() {
		return fechaFin;
	}
	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
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
	 * @return the prioridad
	 */
	public int getPrioridad() {
		return prioridad;
	}
	/**
	 * @param prioridad the prioridad to set
	 */
	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
	}
	/**
	 * @return the acumulado
	 */
	public Boolean getAcumulado() {
		return acumulado;
	}
	/**
	 * @param acumulado the acumulado to set
	 */
	public void setAcumulado(Boolean acumulado) {
		this.acumulado = acumulado;
	}
	/**
	 * @return the unica
	 */
	public Boolean getUnica() {
		return unica;
	}
	/**
	 * @param unica the unica to set
	 */
	public void setUnica(Boolean unica) {
		this.unica = unica;
	}
	/**
	 * @return the permanente
	 */
	public Boolean getPermanente() {
		return permanente;
	}
	/**
	 * @param permanente the permanente to set
	 */
	public void setPermanente(Boolean permanente) {
		this.permanente = permanente;
	}
	/**
	 * @return the afectaInventario
	 */
	public Boolean getAfectaInventario() {
		return afectaInventario;
	}
	/**
	 * @param afectaInventario the afectaInventario to set
	 */
	public void setAfectaInventario(Boolean afectaInventario) {
		this.afectaInventario = afectaInventario;
	}
	/**
	 * @return the presupuestos
	 */
	public BeanPresupuestos getPresupuestos() {
		return presupuestos;
	}
	/**
	 * @param presupuestos the presupuestos to set
	 */
	public void setPresupuestos(BeanPresupuestos presupuestos) {
		this.presupuestos = presupuestos;
	}
	/**
	 * @return the premios
	 */
	public ArrayList<BeanPremio> getPremios() {
		return premios;
	}
	/**
	 * @param premios the premios to set
	 */
	public void setPremios(ArrayList<BeanPremio> premios) {
		this.premios = premios;
	}
	
}
