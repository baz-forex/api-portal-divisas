package com.baz.divisas.api.core.beneficios.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.core.beneficios.dao.SegurosDao;
import com.baz.divisas.api.core.beneficios.dto.ReqEstatusSeguro;
import com.baz.divisas.api.core.beneficios.model.Seguro;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.model.Constant;
import com.mongodb.client.result.InsertOneResult;

/**
 * SegurosServiceImpl
 */
@Service
public class SegurosServiceImpl {

	private SegurosDao segurosDao;
	
	/**
	 * Constructor de la clase.
	 *
	 * @param segurosDao - Seguros Dao
	 */
	@Autowired
	public SegurosServiceImpl(SegurosDao segurosDao) {
		super();
		this.segurosDao = segurosDao;
	}
	
	/**
	 * Consulta seguros.
	 *
	 * @return ResponseApi
	 */
	public ResponseApi consultaSeguros() {
			
		ResponseApi resultado;
		
		List<Seguro> seguros = segurosDao.find();

		if (!seguros.isEmpty()) {
			resultado = new ResponseApi(EstatusResponse.OK, seguros);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
		}

		return resultado;
	}
	
	/**
	 * Consulta seguros por estatus.
	 *
	 * @param estatus - Estatus
	 * @return ResponseApi
	 */
	public ResponseApi consultaSegurosPorEstatus(String estatus) {
		
		ResponseApi resultado;
		
		List<Seguro> seguros = segurosDao.findByEstatus(estatus);

		if (!seguros.isEmpty()) {
			resultado = new ResponseApi(EstatusResponse.OK, seguros);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
		}

		return resultado;
	}
	
	/**
	 * Consulta seguros por estatus.
	 *
	 * @param id - Id Seguro
	 * @return ResponseApi
	 */
	public ResponseApi consultaSegurosPorId(String id) {
		
		ResponseApi resultado;
		
		Seguro seguro = segurosDao.findById(id);

		if (seguro != null) {
			resultado = new ResponseApi(EstatusResponse.OK, seguro);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
		}

		return resultado;
	}
	
	
	/**
	 * Crear.
	 *
	 * @param seguro - Seguro
	 * @return ResponseApi
	 */
	public ResponseApi crear(Seguro seguro) {
				
		InsertOneResult result =  segurosDao.insert(seguro);
		
		if(result.getInsertedId() != null){
			return new ResponseApi(EstatusResponse.OK);
		}
		else {
			return new ResponseApi(EstatusResponse.ERROR_INTERNO);
		}

	}
	
	/**
	 * Actualizar.
	 *
	 * @param seguro - Seguro
	 * @return ResponseApi
	 */
	public ResponseApi actualizar(Seguro seguro) {
		
		Seguro seguroAnt = segurosDao.update(seguro);
		
		if(seguroAnt != null) {
			return new ResponseApi(EstatusResponse.OK);
		}
		else {
			return new ResponseApi(EstatusResponse.ERROR_INTERNO);
		}

	}
	
	/**
	 * Actualizar estatus.
	 *
	 * @param request - ReqEstatusSeguro
	 * @return ResponseApi
	 */
	public ResponseApi actualizarEstatus(ReqEstatusSeguro request) {
		
		Seguro seguroAnt = segurosDao.updateEstatus(request.getId(), request.getEstatus());
		
		if(seguroAnt != null) {
			return new ResponseApi(EstatusResponse.OK);
		}
		else {
			return new ResponseApi(EstatusResponse.ERROR_INTERNO);
		}
	}
	
}
