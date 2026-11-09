package com.baz.divisas.api.core.configuraciones.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.core.configuraciones.dao.PerfilDeClientesDao;
import com.baz.divisas.api.core.configuraciones.dto.BeanLimitesRentabilidad;
import com.baz.divisas.api.core.configuraciones.dto.BeanSegmentoLealtad;
import com.baz.divisas.api.core.configuraciones.dto.BeanSegmentoRentabilidad;
import com.baz.divisas.api.core.configuraciones.dto.BeanValoresRentabilidad;
import com.baz.divisas.api.core.configuraciones.dto.RespConsultaCatalogoRentLealVal;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.model.Constant;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Clase PaisesRestringidos
 * 
 * @author 1027346 Martinez Yonathan
 *
 */

@Service
public class PerfilDeClientesServiceImpl {

	private static final String DESCRIP = "descripcion";
	private static final String ACTIVO = "activo";

	private PerfilDeClientesDao perfilDeClientesDao;
	private BitacoraServiceImpl bitacora;

	/**
	 * Constructor de la clase
	 * 
	 * @param perfilDeClientesDao - PerfilDeClientesDao
	 * @param bitacora - Bitacora Service
	 */
	@Autowired
	public PerfilDeClientesServiceImpl(PerfilDeClientesDao perfilDeClientesDao, BitacoraServiceImpl bitacora) {
		super();
		this.perfilDeClientesDao = perfilDeClientesDao;
		this.bitacora = bitacora;
	}

	/**
	 * Metodo de consulta de campos restringidos
	 * 
	 * @param tipoCatalogo - Identificador del catalogo
	 * @return Lista de campos restringidos
	 */
	public ResponseApi getPerfilesDelCliente(String tipoCatalogo) {

		ResponseApi resultado;

		// Consulta campos restringidos
		RespConsultaCatalogoRentLealVal catalogo = perfilDeClientesDao.consultaCatalogosRentLealValores(tipoCatalogo);

		if (catalogo != null) {
			resultado = new ResponseApi(EstatusResponse.OK, catalogo);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
		}

		return resultado;
	}

	/**
	 * Metodo de consulta de campos restringidos
	 * 
	 * @param tipoCatalogo - Identificador del catalogo
	 * @return Lista de campos restringidos
	 */
	public ResponseApi getValoresRentabilida() {

		ResponseApi resultado;

		// Consulta campos restringidos
		List<BeanValoresRentabilidad> catalogo = perfilDeClientesDao.valoresRentabilidad();

		if (catalogo != null) {
			resultado = new ResponseApi(EstatusResponse.OK, catalogo);
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
		}

		return resultado;
	}

	/**
	 * Metodo de edicion de catalogos segmentacion lealtad
	 * 
	 * @param segmentoLealtad - Identificador del segmento
	 * @return respuesta del update
	 */
	public ResponseApi editarCatSegmentoLealtad(BeanSegmentoLealtad segmentoLealtad) {

		List<BeanSegmentoLealtad> catalogo = perfilDeClientesDao.segmentoLealtad();
		catalogo.stream().filter(cat -> cat.getId().equals(segmentoLealtad.getId()));
		// Se crea el documento a Editar
		Document segmentoLealtadDocument = new Document();
		segmentoLealtadDocument.append("limite_meses", segmentoLealtad.getLimiteMeses())
				.append("transaccion_minimo", segmentoLealtad.getTransaccionMinimo())
				.append("transaccion_maximo", segmentoLealtad.getTransaccionMaximo())
				.append(ACTIVO, segmentoLealtad.getActivo());

		List<Document> listarentabilidad = new ArrayList<>();
		for (BeanLimitesRentabilidad limiteRent : segmentoLealtad.getLimitesRentabilidad()) {
			Document limRentabilidad = new Document();
			limRentabilidad.append("_id", limiteRent.getId()).append(DESCRIP, limiteRent.getDescripcion())
					.append("maximo_premios", limiteRent.getMaximoPremios()).append("estatus", limiteRent.getEstatus());

			listarentabilidad.add(limRentabilidad);
		}

		if (listarentabilidad.isEmpty()) {
			return new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
		}

		segmentoLealtadDocument.append("limites_rentabilidad", listarentabilidad);

		if (!perfilDeClientesDao.editarCatSegmentoLealtad(segmentoLealtadDocument, segmentoLealtad.getId())) {

			return new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("No fue posible actualzar el segmento lealtads."));
		}
		
		for (BeanSegmentoLealtad segLeal : catalogo) {
			if (segLeal.getId().equals(segmentoLealtad.getId())) {
				bitacora.registrar(AccionBitacora.EDITAR, "Actualización segmento de lealtad", segLeal,
						segmentoLealtad);
			}
		}
		return new ResponseApi(EstatusResponse.OK);
	}

	/**
	 * Edita el catalogo de segmento de rentabilidad
	 * 
	 * @param segmentoRentabilidad - Identificador del segmento
	 * @return respuesta del update
	 */
	public ResponseApi editarCatSegmentoRentabilidad(BeanSegmentoRentabilidad segmentoRentabilidad) {

		List<BeanSegmentoRentabilidad> catalogo = perfilDeClientesDao.segmentorentavilidad();
		
		// Se crea el documento a Editar
		Document segmentoDocument = new Document();
		segmentoDocument.append("_id", segmentoRentabilidad.getId())
				.append(DESCRIP, segmentoRentabilidad.getDescripcion())
				.append("monto_minimo", segmentoRentabilidad.getMontoMinimo())
				.append("monto_maximo", segmentoRentabilidad.getMontoMaximo())
				.append("maximo_premios", segmentoRentabilidad.getMaximoPremios())
				.append(ACTIVO, segmentoRentabilidad.getActivo());

		if (perfilDeClientesDao.editarCatSegmentoRentabilidad(segmentoDocument)) {

			for (BeanSegmentoRentabilidad segRent : catalogo) {
				if (segRent.getId().equals(segmentoRentabilidad.getId())) {
					
					bitacora.registrar(AccionBitacora.EDITAR, "Actualización segmento de Rentabilidad", segRent,segmentoRentabilidad);
				}
			}
			
			return new ResponseApi(EstatusResponse.OK);
		}

		return new ResponseApi(EstatusResponse.ERROR_INTERNO,
				Arrays.asList("No fue posible actualzar el segmento Rentavilidad."));
	}

	/**
	 * Edita el catalogo de valores rentabilidad
	 * 
	 * @param valoresRentabilidad - bean rentavilidad
	 * @return respuesta del updat
	 */
	public ResponseApi editarCatValoresRentabilidad(BeanValoresRentabilidad valoresRentabilidad) {

		List<BeanValoresRentabilidad> catalogo = perfilDeClientesDao.valoresRentabilidad();
		
		// Se crea el documento a Editar
		Document valoresRentabilidadDocument = new Document();
		valoresRentabilidadDocument.append("_id", valoresRentabilidad.getId())
				.append(DESCRIP, valoresRentabilidad.getDescripcion()).append("valor", valoresRentabilidad.getValor())
				.append(ACTIVO, valoresRentabilidad.getActivo());

		if (perfilDeClientesDao.editarCatValoresRentabilidad(valoresRentabilidadDocument)) {

			for (BeanValoresRentabilidad valRen : catalogo) {
				if (valRen.getId().equals(valoresRentabilidad.getId())) {
					
					bitacora.registrar(AccionBitacora.EDITAR, "Actualización el Valores Rentabilidad.", valRen,valoresRentabilidad);
				}
			}
			
			return new ResponseApi(EstatusResponse.OK);
		}
		return new ResponseApi(EstatusResponse.ERROR_INTERNO,
				Arrays.asList("No fue posible actualzar el Valores Rentabilidad."));

	}
}
