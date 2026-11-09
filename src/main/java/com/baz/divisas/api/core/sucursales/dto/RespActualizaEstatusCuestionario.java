package com.baz.divisas.api.core.sucursales.dto;

import com.mongodb.client.result.UpdateResult;

/**
 * Clase RespActualizaEstatusCuestionario
 * 
 * @author 1096526
 *
 */
public class RespActualizaEstatusCuestionario {
	private UpdateResult respConsulta;
	private boolean estatus;

	/**
	 * Constructor de la clase
	 */
	public RespActualizaEstatusCuestionario() {
		super();
	}

	/**
	 * @return the respConsulta
	 */
	public UpdateResult getRespConsulta() {
		return respConsulta;
	}

	/**
	 * @param respConsulta the respConsulta to set
	 */
	public void setRespConsulta(UpdateResult respConsulta) {
		this.respConsulta = respConsulta;
	}

	/**
	 * @return the estatus
	 */
	public boolean isEstatus() {
		return estatus;
	}

	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(boolean estatus) {
		this.estatus = estatus;
	}

}
