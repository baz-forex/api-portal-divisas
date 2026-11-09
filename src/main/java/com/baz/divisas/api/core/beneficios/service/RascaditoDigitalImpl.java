package com.baz.divisas.api.core.beneficios.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bson.BsonObjectId;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.core.beneficios.dao.RascadigoDigitalDao;
import com.baz.divisas.api.core.beneficios.dto.ResponseRascadito;
import com.baz.divisas.api.core.beneficios.model.Imagenes;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.LogApp;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

@Service
public class RascaditoDigitalImpl{

	@Autowired
	RascadigoDigitalDao rascadigoDao;

	public ResponseApi activaDesactivaBanner(String id) {

		ResponseApi resultado;

		ResponseRascadito buscaParametria = rascadigoDao.findLandingPage(id);

		if (buscaParametria != null) {

			UpdateResult resultUpdate = rascadigoDao.estatusParameria(id, !buscaParametria.isActivo());

			if (resultUpdate.getMatchedCount() > 0) {

				resultado = new ResponseApi(EstatusResponse.OK);

			} 
			else {
				resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
						Arrays.asList("No fue posible cambiar el estatus de la parametria"));
			}

		} 
		else {
			LogApp.mensaje("No existe la parametria del id: " + id);
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("No existe la parametria con ese id"));
		}

		return resultado;
	}

	public ResponseApi guardarConfiguracionLandingPage(ResponseRascadito requestRascadito) throws IOException {

		ResponseApi resultado;
		
		String obtenerId = "";
		List<ResponseRascadito> buscaParameria = rascadigoDao.consultaParametriaRascadito();
		
		for (ResponseRascadito responseRascadito : buscaParameria) {
			obtenerId =  responseRascadito.getId();
		}
		
		if(null != buscaParameria && !buscaParameria.isEmpty()) {
			return new ResponseApi(EstatusResponse.OK, " Ya existe una parametria, ID: " + obtenerId);
		}

		for (Imagenes imagenElement : requestRascadito.getImagenes()) {

			imagenElement.setValor(new Binary(imagenElement.getImagenId().getBytes()));

		}

		InsertOneResult registraConfiguracionLandingPage = rascadigoDao.registraLandingPage(requestRascadito);

		if (registraConfiguracionLandingPage.getInsertedId() != null) {
			
			BsonObjectId id = (BsonObjectId) registraConfiguracionLandingPage.getInsertedId();
			
			String idParametria =  id.getValue().toHexString();
			
			resultado = new ResponseApi(EstatusResponse.OK,"id parametria: " + idParametria);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("No fue posible registrar la configuracion landing page"));
		}

		return resultado;
	}
	
	public ResponseApi actualizarParametria(ResponseRascadito requestRascadito) throws IOException {

		ResponseApi resultado;

		ResponseRascadito buscaParametria = rascadigoDao.findLandingPage(requestRascadito.getId());

		if (buscaParametria == null) {

			return new ResponseApi(EstatusResponse.SIN_RESULTADOS);
		}

		requestRascadito.setActivo(buscaParametria.isActivo());

		for (Imagenes imagenElement : requestRascadito.getImagenes()) {

			imagenElement.setValor(new Binary(imagenElement.getImagenId().getBytes()));

		}

		UpdateResult registraConfiguracionLandingPage = rascadigoDao.actualizaLandingPage(requestRascadito);

		if (registraConfiguracionLandingPage.getModifiedCount() > 0) {
			resultado = new ResponseApi(EstatusResponse.OK);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("No fue posible registrar la configuracion landing page"));
		}

		return resultado;

	}

	public ResponseApi consultarParametria() {

		ResponseApi resultado;

		List<ResponseRascadito> buscaParameria = rascadigoDao.consultaParametriaRascadito();

		if (!buscaParameria.isEmpty()) {

			resultado = new ResponseApi(EstatusResponse.OK, buscaParameria);

		} else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
		}

		return resultado;

	}

	public ResponseApi estatusParametria() {
		List<ResponseRascadito> buscaParameria = rascadigoDao.consultaParametriaRascadito();
		System.out.println("buscar parametria vacia? :" + buscaParameria.isEmpty());
		if (!buscaParameria.isEmpty()) {
			return new ResponseApi(EstatusResponse.OK, buscaParameria.get(0).isActivo());
		}
		else
		{
			return new ResponseApi(EstatusResponse.OK, false);
		}
	}
}
