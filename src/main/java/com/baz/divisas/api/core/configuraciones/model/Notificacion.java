package com.baz.divisas.api.core.configuraciones.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import org.bson.types.Binary;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.baz.divisas.general.model.Actualizar;
import com.baz.divisas.general.model.Registrar;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Clase Notificacion
 * @author B813196
 * itoxtle
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Notificacion {

	@BsonId
	@BsonRepresentation(BsonType.OBJECT_ID)
	@NotNull(message = "Requerido", groups= {Actualizar.class})
	@Pattern(regexp="^[0-9a-f]{24}", message="Debe ser un valor hexadecimal de longitud 24", 
	 		 groups=Actualizar.class)
	private String id;
	
	@NotNull(message = "Requerido", groups= {Registrar.class, Actualizar.class})
	@Size(min = 1, max = 30, message="Longitud de mínimo {min}, máximo {max}", groups= {Registrar.class, Actualizar.class})
	private String nombre;
	
	@NotNull(message = "Requerido", groups= {Registrar.class, Actualizar.class})
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(message="Debe ser mayor o igual a la fecha actual", groups= {Registrar.class, Actualizar.class})
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") 
	private LocalDate fechaInicio;
	
	@NotNull(message = "Requerido", groups= {Registrar.class, Actualizar.class})
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(message="Debe ser mayor o igual a la fecha actual", groups= {Registrar.class, Actualizar.class})
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate fechaFin;
		
	@Min(value=1, message="Valor mínimo {value}", groups= {Registrar.class, Actualizar.class})
	@Max(value=15, message="Valor máximo {value}", groups= {Registrar.class, Actualizar.class})
	private int tiempoSegundos;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime fechaRegistro;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd  HH:mm:ss")
	private LocalDateTime fechaActualizacion;
	
	private boolean activo;
	private String estatus;
	
	private boolean generalSucursales;

	private List<@Pattern(regexp="[0-9]{4}", message="Las sucursales deben ser numéricas de longitud 4", 
			 groups= {Registrar.class, Actualizar.class}) String> sucursales = new ArrayList<>();
	
	private Binary imagen;
	
	@NotNull(message="Se requiere una imagen", groups= {Registrar.class})
	@BsonIgnore
	@JsonIgnore
	private MultipartFile fileImagen;
	

	/**
	 * Constructor de la clase
	 */
	public Notificacion() {
		super();
	}
	
	@AssertTrue(message="Se requiere una lista de sucursales", groups= {Registrar.class, Actualizar.class})
	@BsonIgnore
	@JsonIgnore
	public boolean isGeneral() {
		
		return generalSucursales || !sucursales.isEmpty();

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public int getTiempoSegundos() {
		return tiempoSegundos;
	}

	public void setTiempoSegundos(int tiempoSegundos) {
		this.tiempoSegundos = tiempoSegundos;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public LocalDateTime getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public boolean isGeneralSucursales() {
		return generalSucursales;
	}

	public void setGeneralSucursales(boolean generalSucursales) {
		this.generalSucursales = generalSucursales;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public List<String> getSucursales() {
		return Collections.unmodifiableList(sucursales);
	}

	public void setSucursales(List<String> sucursales) {
		this.sucursales = Collections.unmodifiableList(sucursales);
	}

	public Binary getImagen() {
		return imagen;
	}

	public void setImagen(Binary imagen) {
		this.imagen = imagen;
	}

	public MultipartFile getFileImagen() {
		return fileImagen;
	}

	public void setFileImagen(MultipartFile fileImagen) {
		this.fileImagen = fileImagen;
	}
	
	/**
	 * Asigna estatus de la notificacion
	 */
	@JsonIgnore
	public void setEstatusNotificacion() {
		
		LocalDate fechaHoy = LocalDate.now();
		
		if( (fechaInicio.isBefore(fechaHoy) || fechaInicio.isEqual(fechaHoy))  && 
			(fechaFin.isAfter(fechaHoy) || fechaFin.isEqual(fechaHoy)) ) {
			estatus = "Vigente";
		}
		else if(fechaFin.isBefore(fechaHoy)) {
			estatus = "No Vigente";
		}
		else{
			estatus = "Próxima";
		}
		
	}

}
