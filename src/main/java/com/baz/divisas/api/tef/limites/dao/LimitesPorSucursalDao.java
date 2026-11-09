package com.baz.divisas.api.tef.limites.dao;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.computed;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Updates.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.tef.limites.dto.LimitesPorSucursalDto;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

/**
 * Clase LimitesPorSucursalDao
 * 
 * @author 164945 ogatica
 *
 */
@Repository
public class LimitesPorSucursalDao {

	private FactoryMongoDB factoryDB;
	private MongoDatabase terminalFinancieroBD;
	private MongoCollection<LimitesPorSucursalDto> coleccionSucursales;

	private static final String LIMITESACTIVO = "limites.$[elem].activo";
	private static final String CODIGODIVISA = "limites.codigoDivisa";
	private static final String IDTIPOOPERACION = "limites.idTipoOperacion";
	private static final String IDBITACORAALTA = "limites.idBitacoraAlta";
	private static final String ID = "_id";

	/**
	 * Constructor de la clase
	 */
	public LimitesPorSucursalDao() {
		super();
		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		factoryDB = FactoryMongoDB.getInstance();
		terminalFinancieroBD = factoryDB.getMongoClient().getDatabase(PropBD.getTerminalFinancieroBD());
		coleccionSucursales = terminalFinancieroBD.getCollection(PropBD.getSucursales(), LimitesPorSucursalDto.class)
				.withCodecRegistry(pojoCodecRegistry);
	}
	
	/**
	 * Constructor de la clase.
	 *
	 * @param coleccion - Mock Collection
	 */
	public LimitesPorSucursalDao(MongoCollection<LimitesPorSucursalDto> coleccion) {
		super();
		this.coleccionSucursales = coleccion;
	}

	/**
	 * Metodo de consulta de limites por sucursal
	 * 
	 * @return - Limites por sucursal
	 */
	public List<LimitesPorSucursalDto> findLimitesSucursal() {

		return coleccionSucursales
				.aggregate(Arrays.asList(match(eq(ID, new Document("$exists", true))), 
						unwind("$limites"),
						match(nin("limites.codigoDivisa", Arrays.asList("OLP"))),
						project(fields(
								   computed("descripcion", "$descripcion"),
								   computed("codigoDivisa", "$limites.codigoDivisa"),
								   computed("idTipoOperacion", "$limites.idTipoOperacion"),
								   computed("diario", "$limites.diario"),
								   computed("mensual", "$limites.mensual"),
								   computed("activo", "$limites.activo"),
								   computed("idBitacoraAlta", "$limites.idBitacoraAlta"),
								   computed("idBitacoraActualizacion", "$limites.idBitacoraActualizacion"))),
						sort(Sorts.descending("idBitacoraAlta"))))
				.allowDiskUse(true).into(new ArrayList<LimitesPorSucursalDto>());

	}

	/**
	 * Metodo de consulta de limitesSucursal por id
	 * 
	 * @param request - LimitesPorSucursalDto
	 * @return - Limites por sucursal
	 */
	public List<LimitesPorSucursalDto> findLimitesSucursalId(LimitesPorSucursalDto request) {

		return coleccionSucursales
				.aggregate(Arrays.asList(match(eq(ID, request.getIdSucursal())), unwind("$limites"),
						match(and(eq(CODIGODIVISA, request.getCodigoDivisa()),
								eq(IDTIPOOPERACION, request.getIdTipoOperacion()))),
						sort(Sorts.descending(IDBITACORAALTA))))
				.allowDiskUse(true).into(new ArrayList<LimitesPorSucursalDto>());

	}

	/**
	 * Metodo de registro de limites por sucursal
	 * 
	 * @param limites - limites por sucursal a registrar
	 * @return UpdateResult
	 */
	public UpdateResult insertLimitesSucursal(LimitesPorSucursalDto limites) {

		return coleccionSucursales.updateOne(eq(ID, limites.getIdSucursal()), addToSet("limites", limites));

	}

	/**
	 * Metodo de actualización del limite de la sucursal
	 * 
	 * @param request - LimitesPorSucursalDto
	 * @return - UpdateResult
	 */
	public UpdateResult updateLimitesSucursal(LimitesPorSucursalDto request) {

		Document diario = new Document();
		Document mensual = new Document();

		diario.append("cliente", request.getDiario().getCliente());
		diario.append("usuario", request.getDiario().getUsuario());
		diario.append("clienteExtranjero", request.getDiario().getClienteExtranjero());
		diario.append("usuarioExtranjero", request.getDiario().getUsuarioExtranjero());
		
		mensual.append("cliente", request.getMensual().getCliente());
		mensual.append("usuario", request.getMensual().getUsuario());
		mensual.append("clienteExtranjero", request.getMensual().getClienteExtranjero());
		mensual.append("usuarioExtranjero", request.getMensual().getUsuarioExtranjero());
		
		return coleccionSucursales.updateOne(
				and(eq(ID, request.getIdSucursal()), eq(CODIGODIVISA, request.getCodigoDivisa()),
						eq(IDTIPOOPERACION, request.getIdTipoOperacion())),
				combine(set("limites.$[elem].diario", diario), set("limites.$[elem].mensual", mensual),
						set("limites.$[elem].idBitacoraActualizacion", request.getIdBitacoraActualizacion())),
				new UpdateOptions().arrayFilters(Arrays.asList(and(eq("elem.codigoDivisa", request.getCodigoDivisa()),
						eq("elem.idTipoOperacion", request.getIdTipoOperacion())))));

	}

	/**
	 * Metodo de actualización de estatus del limite de la sucursal
	 * 
	 * @param request - LimitesPorSucursalDto
	 * @return - UpdateResult
	 */
	public UpdateResult updateEstatusLimitesSucursal(LimitesPorSucursalDto request) {

		return coleccionSucursales.updateOne(
				and(eq(ID, request.getIdSucursal()), eq(CODIGODIVISA, request.getCodigoDivisa()),
						eq(IDTIPOOPERACION, request.getIdTipoOperacion())),
				combine(set(LIMITESACTIVO, request.isActivo()),
						set("limites.$[elem].idBitacoraActualizacion", request.getIdBitacoraActualizacion())),
				new UpdateOptions().arrayFilters(Arrays.asList(and(eq("elem.codigoDivisa", request.getCodigoDivisa()),
						eq("elem.idTipoOperacion", request.getIdTipoOperacion())))));

	}

	public List<LimitesPorSucursalDto> findLimitesSucursalOlp() {
		return coleccionSucursales
				.aggregate(Arrays.asList(match(eq(ID, new Document("$exists", true))), 
						unwind("$limites"),
						match(in("limites.codigoDivisa", Arrays.asList("OLP"))),
						project(fields(
								   computed("descripcion", "$descripcion"),
								   computed("codigoDivisa", "$limites.codigoDivisa"),
								   computed("idTipoOperacion", "$limites.idTipoOperacion"),
								   computed("diario", "$limites.diario"),
								   computed("mensual", "$limites.mensual"),
								   computed("activo", "$limites.activo"),
								   computed("idBitacoraAlta", "$limites.idBitacoraAlta"),
								   computed("idBitacoraActualizacion", "$limites.idBitacoraActualizacion"))),
						sort(Sorts.descending("idBitacoraAlta"))))
				.allowDiskUse(true).into(new ArrayList<LimitesPorSucursalDto>());
	}
}
