package com.baz.divisas.api.tef.sucursales.service;

import java.io.ByteArrayInputStream;
import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baz.divisas.api.tef.sucursales.dto.ReqActSucursal;
import com.baz.divisas.general.dto.ResponseApi;

/**
 * Sucursales Interface.
 */
@Service
public interface ISucursales {

	/**
	 * Obtiene las sucursales Asignadas/Desasignadas.
	 *
	 * @param asignadas - true: Asignadas, false: Desasignadas
	 * @return ResponseApi
	 */
	ResponseApi getSucursalesImpl(boolean asignadas);

	/**
	 * Consulta TC sucursales.
	 *
	 * @return the response api
	 */
	ResponseApi consultaTCSucursales();

	/**
	 * Actualiza TC sucursal.
	 *
	 * @param request the request
	 * @return the response api
	 */
	ResponseApi actualizaTCSucursal(@Valid ReqActSucursal request);

	/**
	 * Carga archivo TC.
	 *
	 * @param file the file
	 * @return the response api
	 */
	ResponseApi cargaArchivoTC(MultipartFile file);

	/**
	 * Descarga TC sucursales.
	 *
	 * @return the byte array input stream
	 */
	ByteArrayInputStream descargaTCSucursales();

	/**
	 * Consulta TC sucursales OLP.
	 *
	 * @return the response api
	 */
	ResponseApi consultaTCSucursalesOlp();

	/**
	 * Actualiza TC sucursal OLP.
	 *
	 * @param request the request
	 * @return the response api
	 */
	ResponseApi actualizaTCSucursalOlp(@Valid ReqActSucursal request);

	/**
	 * Carga archivo TCOLP.
	 *
	 * @param file the file
	 * @return the response api
	 */
	ResponseApi cargaArchivoTCOlp(MultipartFile file);

	/**
	 * Descarga TC sucursales OLP.
	 *
	 * @return the byte array input stream
	 */
	ByteArrayInputStream descargaTCSucursalesOlp();

}
