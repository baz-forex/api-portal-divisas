package com.baz.divisas.api.tef.limites.dao;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Projections.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.tef.general.model.Limites;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.config.PropBDCatalogos;
import com.baz.divisas.general.factory.FactoryMongoDB;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

/**
 * Clase LimitesPorDivisaDao
 * 
 * @author 164945 ogatica
 *
 */
@Repository
public class LimitesPorDivisaDao {

	private FactoryMongoDB factoryDB;
	private MongoDatabase catalogosDivisasBD;
	private MongoCollection<Limites> coleccionDivisas;

	private static final String LIMITESACTIVO = "limites.$[elem].activo";
	private static final String IDTIPOOPERACION = "limites.idTipoOperacion";
	private static final String IDBITACORAALTA = "limites.idBitacoraAlta";
	private static final String ID = "_id";

	/**
	 * Constructor de la clase
	 */
	public LimitesPorDivisaDao() {
		super();
		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		factoryDB = FactoryMongoDB.getInstance();
		catalogosDivisasBD = factoryDB.getMongoClient().getDatabase(PropBDCatalogos.getCatalogosBD());
		coleccionDivisas = catalogosDivisasBD.getCollection(PropBD.getCatMonedasColeccion(), Limites.class)
				.withCodecRegistry(pojoCodecRegistry);
	}

	/**
	 * Constructor de la clase.
	 *
	 * @param coleccion - Mock Collection
	 */
	public LimitesPorDivisaDao(MongoCollection<Limites> coleccion) {
		super();
		this.coleccionDivisas = coleccion;
	}
	
	/**
	 * Metodo de consulta de limites por divisa
	 * 
	 * @return - Limites
	 */
	public List<Limites> findLimitesDivias() {
		
		return coleccionDivisas
				.aggregate(Arrays.asList(
						match(and(eq(ID, new Document("$exists", true)),ne(ID,"OLP"))), 
						unwind("$limites"),
						project(fields(computed("codigoDivisa", "$limites.codigoDivisa"),
									   computed("idTipoOperacion", "$limites.idTipoOperacion"),
									   computed("diario", "$limites.diario"),
									   computed("mensual", "$limites.mensual"),
									   computed("activo", "$limites.activo"),
									   computed("idBitacoraAlta", "$limites.idBitacoraAlta"),
									   computed("idBitacoraActualizacion", "$limites.idBitacoraActualizacion"))),
						sort(Sorts.descending("idBitacoraAlta"))))
				.allowDiskUse(true).into(new ArrayList<Limites>());

	}

	/**
	 * Metodo de consulta de limites por id divisa
	 * 
	 * @param request - Limites
	 * @return - Limites
	 */
	public List<Limites> findLimitesDiviasId(Limites request) {

		return coleccionDivisas
				.aggregate(Arrays.asList(
						match(eq(ID, request.getCodigoDivisa())), 
						unwind("$limites"),
						match(eq(IDTIPOOPERACION, request.getIdTipoOperacion())), 
						sort(Sorts.descending(IDBITACORAALTA))))
				.allowDiskUse(true).into(new ArrayList<Limites>());

	}

	/**
	 * Metodo de registro de limites por divisa
	 * 
	 * @param request - Limites
	 * @return UpdateResult
	 */
	public UpdateResult insertLimitesDivias(Limites request) {

		return coleccionDivisas.updateOne(eq(ID, request.getCodigoDivisa()), addToSet("limites", request));

	}

	/**
	 * Metodo de actualización del limite por divisa
	 * 
	 * @param request - Limites
	 * @return - UpdateResult
	 */
	public UpdateResult updateLimitesDivisas(Limites request) {

		Document diario = new Document();
		Document mensual = new Document();

		diario.append("cliente", request.getDiario().getCliente());
		diario.append("usuario", request.getDiario().getUsuario());
		mensual.append("cliente", request.getMensual().getCliente());
		mensual.append("usuario", request.getMensual().getUsuario());

		return coleccionDivisas.updateOne(
				and(eq(ID, request.getCodigoDivisa()), eq(IDTIPOOPERACION, request.getIdTipoOperacion())),
				combine(set("limites.$[elem].diario", diario), set("limites.$[elem].mensual", mensual),
						set("limites.$[elem].idBitacoraActualizacion", request.getIdBitacoraActualizacion())),
				new UpdateOptions().arrayFilters(Arrays.asList(eq("elem.idTipoOperacion", request.getIdTipoOperacion()))));

	}

	/**
	 * Metodo de actualización estatus del limite por divisa
	 * 
	 * @param request - Limites
	 * @return - UpdateResult
	 */
	public UpdateResult updateEstatusLimitesDivisas(Limites request) {

		return coleccionDivisas.updateOne(
				and(eq(ID, request.getCodigoDivisa()), eq(IDTIPOOPERACION, request.getIdTipoOperacion())),
				combine(set(LIMITESACTIVO, request.isActivo()),
						set("limites.$[elem].idBitacoraActualizacion", request.getIdBitacoraActualizacion())),
				new UpdateOptions().arrayFilters(Arrays.asList(eq("elem.idTipoOperacion", request.getIdTipoOperacion()))));

	}

	public List<Limites> findLimitesDivisaOLP() {
		return coleccionDivisas
				.aggregate(Arrays.asList(
						match(eq(ID, "OLP")), 
						unwind("$limites"),
						project(fields(computed("codigoDivisa", "$limites.codigoDivisa"),
									   computed("idTipoOperacion", "$limites.idTipoOperacion"),
									   computed("diario", "$limites.diario"),
									   computed("mensual", "$limites.mensual"),
									   computed("activo", "$limites.activo"),
									   computed("idBitacoraAlta", "$limites.idBitacoraAlta"),
									   computed("idBitacoraActualizacion", "$limites.idBitacoraActualizacion"))),
						sort(Sorts.descending("idBitacoraAlta"))))
				.allowDiskUse(true).into(new ArrayList<Limites>());
	}

}
