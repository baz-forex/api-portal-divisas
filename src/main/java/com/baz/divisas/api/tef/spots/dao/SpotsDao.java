/**
 * 
 */
package com.baz.divisas.api.tef.spots.dao;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.tef.general.model.Segmento;
import com.baz.divisas.api.tef.regiones.model.Region;
import com.baz.divisas.api.tef.spots.dto.Spot;
import com.baz.divisas.api.tef.sucursales.model.Sucursal;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.DateUtils;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

/**
 * Clase SpotsDao.
 *
 * @author 1077023
 */
@Repository
public class SpotsDao {

	/** The factory DB. */
	private FactoryMongoDB factoryDB;
	
	/** The terminal financiero BD. */
	private MongoDatabase terminalFinancieroBD;
	
	/** The coleccion cat spot. */
	private MongoCollection<Spot> coleccionCatSpot;
	
	/** The coleccion regiones. */
	private MongoCollection<Region> coleccionRegiones;
	
	/** The coleccion segmentos. */
	private MongoCollection<Segmento> coleccionSegmentos;
	
	/** The coleccion sucursales. */
	private MongoCollection<Sucursal> coleccionSucursales;

	/** The Constant ID_DIVISA. */
	private static final String ID_DIVISA = "idDivisa";
	
	/** The Constant FECHA_REGISTRO. */
	private static final String FECHA_REGISTRO = "fechaRegistro";
	
	/** The Constant ID. */
	private static final String ID = "_id";
	
	/** The Constant ACTIVO. */
	private static final String ACTIVO = "activo";
	
	/** The Constant DIVISAS_ID. */
	private static final String DIVISAS_ID = "divisas._id";
	
	/** The Constant DIVISA_ID. */
	private static final String DIVISA_ID = "divisa._id";	
	
	/** The Constant DIVISAS_TIPOOPEID. */
	private static final String DIVISAS_TIPOOPEID = "divisas.tiposOperacion.idTipoOperacion";	
	
	/** The Constant TIPOSOPE_ID. */
	private static final String TIPOSOPE_ID = "tiposOperaciones.idTipoOperacion";		
	
	/** The Constant DIVISA_TCSPOT. */
	private static final String DIVISA_TCSPOT = "divisas.$[divisa].tiposOperacion.$[tiposOperaciones].tipoCambioSpot";	
	
	/** The Constant OLP_TIPOOPEID. */
	private static final String OLP_TIPOOPEID = "tcOlp.tiposOperacion.idTipoOperacion";
	
	/** The Constant OLP_TCSPOT. */
	private static final String OLP_TCSPOT = "tcOlp.tiposOperacion.$[tiposOperaciones].tipoCambioSpot";

	/**
	 * Constructor de la clase.
	 */
	public SpotsDao() {

		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		factoryDB = FactoryMongoDB.getInstance();
		terminalFinancieroBD = factoryDB.getMongoClient().getDatabase(PropBD.getTerminalFinancieroBD());
		coleccionCatSpot = terminalFinancieroBD.getCollection(PropBD.getCatSpot(), Spot.class)
				.withCodecRegistry(pojoCodecRegistry);

		coleccionRegiones = terminalFinancieroBD.getCollection(PropBD.getRegionesColeccion(), Region.class)
				.withCodecRegistry(pojoCodecRegistry);

		coleccionSegmentos = terminalFinancieroBD.getCollection(PropBD.getSegmentosColeccion(), Segmento.class)
				.withCodecRegistry(pojoCodecRegistry);

		coleccionSucursales= terminalFinancieroBD.getCollection(PropBD.getSucursales(),Sucursal.class)
				.withCodecRegistry(pojoCodecRegistry);


	}
	
	/**
	 * Constructor de la clase .
	 *
	 * @param coleccionCatSpot the coleccion cat spot
	 * @param coleccionRegiones the coleccion regiones
	 * @param coleccionSegmentos the coleccion segmentos
	 * @param coleccionSucursales the coleccion sucursales
	 */
	public SpotsDao(MongoCollection<Spot> coleccionCatSpot, MongoCollection<Region> coleccionRegiones,
			MongoCollection<Segmento> coleccionSegmentos, MongoCollection<Sucursal> coleccionSucursales) {
		super();
		this.coleccionCatSpot = coleccionCatSpot;
		this.coleccionRegiones = coleccionRegiones;
		this.coleccionSegmentos = coleccionSegmentos;
		this.coleccionSucursales = coleccionSucursales;
	}
	

	/**
	 * Metodo de consulta de Spot.
	 *
	 * @return - Consulta de Spot
	 */
	public List<Spot> findSpots() {

		return coleccionCatSpot.find().sort(Sorts.descending(ID)).into(new ArrayList<Spot>());

	}

	/**
	 * Metodo de consulta de Spots por fecha.
	 *
	 * @param fechaInicio - Fecha Inicio
	 * @param fechaFin   - Fecha Fin
	 * @param idsDivisas  - Ids Divisas
	 * @return Spots
	 */
	public List<Spot> findSpotsPorFecha(String fechaInicio, String fechaFin, List<String> idsDivisas) {

		return coleccionCatSpot.find(and(gte(FECHA_REGISTRO, fechaInicio), lte(FECHA_REGISTRO, fechaFin), 
				in(ID_DIVISA, idsDivisas)))
				.sort(Sorts.descending(ID)).into(new ArrayList<Spot>());

	}

	/**
	 * Metodo de consulta del ultimo Spot registrado por Divisa.
	 *
	 * @param idDivisa - Id Divisa
	 * @return Spot
	 */
	public Spot findSpotVigenteDivisa(String idDivisa) {

		return coleccionCatSpot.find(eq(ID_DIVISA, idDivisa)).sort(Sorts.descending(ID)).first();

	}

	/**
	 * Metodo de registro de Spots.
	 *
	 * @param spots - Spots a registrar
	 * @return InsertOneResult
	 */
	public void insertSpots(List<Spot> spots) {

		for(Spot spot:spots) {
			spot.setFechaRegistro(DateUtils.getDateFormat(Constant.FORMATOFECHACORTA));
			spot.setHoraRegistro(DateUtils.getDateFormat(Constant.FORMATOHORA));

			coleccionCatSpot.insertOne(spot);
		}
	}
	
	/**
	 * Metodo de registro de Spot.
	 *
	 * @param spots - Spot a registrar
	 * @return InsertOneResult
	 */
	public InsertOneResult insertSpot(Spot spot) {

		spot.setFechaRegistro(DateUtils.getDateFormat(Constant.FORMATOFECHACORTA));
		spot.setHoraRegistro(DateUtils.getDateFormat(Constant.FORMATOHORA));

		return coleccionCatSpot.insertOne(spot);

	}

	/**
	 * Metodo de actualización de id Bitacora.
	 *
	 * @param id     - Id del Spot
	 * @param idBitacora - Estatus
	 * @return - UpdateResult
	 */
	public UpdateResult updateIdBitacoraAlta(String id, String idBitacora) {

		return coleccionCatSpot.updateOne(eq(ID, new ObjectId(id)), set("idBitacora", idBitacora));

	}

	/**
	 * Actualiza Tipo de Cambio Spot de una Region.
	 *
	 * @param idRegion - Id de Region
	 * @param idDivisa - Id de Divisa
	 * @param tipoOperacion - Tipo de Operacion
	 * @param tcSpot - Tipo de Cambio Spot
	 * @return UpdateResult
	 */
	public UpdateResult updateTCSpotRegion(String idRegion, String idDivisa, 
			String tipoOperacion, Double tcSpot) {

		return coleccionRegiones.updateOne(
					and(eq(ID, new ObjectId(idRegion)),
						eq(ACTIVO, true),
						eq(DIVISAS_ID, idDivisa),
						eq(DIVISAS_TIPOOPEID, tipoOperacion)),

					set(DIVISA_TCSPOT, tcSpot),
				new UpdateOptions().arrayFilters(Arrays.asList(
						eq(DIVISA_ID, idDivisa),
						eq(TIPOSOPE_ID, tipoOperacion))));

	}

	/**
	 * Actualiza Tipo de Cambio Spot de Regiones.
	 *
	 * @param regiones - Ids de regiones
	 * @param idDivisa - Id de Divisa
	 * @param tipoOperacion - Tipo de Operacion
	 * @param tcSpot - Tipo de cambio Spot
	 * @return UpdateResult
	 */
	public UpdateResult updateTCSpotRegiones(Collection<String> regiones , String idDivisa, 
			String tipoOperacion, Double tcSpot) {

		List<ObjectId> idsRegiones = regiones.stream().map(ObjectId::new).collect(Collectors.toList());
		
		return coleccionRegiones.updateMany(
					and(in(ID, idsRegiones),
						eq(ACTIVO, true),
						eq(DIVISAS_ID, idDivisa),
						eq(DIVISAS_TIPOOPEID, tipoOperacion)),
					set(DIVISA_TCSPOT, tcSpot),
				new UpdateOptions().arrayFilters(Arrays.asList(
						eq(DIVISA_ID, idDivisa),
						eq(TIPOSOPE_ID, tipoOperacion))));

	}

	/**
	 * Actualiza Tipo de Cambio Spot de un Segmento.
	 *
	 * @param idSegmento - Id de Segmento
	 * @param idDivisa - Id de Divisa
	 * @param tipoOperacion - Tipo de Operacion
	 * @param tcSpot - Tipo de Cambio Spot
	 * @return UpdateResult
	 */
	public UpdateResult updateTCSpotSegmento(String idSegmento, String idDivisa, 
			String tipoOperacion, Double tcSpot) {

		return coleccionSegmentos.updateOne(
					and(eq(ID, new ObjectId(idSegmento)),
						eq(ACTIVO, true),
						eq(DIVISAS_ID, idDivisa),
						eq(DIVISAS_TIPOOPEID, tipoOperacion)),

					set(DIVISA_TCSPOT, tcSpot),
				new UpdateOptions().arrayFilters(Arrays.asList(
						eq(DIVISA_ID, idDivisa),
						eq(TIPOSOPE_ID, tipoOperacion))));

	}
	
	/**
	 * Actualiza Tipo de Cambio Spot de Segmentos.
	 *
	 * @param segmentos - Ids de segmentos
	 * @param idDivisa - Id de Divisa
	 * @param tipoOperacion - Tipo de Operacion
	 * @param tcSpot - Tipo de cambio Spot
	 * @return UpdateResult
	 */
	public UpdateResult updateTCSpotSegmentos(Collection<String> segmentos , String idDivisa, 
			String tipoOperacion, Double tcSpot) {

		List<ObjectId> idsSegmentos = segmentos.stream().map(ObjectId::new).collect(Collectors.toList());
		
		return coleccionSegmentos.updateMany(
					and(in(ID, idsSegmentos),
						eq(ACTIVO, true),
						eq(DIVISAS_ID, idDivisa),
						eq(DIVISAS_TIPOOPEID, tipoOperacion)),

					set(DIVISA_TCSPOT, tcSpot),
				new UpdateOptions().arrayFilters(Arrays.asList(
						eq(DIVISA_ID, idDivisa),
						eq(TIPOSOPE_ID, tipoOperacion))));

	}

	/**
	 * Actualiza Tipo de Cambio Spot de sucursales.
	 *
	 * @param sucursal the sucursal
	 * @param idDivisa - Id de la divisa
	 * @param tipoOperacion - Tipo de operacion
	 * @param tcSpot - Tipo de Cambio Spot
	 * @return Resultado
	 */
	public UpdateResult updateTCSpotSucursales(String sucursal,String idDivisa, 
			String tipoOperacion, Double tcSpot) {

		UpdateResult resultado = coleccionSucursales.updateOne(
						and(eq(ID, sucursal),
							eq(DIVISAS_ID, idDivisa),
							eq(DIVISAS_TIPOOPEID, tipoOperacion)),
						combine(set(DIVISA_TCSPOT, tcSpot),
						set("fechaUltimoCambio",DateUtils.getDateFormat("YYYY-MM-dd HH:mm:ss"))),
					new UpdateOptions().arrayFilters(Arrays.asList(
							eq(DIVISA_ID, idDivisa), eq(TIPOSOPE_ID, tipoOperacion))));
		
		

		return resultado;

	}

	/**
	 * Update TC spot sucursales OLP.
	 *
	 * @param sucursal the sucursal
	 * @param tipoOperacion the tipo operacion
	 * @param tcSpot the tc spot
	 * @return the update result
	 */
	public UpdateResult updateTCSpotSucursalesOLP(String sucursal, String tipoOperacion, double tcSpot) {
		UpdateResult resultado = coleccionSucursales.updateOne(
				and(eq(ID, sucursal),
						eq(OLP_TIPOOPEID, tipoOperacion)),
				combine(set(OLP_TCSPOT, tcSpot)),
				new UpdateOptions().arrayFilters(Arrays.asList(
						 eq(TIPOSOPE_ID, tipoOperacion))));

		return resultado;

	}
}
