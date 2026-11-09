package com.baz.divisas.api.tef.regionessucursales.services;

import java.util.List;

import com.baz.divisas.general.dto.ResponseApi;

/**
 * The Interface IRegionSucursalService.
 *
 * @author 1077023
 */
public interface IRegionSucursalService {
	
	/**
	 * Consulta las sucursales en las regiones.
	 *
	 * @return ResponseApi
	 */
	ResponseApi consultaSucursalesRegiones();
	
	/**
	 * Asigna sucursales a una region.
	 *
	 * @param idRegion - Id de la region
	 * @param idsSucursales - Ids de las sucursales
	 * @return ResponseApi
	 */
	ResponseApi asignarSucursalesRegion(String idRegion, List<String> idsSucursales);
	
	/**
	 * Elimina una sucursal de la region.
	 *
	 * @param idRegion - Id de la region
	 * @param idSucursal - Id de la sucursal
	 * @return ResponseApi
	 */
	ResponseApi eliminarSucursalRegion(String idRegion, String idSucursal);
    
    /**
     * Consulta regiones segmentos sucursales.
     *
     * @return the response api
     */
    ResponseApi consultaRegionesSegmentosSucursales();

	/**
	 * Consulta TC regiones sucursales.
	 *
	 * @param idRegion the id region
	 * @param idDivisa the id divisa
	 * @return the response api
	 */
	ResponseApi consultaTCRegionesSucursales(String idRegion, String idDivisa);

	/**
	 * Consulta regiones segmentos sucursales OLP.
	 *
	 * @return the response api
	 */
	ResponseApi consultaRegionesSegmentosSucursalesOlp();

	/**
	 * Consulta TC regiones sucursales OLP.
	 *
	 * @param idRegion the id region
	 * @return the response api
	 */
	ResponseApi consultaTCRegionesSucursalesOlp(String idRegion);

	/**
	 * Consulta sucursales regiones olp.
	 *
	 * @return the response api
	 */
	ResponseApi consultaSucursalesRegionesOlp();

	/**
	 * Asignar sucursales region olp.
	 *
	 * @param idRegion the id region
	 * @param idSucursal the id sucursal
	 * @return the response api
	 */
	ResponseApi asignarSucursalesRegionOlp(String idRegion, List<String> idSucursal);

	/**
	 * Eliminar sucursal region olp.
	 *
	 * @param idRegion the id region
	 * @param idSucursal the id sucursal
	 * @return the response api
	 */
	ResponseApi eliminarSucursalRegionOlp(String idRegion, String idSucursal);

}
