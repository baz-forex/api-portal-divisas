package com.baz.divisas.api.tef.regiones.services;

import com.baz.divisas.general.dto.ResponseApi;

import org.springframework.stereotype.Service;

/**
 * IRegionesService.
 *
 * @author 164945
 * ogatica
 */
@Service
public interface IRegionesService {

	/**
	 * Consulta regiones.
	 *
	 * @return ResponseApi
	 */
	ResponseApi consultaRegiones();
	
	/**
	 * Crea una region.
	 *
	 * @param descripcion - Descripcion de la region
	 * @return ResponseApi
	 */
	ResponseApi creaRegion(String descripcion);
	
	/**
	 * Actualiza descripcion de la region.
	 *
	 * @param id - Id
	 * @param descripcion - Descripcion
	 * @return ResponseApi
	 */
	ResponseApi actualizaDescripcionRegion(String id, String descripcion);
	
	/**
	 * Actualiza estatus de la region.
	 *
	 * @param id - Id
	 * @return ResponseApi
	 */
	ResponseApi actualizaEstatusRegion(String id);
	
	/**
	 * Elimina una region.
	 *
	 * @param id - Id
	 * @return ResponseApi
	 */
	ResponseApi eliminaRegion(String id);
	
	/**
	 * Actualiza estatus de la Divisa en la Region.
	 *
	 * @param idRegion - Id de la Region
	 * @param idDivisa - Id de la Divisa
	 * @return ResponseApi
	 */
	ResponseApi actualizaEstatusDivisaRegion(String idRegion, String idDivisa);

	/**
	 * Consulta regiones OLP.
	 *
	 * @return the response api
	 */
	ResponseApi consultaRegionesOlp();

	/**
	 * Crea region olp.
	 *
	 * @param descripcion the descripcion
	 * @return the response api
	 */
	ResponseApi creaRegionOlp(String descripcion);

	/**
	 * Actualiza descripcion region olp.
	 *
	 * @param id the id
	 * @param descripcion the descripcion
	 * @return the response api
	 */
	ResponseApi actualizaDescripcionRegionOlp(String id, String descripcion);

	/**
	 * Actualiza estatus region olp.
	 *
	 * @param id the id
	 * @return the response api
	 */
	ResponseApi actualizaEstatusRegionOlp(String id);

	/**
	 * Elimina region olp.
	 *
	 * @param id the id
	 * @return the response api
	 */
	ResponseApi eliminaRegionOlp(String id);

	/**
	 * Actualiza estatus divisa region olp.
	 *
	 * @param idRegion the id region
	 * @param idDivisa the id divisa
	 * @return the response api
	 */
	ResponseApi actualizaEstatusDivisaRegionOlp(String idRegion, String idDivisa);
	

	
}
