package com.baz.divisas.api.usuarios.dao;

import static com.mongodb.client.model.Filters.in;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.usuarios.model.Nodo;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.baz.divisas.general.util.LogApp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

/**
 * DAO Modulos
 * @author B813196
 *
 */
@Repository
public class ModulosDao {

	private FactoryMongoDB factoryDB;
	private MongoDatabase  authBD;
	private MongoCollection<Document>  coleccionModulos;

	/**
	 * Constructor de la clase
	 */
	public ModulosDao() {
		factoryDB = FactoryMongoDB.getInstance();
		authBD = factoryDB.getMongoClient().getDatabase(PropBD.getAuthBD());
		coleccionModulos =  authBD.getCollection(PropBD.getModulosColeccion());
	}

	/**
	 * Metodo de consulta de modulos por ids
	 * @param idsModulos - Lista de ids de modulos a consultar
	 * @return - Lista de modulos
	 */
	public List<Nodo> getModulosById(List<String> idsModulos){

		ArrayList<Nodo> modulos = new ArrayList<>();
		Gson gson = new Gson();

		try{					

			ArrayList<Document> resultado = coleccionModulos.find(in("_id", idsModulos))
															.sort(Sorts.ascending("nivel","orden")).into(new ArrayList<Document>());
			modulos = gson.fromJson(gson.toJson(resultado), new TypeToken<ArrayList<Nodo>>(){}.getType());		

		}
		catch(MongoException e){
			LogApp.excepcion(e, "Ocurrio un error en la consulta de modulos por id");
		}

		return modulos;
	}

	/**
	 * Metodo de consulta de modulos existentes
	 * @return Lista de modulos
	 */
	public List<Nodo> getModulos(){

		ArrayList<Nodo> modulos = new ArrayList<>();
		Gson gson = new Gson();

		try{

			ArrayList<Document> resultModulos = coleccionModulos.find()
																.sort(Sorts.ascending("nivel","orden")).into(new ArrayList<Document>());
			modulos = gson.fromJson(gson.toJson(resultModulos), new TypeToken<ArrayList<Nodo>>(){}.getType());

		}
		catch(MongoException e) {
			LogApp.excepcion(e, "Ocurrio un error en la consulta de modulos");
		}

		return modulos;
	}

}
