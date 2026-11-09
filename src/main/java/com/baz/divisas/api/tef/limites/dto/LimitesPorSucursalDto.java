package com.baz.divisas.api.tef.limites.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.bson.codecs.pojo.annotations.BsonProperty;

import com.baz.divisas.api.tef.general.LimitesOnUpdateStatus;
import com.baz.divisas.api.tef.general.LimitesOnUpsert;
import com.baz.divisas.api.tef.general.model.Limites;

/**
 * Clase ReqAltaLimitesPorSucursal
 * 
 * @author 164945 ogatica
 */
public class LimitesPorSucursalDto extends Limites {

	@NotBlank(message = "Requerido no vacios", groups= {LimitesOnUpdateStatus.class,LimitesOnUpsert.class})
	@NotNull(message = "Requerido", groups= {LimitesOnUpdateStatus.class, LimitesOnUpsert.class})
	@BsonProperty("_id")
	private String idSucursal;
	private String descripcion;
	
	/**
	 * @return the idSucursal
	 */
	public String getIdSucursal() {
		return idSucursal;
	}

	/**
	 * @param idSucursal the idSucursal to set
	 */
	public void setIdSucursal(String idSucursal) {
		this.idSucursal = idSucursal;
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

}
