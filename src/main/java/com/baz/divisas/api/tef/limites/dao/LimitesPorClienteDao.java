package com.baz.divisas.api.tef.limites.dao;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.tef.limites.dto.LimitesPorClienteDto;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

/**
 * Clase LimitesPorClienteDao
 * 
 * @author 164945 ogatica
 *
 */
@Repository
public class LimitesPorClienteDao {

	private FactoryMongoDB factoryDB;
	private MongoDatabase terminalFinancieroBD;
	private MongoCollection<LimitesPorClienteDto> coleccionLimitesCliente;

	private static final String ACTIVO = "activo";
	private static final String IDCLIENTEDIVISAS = "idClienteDivisas";
	private static final String CODIGODIVISA = "codigoDivisa";
	private static final String IDTIPOOPERACION = "idTipoOperacion";
	private static final String ID = "_id";

	/**
	 * Constructor de la clase
	 */
	public LimitesPorClienteDao() {
		super();
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		factoryDB = FactoryMongoDB.getInstance();
		terminalFinancieroBD = factoryDB.getMongoClient().getDatabase(PropBD.getTerminalFinancieroBD());
		coleccionLimitesCliente = terminalFinancieroBD.getCollection(PropBD.getLimitesClienteColeccion(), 
				LimitesPorClienteDto.class).withCodecRegistry(pojoCodecRegistry);
	}

	/**
	 * Constructor de la clase.
	 *
	 * @param coleccion - Mock Collection
	 */
	public LimitesPorClienteDao(MongoCollection<LimitesPorClienteDto> coleccion) {
		super();
		this.coleccionLimitesCliente = coleccion;
	}
	
	/**
	 * Metodo de consulta de limites por cliente
	 * 
	 * @return - Limites por cliente
	 */
	public List<LimitesPorClienteDto> findLimitesCliente() {

		return coleccionLimitesCliente.find(new Document("codigoDivisa",new Document("$nin",Arrays.asList("OLP")))).sort(Sorts.descending(IDCLIENTEDIVISAS))
				.into(new ArrayList<LimitesPorClienteDto>());

	}

	/**
	 * Metodo de consulta de limitesCliente por id
	 * 
	 * @param id - Id limite
	 * @return - LimitesPorClienteDto
	 */
	public LimitesPorClienteDto findLimitesClienteId(String id) {

		return coleccionLimitesCliente.find(eq(ID, new ObjectId(id))).first();

	}

	/**
	 * Metodo de consulta de limitesCliente por id
	 * 
	 * @param request - LimitesPorClienteDto
	 * @return - Registros de limitesCliente por id
	 */
	public LimitesPorClienteDto findLimitesClienteId(LimitesPorClienteDto request) {

		return coleccionLimitesCliente
				.find(and(eq(IDCLIENTEDIVISAS, request.getIdClienteDivisas()),
						in(IDTIPOOPERACION, request.getIdTipoOperacion()), eq(CODIGODIVISA, request.getCodigoDivisa())))
				.first();

	}

	/**
	 * Metodo de registro de limites por cliente
	 * 
	 * @param limites - LimitesPorClienteDto
	 * @return InsertOneResult
	 */
	public InsertOneResult insertLimitesCliente(LimitesPorClienteDto limites) {

		return coleccionLimitesCliente.insertOne(limites);

	}

	/**
	 * Metodo de actualización de estatus de la notificacion
	 * 
	 * @param request - LimitesPorClienteDto
	 * @return - UpdateResult
	 */
	public UpdateResult updateLimitesCliente(LimitesPorClienteDto request) {

		Document diario = new Document();
		Document mensual = new Document();

		diario.append("cliente", request.getDiario().getCliente());
		diario.append("usuario", request.getDiario().getUsuario());
		mensual.append("cliente", request.getMensual().getCliente());
		mensual.append("usuario", request.getMensual().getUsuario());

		return coleccionLimitesCliente.updateOne(eq(ID, new ObjectId(request.getId())), combine(set("diario", diario),
				set("mensual", mensual), set("idBitacoraActualizacion", request.getIdBitacoraActualizacion())));
	}

	/**
	 * Metodo de actualización de estatus de la notificacion.
	 *
	 * @param id - Id del Limite
	 * @param activo - Estatus
	 * @param idBitacoraActualizacion - Id bitacora actualizacion
	 * @return - UpdateResult
	 */
	public UpdateResult updateEstatusLimitesCliente(String id, boolean activo, String idBitacoraActualizacion) {

		return coleccionLimitesCliente.updateOne(eq(ID, new ObjectId(id)),
				combine(set(ACTIVO, activo), set("idBitacoraActualizacion", idBitacoraActualizacion)));

	}

	public List<LimitesPorClienteDto> findLimitesClienteOlp() {
		return coleccionLimitesCliente.find(new Document("codigoDivisa","OLP")).sort(Sorts.descending(IDCLIENTEDIVISAS))
				.into(new ArrayList<LimitesPorClienteDto>());
	}

}
