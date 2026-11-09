package com.baz.divisas.api.tef.limites.services;

import java.io.ByteArrayInputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baz.divisas.api.tef.limites.dto.LimitesPorSucursalDto;
import com.baz.divisas.general.dto.ResponseApi;

/**
 * Clase ILimitesPorSucursalService.
 *
 * @author 164945
 * ogatica
 */
@Service
public interface ILimitesPorSucursalService {

	/**
	 * Consulta limites sucursal.
	 *
	 * @return the response api
	 */
	public ResponseApi consultaLimitesSucursal();
	
	/**
	 * Crea limite sucursal.
	 *
	 * @param reqCreaLimitesRegiones the req crea limites regiones
	 * @return the response api
	 */
	public ResponseApi creaLimiteSucursal(LimitesPorSucursalDto reqCreaLimitesRegiones);
	
	/**
	 * Actualiza limite sucursal.
	 *
	 * @param reqActualizaLimitesPorSucursal the req actualiza limites por sucursal
	 * @return the response api
	 */
	public ResponseApi actualizaLimiteSucursal(LimitesPorSucursalDto reqActualizaLimitesPorSucursal);
	
	/**
	 * Actualiza estatus limite sucursal.
	 *
	 * @param reqActualizaLimitesPorSucursal the req actualiza limites por sucursal
	 * @return the response api
	 */
	public ResponseApi actualizaEstatusLimiteSucursal(LimitesPorSucursalDto reqActualizaLimitesPorSucursal);
	
	/**
	 * Consulta limites sucursal olp.
	 *
	 * @return the response api
	 */
	public ResponseApi consultaLimitesSucursalOlp();

	/**
	 * Carga archivo limites.
	 *
	 * @param file the file
	 * @return the response api
	 */
	public ResponseApi cargaArchivoLimites(MultipartFile file);

	/**
	 * Descarga limites sucursales.
	 *
	 * @return the byte array input stream
	 */
	public ByteArrayInputStream descargaLimitesSucursales(String divisa);	
	
}
