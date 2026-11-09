package com.baz.divisas.api.core.beneficios.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import com.baz.divisas.general.model.Actualizar;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.model.Registrar;
import com.baz.divisas.general.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

/**
 * Seguro
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Seguro {

	@BsonId
	@BsonRepresentation(BsonType.OBJECT_ID)
	@NotNull(message = "Requerido", groups= {Actualizar.class})
	@Pattern(regexp="^[0-9a-f]{24}", message="Debe ser un valor hexadecimal de longitud 24", 
	 		 groups=Actualizar.class)
	@ApiModelProperty(value="Id Seguro", example="64d4268d1b41f232430b83e9", position=1)
	private String id;	
	
	@BsonProperty("fecha_registro")
	@ApiModelProperty(hidden=true)
	private String fechaRegistro;
	
	@NotEmpty(message = "Requerido", groups= {Registrar.class, Actualizar.class})
	@ApiModelProperty(value="Nombre", example="Seguro Contra Robo", position=2, required=true)
	private String nombre;
	
	@NotNull(message = "Requerido", groups= {Registrar.class, Actualizar.class})
	@Pattern(regexp="^(Vigente|Inactivo|Eliminada)$", message="Estatus invalido",
			 groups= {Registrar.class, Actualizar.class})
	@ApiModelProperty(value="Estatus", example="Vigente", position=3, required=true)
	private String estatus;
	
	@NotNull(message = "Requerido", groups= {Registrar.class, Actualizar.class})
	@Size(min=1, message="Se requiere mínimo un seguro", groups={Registrar.class, Actualizar.class})
	@Valid
	@ApiModelProperty(value="Premios Seguros", position=4, required=true)
	private List<PremioSeguro> premiosSeguros;
		
	/**
	 * Constructor de la clase
	 */
	public Seguro() {
		super();
		this.fechaRegistro = DateUtils.getDateFormat(Constant.FORMATOFECHACOMPLETAEXT);
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
	 * @return the premiosSeguros
	 */
	public List<PremioSeguro> getPremiosSeguros() {
		return premiosSeguros;
	}

	/**
	 * @param premiosSeguros the premiosSeguros to set
	 */
	public void setPremiosSeguros(List<PremioSeguro> premiosSeguros) {
		this.premiosSeguros = premiosSeguros;
	}

}
