package com.baz.divisas.api.usuarios.dao;

import com.baz.divisas.api.usuarios.model.Usuario;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.baz.divisas.general.util.LogApp;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.UpdateResult;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import static org.bson.codecs.configuration.CodecRegistries.*;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.stereotype.Repository;

/**
 * DAO Usuarios
 * 
 * @author B813196
 *
 */
@Repository
public class UsuariosDao {

	private CodecRegistry pojoCodecRegistry;

	private FactoryMongoDB factoryDB;
	private MongoDatabase authBD;
	@Valid
	private MongoCollection<Usuario> coleccionUsuarios;

	/**
	 * Constructor de la clase
	 */
	public UsuariosDao() {

		pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
							fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		factoryDB = FactoryMongoDB.getInstance();
		authBD = factoryDB.getMongoClient().getDatabase(PropBD.getAuthBD());
		coleccionUsuarios = authBD.getCollection(PropBD.getUsuariosColeccion(), Usuario.class)
								  .withCodecRegistry(pojoCodecRegistry);
	}

	/**
	 * Metodo de consulta de usuarios

	 * @return Usuarios
	 */
	public List<Usuario> findAll() {
		
		List<Usuario> usuarios = new ArrayList<>();

		try {

			usuarios = coleccionUsuarios.find().projection(Projections.exclude("modulos"))
										.sort(Sorts.ascending("_id")).into(new ArrayList<Usuario>());

		} 
		catch (MongoException e) {
			LogApp.excepcion(e, "Ocurrio un error al realizar la consulta de usuarios");
		}

		return usuarios;
	}
	
	/**
	 * Metodo de consulta de usuario por id
	 * 
	 * @param idUsuario
	 *            - Identificador de usuario
	 * @return Usuario
	 */
	public Usuario findById(String idUsuario) {
		Usuario usuario = null;

		try {

			usuario = coleccionUsuarios.find(eq("_id", idUsuario)).first();

		} 
		catch (MongoException e) {
			LogApp.excepcion(e, "Ocurrio un error al realizar la consulta del usuario");
		}

		return usuario;
	}

	/**
	 * Metodo de registro de usuario
	 * @param usuario - Nuevo usuario
	 * @return Resultado
	 */
	public boolean insertUsuario(@Valid Usuario usuario) {

		try {

			coleccionUsuarios.insertOne(usuario);

		} 
		catch (MongoException e) {
			LogApp.excepcion(e, "Ocurrio un error al intentar insertar el usuario");
			return false;
		}

		return true;

	}
	
	/**
	 * Metodo de actualizacion de usuario
	 * @param usuario - Usuario
	 * @return Resultado
	 */
	public boolean updateUsuario(@Valid Usuario usuario) {

		try {

			UpdateResult result = coleccionUsuarios.updateOne(
											   eq("_id", usuario.getId()),
											   set("modulos", usuario.getModulos()));
			
			if(result.getMatchedCount() == 0) {
				return false;
			}
		} 
		catch (MongoException e) {
			LogApp.excepcion(e, "Ocurrio un error al intentar actualizar el usuario");
			return false;
		}

		return true;

	}

}
