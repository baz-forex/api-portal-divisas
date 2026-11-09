package com.baz.divisas.api.catalogos.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.bson.BsonArray;
import org.bson.Document;
import org.bson.codecs.BsonArrayCodec;
import org.bson.codecs.BsonValueCodecProvider;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.DocumentCodecProvider;
import org.bson.codecs.ValueCodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.json.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.catalogos.dao.CatalogosDao;
import com.baz.divisas.api.catalogos.model.CatPaisNacionalidad;
import com.baz.divisas.api.catalogos.model.CatRangoEdadesCampanias;
import com.baz.divisas.api.catalogos.model.Catalogo;
import com.baz.divisas.api.tef.general.model.Limites;
import com.baz.divisas.api.tef.general.model.MontosLimite;
import com.baz.divisas.api.tef.regiones.dao.RegionesDao;
import com.baz.divisas.api.tef.regiones.model.Divisa;
import com.baz.divisas.api.tef.regiones.model.Estrategia;
import com.baz.divisas.api.tef.regiones.model.TipoOperacion;
import com.baz.divisas.api.tef.segmentos.dao.SegmentosDao;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.DateUtils;
import com.google.gson.Gson;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

/**
 * Servicio de Catalogos
 * 
 * @author B813196 itoxtle
 */
@Service
public class CatalogosServiceImpl {

	private CatalogosDao catalogosDao;
	@Autowired
	private BitacoraServiceImpl bitacora;
	@Autowired
	private SegmentosDao segmentoDao;
	@Autowired
	private RegionesDao regionDao;

	private static final String FORMATOFECHA = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	/**
	 * Constructor de la clase
	 * 
	 * @param catalogosDao - DAO Catalogos
	 */
	@Autowired
	public CatalogosServiceImpl(CatalogosDao catalogosDao, RegionesDao regionDao, SegmentosDao segmentoDao, BitacoraServiceImpl bitacora) {
		super();
		this.catalogosDao = catalogosDao;
		this.regionDao = regionDao;
		this.segmentoDao = segmentoDao;
		this.bitacora = bitacora;
	}

	/**
	 * Metodo de consulta de catalogo de Paises/Nacionalidades
	 * 
	 * @return ResponseApi
	 */
	public ResponseApi getCatPaisNacionalidad() {

		ResponseApi resultado;

		// Consulta paises restringidos
		List<CatPaisNacionalidad> paisesNacionalidades = catalogosDao.findCatPaisNacionalidad();

		if (!paisesNacionalidades.isEmpty()) {
			resultado = new ResponseApi(EstatusResponse.OK, paisesNacionalidades);
		} else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
		}

		return resultado;
	}

	/**
	 * Metodo de consulta de catalogo de Estados
	 * 
	 * @return ResponseApi
	 */
	public ResponseApi getCatEstados() {

		ResponseApi resultado;

		// Consulta paises restringidos
		List<Catalogo> estados = catalogosDao.findCatEstados();

		if (!estados.isEmpty()) {
			resultado = new ResponseApi(EstatusResponse.OK, estados);
		} else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
		}

		return resultado;
	}

	/**
	 * Metodo de consulta de catalogo de Divisas
	 * 
	 * @return ResponseApi
	 */
	public ResponseApi getCatDivisas() {

		ResponseApi resultado;

		List<Catalogo> divisas = catalogosDao.findCatDivisas();

		if (!divisas.isEmpty()) {

			resultado = new ResponseApi(EstatusResponse.OK, divisas);

		} else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
		}

		return resultado;
	}
	
	/**
	 * Metodo de consulta de catalogo de identificaciones
	 * 
	 * @return ResponseApi
	 */
	public ResponseApi getCatIdentificaciones() {

		ResponseApi resultado;

		List<Catalogo> identificaciones = catalogosDao.findCatIdentificaciones();

		if (!identificaciones.isEmpty()) {

			resultado = new ResponseApi(EstatusResponse.OK, identificaciones);

		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
		}

		return resultado;
	}

	/**
	 * Metodo de actualizacion de divisas
	 * 
	 * @param request - Divisa
	 * @return Resultado
	 */
	public ResponseApi actualizaEstatusDivisa(@Valid Catalogo request) {

		ResponseApi resultado;

		boolean actualizado = catalogosDao.actualizarEstatusCatDivisas(request.getId(), request.isActivo());

		if (actualizado) {
			resultado = new ResponseApi(EstatusResponse.OK);
		} else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
		}

		return resultado;
	}

	/**
	 * Metodo de actualizacion de limite de divisa
	 * 
	 * @param reqCreaLimitesDivisa - ReqCreaLimitesDivisa
	 * @return ResponseApi
	 * @throws IOException - IOException
	 */
	public ResponseApi creaDivisa(Catalogo req) {
		ResponseApi resultado;
		List<Limites> limiteList = new ArrayList<>();
		Document divisa = new Document();

		List<Catalogo> catalogo = catalogosDao.findCatDivisasId(req.getId());

		if (catalogo.isEmpty()) {
			String idBitacoraAlta = bitacora.registrar(AccionBitacora.REGISTRAR,
					"Registro del limites de divisas " + req.getId(), null, req);
			Limites limite410 = new Limites(req.getId(), "410", new MontosLimite(),
					new MontosLimite(), false, idBitacoraAlta);

			Limites limite411 = new Limites(req.getId(), "411", new MontosLimite(),
					new MontosLimite(), false, idBitacoraAlta);

			Limites limite412 = new Limites(req.getId(), "412", new MontosLimite(),
					new MontosLimite(), false, idBitacoraAlta);

			Limites limite413 = new Limites(req.getId(), "413", new MontosLimite(),
					new MontosLimite(), false, idBitacoraAlta);

			limiteList.add(limite410);
			limiteList.add(limite411);
			limiteList.add(limite412);
			limiteList.add(limite413);

			String jsnDivisa = new Gson().toJson(limiteList);

			CodecRegistry codecRegistry = CodecRegistries.fromProviders(
					Arrays.asList(new ValueCodecProvider(), new BsonValueCodecProvider(), new DocumentCodecProvider()));

			JsonReader reader = new JsonReader(jsnDivisa);
			BsonArrayCodec arrayReader = new BsonArrayCodec(codecRegistry);

			BsonArray docArray = arrayReader.decode(reader, DecoderContext.builder().build());

			divisa.append("_id", req.getId());
			divisa.append("descripcion", req.getDescripcion().toUpperCase());
			divisa.append("limites", docArray);
			divisa.append("activo", false);

			InsertOneResult resultInsert = catalogosDao.insertDivisa(divisa);

			if (resultInsert.getInsertedId() != null) {

				Limites limites = new Limites(false, new MontosLimite(0.0, 0.0,0.0, 0.0), new MontosLimite(0.0, 0.0,0.0, 0.0));
				Estrategia estrategia = new Estrategia(false, 0.0, DateUtils.getDateFormato(FORMATOFECHA), "");

				TipoOperacion t410 = new TipoOperacion("410", "COMPRA EFECTIVO", 0.0,0.0, estrategia, limites, true);
				TipoOperacion t411 = new TipoOperacion("411", "VENTA EFECTIVO", 0.0,0.0, estrategia, limites, true);
				TipoOperacion t412 = new TipoOperacion("412", "COMPRA ABONO CUENTA", 0.0,0.0, estrategia, limites, true);
				TipoOperacion t413 = new TipoOperacion("413", "VENTA CARGO CUENTA", 0.0,0.0, estrategia, limites, true);

				Divisa divisaNueva = new Divisa(req.getId(), req.getDescripcion().toUpperCase(),
						Arrays.asList(t410, t411, t412, t413), false);

				UpdateResult resultUpdateRegion = regionDao.addDivisa(divisaNueva);
				if (resultUpdateRegion.getMatchedCount() <= 0) {
					catalogosDao.resetLimitesRegion(req.getId());
					return new ResponseApi(EstatusResponse.ERROR_INTERNO,
							Arrays.asList("No fue posible registrar la divisa en las regiones."));

				} else {
					UpdateResult resultUpdateSegmento = segmentoDao.addDivisa(divisaNueva);
					if (resultUpdateSegmento.getMatchedCount() <= 0) {
						catalogosDao.resetLimitesRegion(req.getId());
						return new ResponseApi(EstatusResponse.ERROR_INTERNO,
								Arrays.asList("No fue posible registrar la divisa en los segmentos."));
					}
				}

				resultado = new ResponseApi(EstatusResponse.OK);

			} else {
				resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
						Arrays.asList("No fue posible registrar el limite de divisa."));
			}

		} else {
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("Ya existe un limite de divisa igual."));
		}

		return resultado;
	}
	
	
	/**
	 * Metodo de consulta de Estatus Cotizacion.
	 *
	 * @return ResponseApi
	 */
	public ResponseApi getCatEstatusCotizacion() {

		ResponseApi resultado;

		List<Catalogo> estatus = catalogosDao.findCatEstatusCotizacion();

		if (!estatus.isEmpty()) {

			resultado = new ResponseApi(EstatusResponse.OK, estatus);

		} 
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
		}

		return resultado;
	}
	/**
     * Metodo de consulta de catalogo de tipo operacion
     * 
     * @return ResponseApi
     */
    public ResponseApi getCatTipoOperacion() {
        ResponseApi resultado;

        List<Catalogo> identificaciones = catalogosDao.findCatTipoOperacion();

        if (!identificaciones.isEmpty()) {

            resultado = new ResponseApi(EstatusResponse.OK, identificaciones);

        } else {
            resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
        }

        return resultado;
    }
	/**
     * Metodo de consulta de catalogo de tipo cliente
     * 
     * @return ResponseApi
     */
    public ResponseApi getCatTipoCliente() {
        ResponseApi resultado;

        List<Catalogo> identificaciones = catalogosDao.findCatTipoCliente();

        if (!identificaciones.isEmpty()) {

            resultado = new ResponseApi(EstatusResponse.OK, identificaciones);

        } else {
            resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
        }

        return resultado;
    }
	/**
     * Metodo de consulta de catalogo de perfil cliente campanias
     * 
     * @return ResponseApi
     */
    public ResponseApi getCatPerfilClienteCampanias() {
        ResponseApi resultado;

        List<Catalogo> identificaciones = catalogosDao.findCatPerfilClienteCampanias();

        if (!identificaciones.isEmpty()) {

            resultado = new ResponseApi(EstatusResponse.OK, identificaciones);

        } else {
            resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
        }

        return resultado;
    }
	/**
     * Metodo de consulta de catalogo de tipo limite campanias
     * 
     * @return ResponseApi
     */
    public ResponseApi getCatTipoLimiteCampanias() {
        ResponseApi resultado;

        List<Catalogo> identificaciones = catalogosDao.findCatTipoLimiteCampanias();

        if (!identificaciones.isEmpty()) {

            resultado = new ResponseApi(EstatusResponse.OK, identificaciones);

        } else {
            resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
        }

        return resultado;
    }
	/**
     * Metodo de consulta de catalogo de tipo acumulado campanias
     * 
     * @return ResponseApi
     */
    public ResponseApi getCatTipoAcumuladoCampanias() {
        ResponseApi resultado;

        List<Catalogo> identificaciones = catalogosDao.findCatTipoAcumuladoCampanias();

        if (!identificaciones.isEmpty()) {

            resultado = new ResponseApi(EstatusResponse.OK, identificaciones);

        } else {
            resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
        }

        return resultado;
    }
	/**
     * Metodo de consulta de catalogo de estatus campanias
     * 
     * @return ResponseApi
     */
    public ResponseApi getCatEstatusCampanias() {
        ResponseApi resultado;

        List<Catalogo> identificaciones = catalogosDao.findCatEstatusCampanias();

        if (!identificaciones.isEmpty()) {

            resultado = new ResponseApi(EstatusResponse.OK, identificaciones);

        } else {
            resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
        }

        return resultado;
    }
	/**
     * Metodo de consulta de catalogo de rango de edades campanias
     * 
     * @return ResponseApi
     */
    public ResponseApi getCatRangoEdadesCampanias() {
        ResponseApi resultado;

        List<CatRangoEdadesCampanias> identificaciones = catalogosDao.findCatRangoEdadesCampanias();

        if (!identificaciones.isEmpty()) {

            resultado = new ResponseApi(EstatusResponse.OK, identificaciones);

        } else {
            resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS, Arrays.asList(Constant.SIN_INFORMACION));
        }

        return resultado;
    }

}
