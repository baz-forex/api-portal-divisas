package com.baz.divisas.api.bitacora.services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.bson.BsonInvalidOperationException;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.bitacora.dao.BitacoraDao;
import com.baz.divisas.api.bitacora.dto.BitacoraRequest;
import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.model.Bitacora;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.util.LogApp;
import com.baz.divisas.general.util.SecurityUtils;
import com.baz.divisas.security.services.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.mongodb.client.result.UpdateResult;

/**
 * Servicio de bitacora
 * @author B813196
 * itoxtle
 */
@Service
public class BitacoraServiceImpl {

	private SecurityUtils securityService;

	private BitacoraDao bitacoraDao;
	

	/**
	 * Constructor de la clase.
	 *
	 * @param bitacoraDao - Bitacora DAO
	 * @param securityService - SecurityUtils
	 */
	@Autowired
	public BitacoraServiceImpl(BitacoraDao bitacoraDao, SecurityUtils securityService) {
		super();
		this.bitacoraDao = bitacoraDao;
		this.securityService = securityService;
	}

	/**
	 * Metodo de registro de bitacora
	 * @param tipoAccion - Accion realizada
	 * @param detalleAccion - Detalle de la accion realizada
	 * @param datosAnteriores - Datos antes de realizar la accion
	 * @param datosActuales - Datos despues de realizar la accion
	 * 
	 * @return IdBitacora
	 */
	public String registrar(AccionBitacora tipoAccion, String detalleAccion,
			Object datosAnteriores, Object datosActuales) {

		return insertBitacora(tipoAccion, detalleAccion, datosAnteriores, datosActuales);

	}
	
	/**
	 * Metodo de registro de bitacora
	 * @param tipoAccion - Accion realizada
	 * @param detalleAccion - Detalle de la accion realizada
	 * @param datosAnteriores - Datos antes de realizar la accion
	 * @param datosActuales - Datos despues de realizar la accion
	 * @param updateResult - Resultado del update
	 */
	public void registrar(AccionBitacora tipoAccion, String detalleAccion) {
		
		insertBitacora(tipoAccion, detalleAccion, null, null);

	}
	
	/**
	 * Metodo de registro de bitacora
	 * @param tipoAccion - Accion realizada
	 * @param detalleAccion - Detalle de la accion realizada
	 * @param datosAnteriores - Datos antes de realizar la accion
	 * @param datosActuales - Datos despues de realizar la accion
	 * @param updateResult - Resultado del update
	 */
	public void registrar(AccionBitacora tipoAccion, String detalleAccion,
			Object datosAnteriores, Object datosActuales, UpdateResult updateResult) {

		if(updateResult.getModifiedCount() > 0) {
			insertBitacora(tipoAccion, detalleAccion, datosAnteriores, datosActuales);
		}

	}
	
	
	/**
	 * Metodo de registro de bitacora.
	 *
	 * @param tipoAccion - Accion realizada
	 * @param detalleAccion - Detalle de la accion realizada
	 * @param activo - Estatus
	 * @return Id Bitacora
	 */
	public String registrarCambioEstatus(AccionBitacora tipoAccion, String detalleAccion, boolean activo) {
		
		String estatus = "ACTIVO";
		
		if(!activo) {
			estatus = "INACTIVO";
		}

		
		return insertBitacora(tipoAccion, detalleAccion + estatus, null, null);

	}
	
	private String insertBitacora(AccionBitacora tipoAccion, String descripcionAccion,
			Object datosAnteriores, Object datosActuales) {
		Bitacora bitacora = new Bitacora();
		String idBitacora = LocalDateTime.now().toString();
		
		try {
			idBitacora = LocalDateTime.now().toString();
			
			bitacora.setId(idBitacora);

			// Set datos del usuario que realiza la accion
			UserDetailsImpl user = securityService.getUserSesion();			
			bitacora.setIdUsuario(user.getIdUserSesion());
			bitacora.setNombre(user.getNombre());
			bitacora.setApellidoPaterno(user.getApellidoPaterno());
			bitacora.setApellidoMaterno(user.getApellidoMaterno());
			bitacora.setIdModulo(user.getIdModulo());
			bitacora.setTipoAccion(tipoAccion.toString());
			bitacora.setDescripcionAccion(descripcionAccion);

			ObjectMapper mapper = new ObjectMapper()
								.registerModule(new ParameterNamesModule())
							    .registerModule(new Jdk8Module())
							    .registerModule(new JavaTimeModule());
			
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			
			if(datosAnteriores != null) {
				// Se evita Document.parse(json): reinterpreta sintaxis Extended JSON de
				// MongoDB ($where, $function, etc.) si el objeto de origen la contiene.
				// convertValue hace mapeo por reflexion de tipos Java, sin reinterpretar texto.
				@SuppressWarnings("unchecked")
				java.util.Map<String, Object> mapAnterior =
						mapper.convertValue(datosAnteriores, java.util.Map.class);
				bitacora.setDatosAnteriores(new Document(mapAnterior));
			}

			if(datosActuales != null) {
				@SuppressWarnings("unchecked")
				java.util.Map<String, Object> mapActual =
						mapper.convertValue(datosActuales, java.util.Map.class);
				bitacora.setDatosActuales(new Document(mapActual));
			}

			if(bitacora.getIdModulo() != null && !bitacora.getIdModulo().isEmpty()) {
				bitacoraDao.insertBitacora(bitacora);
			}
			else {
				LogApp.mensaje(bitacora.toString());
			}

		}
		catch(BsonInvalidOperationException | ClassCastException | IllegalArgumentException e) {
			LogApp.excepcion(e, "Ocurrio un error al registrar la bitacora");
		}
		return idBitacora;
	}
	

	/**
	 * Metodo de consulta bitacora
	 * 
	 * @param request - Parametros de busqueda
	 * @return - ResponseApi
	 */
	public ResponseApi getBitacora(BitacoraRequest request) {

		ResponseApi resultado;

		// Consulta bitacora
		List<Bitacora> resultadoBitacora = bitacoraDao.find(request);

		if (resultadoBitacora != null && !resultadoBitacora.isEmpty()) {
			
			for (Bitacora bitacora : resultadoBitacora) {
				
				if(bitacora.getDatosAnteriores() != null || bitacora.getDatosActuales() != null) {
					bitacora.setDetalle(true);
				}
				
				bitacora.setDatosAnteriores(null);
				bitacora.setDatosActuales(null);
			}
			
			resultado = new ResponseApi(EstatusResponse.OK, resultadoBitacora);					
		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No existe informacion."));
		}

		return resultado;
	}
	
	/**
	 * Metodo de consulta bitacora por id
	 * 
	 * @param idBitacora - Id Bitacora
	 * @return - ResponseApi
	 */
	public ResponseApi getBitacoraPorId(String idBitacora) {

		ResponseApi resultado;

		// Consulta bitacora
		Bitacora bitacora = bitacoraDao.findById(idBitacora);

		if (bitacora != null) {
			resultado = new ResponseApi(EstatusResponse.OK, bitacora);					
		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No existe informacion."));
		}

		return resultado;
	}

}
