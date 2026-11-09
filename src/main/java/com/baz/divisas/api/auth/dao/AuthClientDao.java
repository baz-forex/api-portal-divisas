package com.baz.divisas.api.auth.dao;

import static org.bson.codecs.configuration.CodecRegistries.*;

import javax.validation.Valid;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.auth.model.AuthClient;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;

import static com.mongodb.client.model.Filters.*;

/**
 * Clase
 * DAO Autenticacion
 * Security
 * 
 * @author B813196
 * itoxtle
 *
 */
@Repository
public class AuthClientDao {
	
	private CodecRegistry pojoCodecRegistry;

	
	private FactoryMongoDB factoryMGDB;
	private MongoDatabase database;
	@Valid
	private MongoCollection<AuthClient> colleccionAuthClientes;

	/**
	 * Constructor de la clase
	 */
	public AuthClientDao() {
		super();
		pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
	  						fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		
		factoryMGDB = FactoryMongoDB.getInstance();
		database = factoryMGDB.getMongoClient().getDatabase(PropBD.getAuthBD());
		colleccionAuthClientes = database.getCollection(PropBD.getAuthClientesApiDivColeccion(), AuthClient.class)
				   					.withCodecRegistry(pojoCodecRegistry);
	}

	/**
	 * Metodo de consulta de usuario por ID
	 * 
	 * @param idUsuario - Identificador de usuario
	 * @return - AuthClient
	 */
	public AuthClient findById(String idUsuario) {

		return colleccionAuthClientes.find(eq("_id", idUsuario)).first();

	}
	
	/**
	 * Metodo de registro de Cliente Api Divisas
	 * @param client - Cliente
	 * @return InsertOneResult
	 */
	public InsertOneResult insertClient(AuthClient client) {

		return colleccionAuthClientes.insertOne(client);

	}

}
