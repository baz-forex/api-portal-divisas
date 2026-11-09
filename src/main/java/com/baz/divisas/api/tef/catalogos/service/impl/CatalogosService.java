package com.baz.divisas.api.tef.catalogos.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.catalogos.model.Catalogo;
import com.baz.divisas.api.tef.catalogos.dao.CatalogosDao;
import com.baz.divisas.api.tef.catalogos.dto.RespTiposOperacion;
import com.baz.divisas.api.tef.catalogos.service.ICatalogos;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

@Service("CatalogosTEFService")
public class CatalogosService  implements ICatalogos{
	
	@Autowired
	private CatalogosDao catalogosDao;
	
	@Override
	public ResponseApi getTiposOperacion() {
		
		ResponseApi resultado;
		
		List<Catalogo> tiposOperacion = catalogosDao.findTiposOperacion();
		
		if(tiposOperacion != null && !tiposOperacion.isEmpty()) {
			
			RespTiposOperacion responseoperaciones = new RespTiposOperacion(tiposOperacion);
			resultado = new ResponseApi(EstatusResponse.OK,responseoperaciones);
			
		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No existen tipos de operacion."));
		}
		
		return resultado;
	
	}

	@Override
	public ResponseApi actualizaEstatusTipoOperacion(@Valid Catalogo tipoOperacion) {
		
		ResponseApi resultado; 
		
		boolean existeTipoOperacion = catalogosDao.existeTipoOperacion(tipoOperacion.getId());
		
		if(existeTipoOperacion) {
			
			if(catalogosDao.updateEstatusTipoOperacion(tipoOperacion.getId(), tipoOperacion.isActivo())) {
				resultado =  new ResponseApi(EstatusResponse.OK);
			}
			else {
				resultado = new ResponseApi(EstatusResponse.NO_CONTENT, 
						Arrays.asList("El tipo de operacion no existe."));
			}
			
		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, 
					Arrays.asList("El tipo de operacion no existe."));
		} 
		
		return resultado;
	}

}
