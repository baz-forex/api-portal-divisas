package com.baz.divisas.general.factory;

import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.util.LogApp;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class FactoryMongoDB {
	
	private static FactoryMongoDB instance;
	private MongoClient mongoClient;

	public FactoryMongoDB() {
		this.mongoClient = MongoClients.create(new ConnectionString(PropBD.getUriMongoDB()));
	}
	private static synchronized void createInstance() {
		if (instance == null) {
			try {
			
				instance = new FactoryMongoDB();
				instance.getMongoClient().getClusterDescription();

			} 
			catch (RuntimeException e) {
				LogApp.excepcion(e, "Ocurrio un error al crear la conexion a BD");
			}
		}
	}

	public static FactoryMongoDB getInstance() {
		createInstance();
		return instance;
	}

	public MongoClient getMongoClient() {
		return mongoClient;
	}

	public void setMongoClient(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	public static void validaConexionMongo() {
		getInstance().getMongoClient().getClusterDescription().toString();
	}
}
