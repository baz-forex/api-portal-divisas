package com.baz.divisas.api.core.configuraciones.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import com.baz.divisas.general.model.Actualizar;
import com.baz.divisas.general.model.Registrar;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Clase SegmentoSucursal
 * @author B813196
 * itoxtle
 */
public class SegmentoSucursal {

	@BsonId
	@BsonRepresentation(BsonType.OBJECT_ID)
	@Pattern(regexp="^[0-9a-f]{24}", message="Debe ser un valor hexadecimal de longitud 24", 
			 groups=Actualizar.class)
	private String id;
	@JsonIgnore
	private String negocio = "DIV";
	@BsonProperty("id_region")
	@NotEmpty(message = "Requerido", groups= {Registrar.class, Actualizar.class})
	private String idRegion;
	@NotEmpty(message = "Requerido", groups= {Registrar.class, Actualizar.class})
	private String nombre;
	@NotEmpty(message = "Requerido", groups= {Registrar.class, Actualizar.class})
	private String descripcion;
	@NotEmpty(message = "Requerido", groups= {Registrar.class, Actualizar.class})
	private String detalle;
	@NotEmpty(message = "Requerido", groups= {Registrar.class, Actualizar.class})
	private String tipo;
	private boolean activo;
	private Set<@Pattern(regexp="[0-9]{4}", message="Las sucursales deben ser numéricas de longitud 4", 
						 groups= {Registrar.class, Actualizar.class}) String>
			sucursales = new HashSet<>();
	
	/**
	 * Constructor de la clase
	 */
	public SegmentoSucursal() {
		super();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNegocio() {
		return negocio;
	}
	public void setNegocio(String negocio) {
		this.negocio = negocio;
	}
	public String getIdRegion() {
		return idRegion;
	}
	public void setIdRegion(String idRegion) {
		this.idRegion = idRegion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public Set<String> getSucursales() {
		return Collections.unmodifiableSet(sucursales);
	}
	public void setSucursales(Set<String> sucursales) {
		this.sucursales = Collections.unmodifiableSet(sucursales);
	}

}
