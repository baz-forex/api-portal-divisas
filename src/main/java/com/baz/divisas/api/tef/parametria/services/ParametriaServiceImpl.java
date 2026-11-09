/**
 * 
 */
package com.baz.divisas.api.tef.parametria.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baz.divisas.api.digital.configuraciones.model.ParametriaChatbot;
import com.baz.divisas.api.tef.parametria.dao.ParametriaDao;
import com.baz.divisas.api.tef.parametria.dto.ReqActualizaCampoParametria;
import com.baz.divisas.api.tef.parametria.dto.ReqAltaParametria;
import com.baz.divisas.api.tef.parametria.dto.ReqConsultaParametria;
import com.baz.divisas.api.tef.parametria.dto.ReqEliminarCampoParametria;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.model.Constant;
import com.google.gson.Gson;

/**
 * Clase de implementacion para parametria
 * @author B86960
 *
 */
@Service
public class ParametriaServiceImpl {

	private ParametriaDao dao;
	/**
	 * Constructor
	 * @param dao dao
	 */
	@Autowired
	public ParametriaServiceImpl(ParametriaDao dao) {
		this.dao = dao;
	}
	
	/**
	 * Metodo para consultar la parametria solicitada.
	 *
	 * @param request the request
	 * @return response
	 */
	public ResponseApi consultar(ReqConsultaParametria request) {
		//Se añade lista de detalles
		List<String> detalles = new ArrayList<>();
		//Se ejecuta la validacion de campo
		if(!validacion(request.getParametria(), detalles)) {
			return new ResponseApi(EstatusResponse.PETICION_INCORRECTA, detalles);
		}
		//Se ejecuta la consulta de la parametria
		Document doc = dao.consultar(request.getParametria());
		//Se valida que no este vacio el documento
		if(doc.isEmpty())
		{
			return new ResponseApi(EstatusResponse.SIN_RESULTADOS);
		}
		return new ResponseApi(EstatusResponse.OK, doc);
	}
	
	/**
	 * Consultar activo.
	 *
	 * @param request the request
	 * @return the response api
	 */
	public ResponseApi consultarActivo(ReqConsultaParametria request) {
		//Se añade lista de detalles
		List<String> detalles = new ArrayList<>();
		//Se ejecuta la validacion de campo
		if(!validacion(request.getParametria(), detalles)) {
			return new ResponseApi(EstatusResponse.PETICION_INCORRECTA, detalles);
		}
		//Se ejecuta la consulta de campo activo de la parametria
		return new ResponseApi(EstatusResponse.OK, dao.obtenerActivo(request.getParametria()));
	}
	
	/**
	 * Activar.
	 *
	 * @param request the request
	 * @param valor the valor
	 * @return the response api
	 */
	public ResponseApi actualizarCampoActivo(ReqConsultaParametria request, boolean valor) {
		//Se añade lista de detalles
		List<String> detalles = new ArrayList<>();
		//Se ejecuta la validacion de campo
		if(!validacion(request.getParametria(), detalles)) {
			return new ResponseApi(EstatusResponse.PETICION_INCORRECTA, detalles);
		}
		//Se actualiza el campo activo segun parametro valor
		if(dao.actualizarCampoActivo(request.getParametria(), valor)){
			return new ResponseApi(EstatusResponse.OK, true);
		}
		else {
			//Se agrega el detalle en caso de no actualizar ningun dato
			detalles.add("No existe la parametria o el valor ya se encuentra " + valor);
			return new ResponseApi(EstatusResponse.SIN_RESULTADOS, detalles);
		}
	}
	
	/**
	 * Actualizar campo.
	 *
	 * @param request the request
	 * @return the response api
	 */
	public ResponseApi actualizarCampo(ReqActualizaCampoParametria request) {
		//Se añade lista de detalles
		List<String> detalles = new ArrayList<>();
		//Se ejecuta la validacion de campo
		if(!validacion(request.getParametria(), detalles)) {
			return new ResponseApi(EstatusResponse.PETICION_INCORRECTA, detalles);
		}
		//Se actualiza el campo activo segun parametro valor
		if(dao.actualizarCampo(request.getParametria(), request.getCampo(), request.getValor())){
			return new ResponseApi(EstatusResponse.OK, true);
		}
		else {
			//Se agrega el detalle en caso de no actualizar ningun dato
			detalles.add("No existe la parametria o el valor ya se encuentra actualizado");
			return new ResponseApi(EstatusResponse.SIN_RESULTADOS, detalles);
		}
	}
	
	/**
	 * Eliminar campo.
	 *
	 * @param request the request
	 * @return the response api
	 */
	public ResponseApi eliminarCampo(ReqEliminarCampoParametria request) {
		//Se añade lista de detalles
		List<String> detalles = new ArrayList<>();
		//Se ejecuta la validacion de campo
		if(!validacion(request.getParametria(), detalles)) {
			return new ResponseApi(EstatusResponse.PETICION_INCORRECTA, detalles);
		}
		//Se actualiza el campo activo segun parametro valor
		if(dao.eliminarCampo(request.getParametria(), request.getCampo())){
			return new ResponseApi(EstatusResponse.OK, true);
		}
		else {
			//Se agrega el detalle en caso de no actualizar ningun dato
			detalles.add("No existe la parametria o el valor ya se encuentra actualizado");
			return new ResponseApi(EstatusResponse.SIN_RESULTADOS, detalles);
		}
	}

	/**
	 * Eliminar.
	 *
	 * @param request the request
	 * @return the response api
	 */
	public ResponseApi eliminar(ReqConsultaParametria request) {
		//Se añade lista de detalles
		List<String> detalles = new ArrayList<>();
		//Se ejecuta la validacion de campo
		if(!validacion(request.getParametria(), detalles)) {
			return new ResponseApi(EstatusResponse.PETICION_INCORRECTA, detalles);
		}
		//Se actualiza el campo activo segun parametro valor
		if(dao.eliminar(request.getParametria())){
			return new ResponseApi(EstatusResponse.OK, true);
		}
		else {
			//Se agrega el detalle en caso de no actualizar ningun dato
			detalles.add("No existe la parametria");
			return new ResponseApi(EstatusResponse.SIN_RESULTADOS, detalles);
		}
	}
	
	/**
	 * Alta.
	 *
	 * @param request the request
	 * @return the response api
	 */
	public ResponseApi alta(ReqAltaParametria request) {
		//Se añade lista de detalles
		List<String> detalles = new ArrayList<>();
		//Se ejecuta la validacion de campo
		if(!validacion(request.getParametria(), detalles)) {
			return new ResponseApi(EstatusResponse.PETICION_INCORRECTA, detalles);
		}
		Document document = new Document("_id", request.getParametria());
		request.getCampos().forEach((k, v) -> document.append(k.toString(), v));
		
		//Se actualiza el campo activo segun parametro valor
		if(dao.alta(document)){
			return new ResponseApi(EstatusResponse.OK, true);
		}
		else {
			//Se agrega el detalle en caso de no actualizar ningun dato
			detalles.add("No fue posible insertar la parametria, puede que ya exista, validar en la consulta");
			return new ResponseApi(EstatusResponse.SIN_RESULTADOS, detalles);
		}
	}

	/**
	 * Consultar.
	 *
	 * @return the response api
	 */
	public ResponseApi consultar() {
		//Se añade lista de detalles
		List<String> detalles = new ArrayList<>();
		List<String> parametrias = dao.consultar();
		if(parametrias.isEmpty()) {
			detalles.add("No existen parametrias");
			return new ResponseApi(EstatusResponse.SIN_RESULTADOS, detalles);
		}
		else{
			return new ResponseApi(EstatusResponse.OK, parametrias);
		}
	}
	
	private static boolean validacion(String parametria, List<String> detalles) {
		boolean flag = true;
		//Se valida que parametria no este nulo o vacio
		if(parametria == null || parametria.isEmpty()) {
			//Se agrega el detalle
			detalles.add("El campo de parametria no puede ser nulo o estar vacio");
			flag = false;
		}
		return flag;
	}

	public ResponseApi getParametriaChatBot(String idParametria,String divisa) {
		ResponseApi resultado;
		
		Document docParametria = dao.findParametria(idParametria, divisa);
		
		if (docParametria != null) {			
			Gson gson = new Gson();
			String docJson = gson.toJson(docParametria);
			ParametriaChatbot parametria = gson.fromJson(docJson, ParametriaChatbot.class);
			
			resultado = new ResponseApi(EstatusResponse.OK, parametria);					
		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
	    								Arrays.asList(Constant.SIN_INFORMACION));
		}
		
		return resultado;
	}

	public ResponseApi updateParametriaOperacion(String idParametria,String divisa, 
			ParametriaChatbot parametria) {
		ResponseApi resultado;

		Document docParametriaAnterior = dao.updateParametria(idParametria, divisa, parametria);

		if (docParametriaAnterior != null) {

			resultado = new ResponseApi(EstatusResponse.OK);

		}
		else {
			resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("No fue posible actualzar la parametria de chatbot."));
		}

		return resultado;
	}
	
}
