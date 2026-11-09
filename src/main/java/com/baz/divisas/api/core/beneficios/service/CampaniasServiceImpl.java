/**
 * 
 */
package com.baz.divisas.api.core.beneficios.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.core.beneficios.dao.CampaniasDao;
import com.baz.divisas.api.core.beneficios.dto.ReqAltaPremio;
import com.baz.divisas.api.core.beneficios.dto.ReqEliminaPremio;
import com.baz.divisas.api.core.beneficios.dto.ReqEstatusCampania;
import com.baz.divisas.api.core.beneficios.dto.ReqEstatusPremio;
import com.baz.divisas.api.core.beneficios.dto.ReqTipoCampania;
import com.baz.divisas.api.core.beneficios.model.BeanCampania;
import com.baz.divisas.api.core.beneficios.model.BeanPremio;
import com.baz.divisas.api.core.beneficios.util.CamposGenericos;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.DateUtils;

/**
 * The Class CampaniasServiceImpl.
 *
 * @author B86960
 */
@Service
public class CampaniasServiceImpl {

	/** The dao. */
	private CampaniasDao dao;

	/**
	 * Constructor.
	 *
	 * @param dao the dao
	 */
	public CampaniasServiceImpl(CampaniasDao dao) {
		super();
		this.dao = dao;
	}

	/**
	 * Crear.
	 *
	 * @param request the request
	 * @return the response api
	 */
	public ResponseApi crear(BeanCampania request) {
		List<String> detalles = new ArrayList<>();
		//Se valida si cumple con las reglas
		if(!validacion(request, detalles)) {
			//Se envia el detalle del por que no es exitosa la peticion
			return new ResponseApi(EstatusResponse.PETICION_INCORRECTA, detalles);
		}
		//Se asigna la fecha en que se registra la campania
		request.setFechaRegistro(LocalDateTime.now().toString());
		CamposGenericos.actividadesEconomicas(request);
		//CamposGenericos.perfilCliente(request)
		//Se asignan los ids de los premios en caso de contener
		if(request.getPremios() != null)
		{
			for (BeanPremio item : request.getPremios()) {
				item.setId(new ObjectId().toHexString());
			}	
		}
		//Se ejecuta la insercion
		dao.insert(request);
		return new ResponseApi(EstatusResponse.OK);
	}

	private static boolean validacion(BeanCampania request, List<String> detalles) {
		//Se obtiene la fecha actual
		String fecha = DateUtils.getDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		//Se asigna el formato de fecha
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		try {
			//Se parsean las fechas por el formato anterior
			Date fechaActual = formato.parse(fecha);
			Date fechaInicio = formato.parse(request.getFechaInicio());
			//Se valida si la fecha actual es menor a la fecha de inicio de campania
			if(!fechaActual.equals(fechaInicio) && fechaActual.after(fechaInicio)) {
				detalles.add("fechaInicio: Es menor a la fecha actual");
				return false;
			}
		}
		catch (ParseException ex){
			detalles.add("fechaInicio: continiene un valor no valido");
			return false;
		}
		
		return true;
	}

	/**
	 * Obtener.
	 *
	 * @return the response api
	 */
	public ResponseApi obtener() {
		//Se ejecuta la validacion de campanias
		validaVigenciaCampanias();
		return new ResponseApi(EstatusResponse.OK, dao.consultar());
	}

	private void validaVigenciaCampanias() {
		//Se ejecuta el proceso de actualizacion de campañas fuera de vigencia
		dao.actualizaCampaniasFueraVigencia();
	}

	/**
	 * Actualizar.
	 *
	 * @param request the request
	 * @return the response api
	 */
	public ResponseApi actualizar(BeanCampania request) {
		List<String> detalles = new ArrayList<>();
		if(!validacion(request, detalles)) {
			return new ResponseApi(EstatusResponse.PETICION_INCORRECTA, detalles);
		}
		dao.actualizar(request);
		return new ResponseApi(EstatusResponse.OK);
	}

	/**
	 * Eliminar.
	 *
	 * @param idCampania the id campania
	 * @return the response api
	 */
	public ResponseApi eliminar(String idCampania) {
		BeanCampania campania = dao.consultaUnica(idCampania);
		if(campania != null && validaEstatus(campania.getEstatus())) {
			dao.eliminarCampania(idCampania);
			return new ResponseApi(EstatusResponse.OK);
		}
		else {
			List<String> detalles = new ArrayList<>();
			detalles.add("la campania no existe o se encuentra en un estatus no valido para eliminarla");
			return new ResponseApi(EstatusResponse.SIN_RESULTADOS, detalles);
		}
	}

	private static boolean validaEstatus(String estatus) {
		return !(estatus.contains("Creada") || estatus.contains("Vigente"));
	}

	/**
	 * Actualizar tipo.
	 *
	 * @param tipoCampania the tipo campania
	 * @return the response api
	 */
	public ResponseApi actualizarTipo(ReqTipoCampania tipoCampania) {
		dao.actualizarTipo(tipoCampania);
		return new ResponseApi(EstatusResponse.OK);
	}

	/**
	 * Actualizar estatus.
	 *
	 * @param request the request
	 * @return the response api
	 */
	public ResponseApi actualizarEstatus(ReqEstatusCampania request) {
		dao.actualizarEstatus(request);
		return new ResponseApi(EstatusResponse.OK);
	}

	/**
	 * Crear premio.
	 *
	 * @param request the request
	 * @return the response api
	 */
	public ResponseApi crearPremio(ReqAltaPremio request) {
		if(request.getPremios() != null)
		{
			for (BeanPremio item : request.getPremios()) {
				item.setId(new ObjectId().toHexString());
				CamposGenericos.asignarActividadEconomica(item);
				dao.insertPremio(request.getIdCampania(), item);
			}	
		}
		//CamposGenericos.asignarPerfilCliente(request.getPremio())

		return new ResponseApi(EstatusResponse.OK);
	}

	/**
	 * Actualizar premio.
	 *
	 * @param request the request
	 * @return the response api
	 */
	public ResponseApi actualizarPremio(ReqAltaPremio request) {
		dao.actualizarPremio(request);
		return new ResponseApi(EstatusResponse.OK);
	}

	/**
	 * Eliminar premio.
	 *
	 * @param request the request
	 * @return the response api
	 */
	public ResponseApi eliminarPremio(ReqEliminaPremio request) {
		dao.eliminarPremio(request);
		return new ResponseApi(EstatusResponse.OK);
	}

	/**
	 * Actualiza estatus premio.
	 *
	 * @param request the request
	 * @return the response api
	 */
	public ResponseApi actualizarEstatusPremio(ReqEstatusPremio request) {
		dao.actualizarEstatusPremio(request);
		return new ResponseApi(EstatusResponse.OK);
	}

}
