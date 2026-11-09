package com.baz.divisas.api.core.reporteria.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.core.reporteria.dto.ReqBusquedaIncidencias;
import com.baz.divisas.api.core.reporteria.model.Incidencia;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.baz.divisas.general.util.LogApp;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Clase ReporteriaDao
 * 
 * @author 1027346 Martinez Yonathan
 *
 */

@Repository
public class IncidenciasDao {

	private FactoryMongoDB factoryDB;
	private MongoDatabase incidenciasBD;
	private MongoCollection<Document> coleccionIncidencias;
	private static final String FECHA_INCIDENCIA = "fecha_incidencia";
	private static final String MATCH = "$match";
	private static final String UNWIND = "$unwind";
	private Gson gson;

	/**
	 * Constructor de la clase
	 * 
	 */
	public IncidenciasDao() {
		super();
		gson = new Gson();
		factoryDB = FactoryMongoDB.getInstance();
		incidenciasBD = factoryDB.getMongoClient().getDatabase(PropBD.getIncidenciasBD());
		coleccionIncidencias = incidenciasBD.getCollection(PropBD.getIncidenciasBCDColeccion());
	}
	
	/**
	 * Constructor de la clase.
	 *
	 * @param coleccion - Mock Collection
	 */
	public IncidenciasDao(MongoCollection<Document> coleccion) {
		super();
		gson = new Gson();
		coleccionIncidencias = coleccion;
	}

	/**
	 * 
	 * Obtiene las incidencias por tipo en un rango de fechas
	 * 
	 * @param reqBusquedaIncidencias Objeto con los parametros de busqueda
	 * @param isDescarga - Bandera que indica si es descarga de informacion
	 * 
	 * @return Lista de incidencias
	 */
	public List<Incidencia> consulta(@Valid ReqBusquedaIncidencias reqBusquedaIncidencias, boolean isDescarga) {

		ArrayList<Incidencia> listaIncidencias = new ArrayList<>();

		List<Document> listDocs = null;
		Document filtro = new Document();
		Document sortlist = new Document();
		Document project = new Document();

		filtro.append(FECHA_INCIDENCIA, new Document("$elemMatch",
										new Document("$gte", reqBusquedaIncidencias.getFechaInicial())
											 .append("$lte", reqBusquedaIncidencias.getFechaFinal())));
		filtro.append("tipo_incidencia", reqBusquedaIncidencias.getTipoIncidencia());

		sortlist.append(FECHA_INCIDENCIA, 1).append("datos_busqueda.nombre", 1).append("_id", 1);

		if (!isDescarga) {
			
			project.append("_id", 1).append(FECHA_INCIDENCIA, 1).append("datos_busqueda.nombre", 1)
					.append("datos_busqueda.apellido_paterno", 1).append("datos_busqueda.apellido_materno", 1)
					.append("datos_busqueda.fecha_nacimiento", 1);
			
			listDocs = coleccionIncidencias
					.aggregate(Arrays.asList(new Document(MATCH, filtro), new Document(UNWIND, "$"+FECHA_INCIDENCIA),
							new Document(MATCH,
									new Document(FECHA_INCIDENCIA,
											new Document("$gte", reqBusquedaIncidencias.getFechaInicial()).append("$lte",
													reqBusquedaIncidencias.getFechaFinal()))),
							new Document("$project", project), new Document("$sort", sortlist)))
					.allowDiskUse(true).into(new ArrayList<Document>());
		} 
		else {
			
			listDocs = coleccionIncidencias.aggregate(Arrays.asList(new Document(MATCH, filtro),
					new Document(UNWIND, "$fecha_incidencia"),
					new Document(MATCH,
							new Document(FECHA_INCIDENCIA,
									new Document("$gte", reqBusquedaIncidencias.getFechaInicial()).append("$lte",
											reqBusquedaIncidencias.getFechaFinal()))),
					new Document("$sort", sortlist))).allowDiskUse(true).into(new ArrayList<Document>());
		}
		
		for (Document row : listDocs) {
			Incidencia incidencias = gson.fromJson(gson.toJson(row), Incidencia.class);
			incidencias.setId(row.getObjectId("_id").toHexString());
			listaIncidencias.add(incidencias);
		}

		return listaIncidencias;
	}

	/**
	 * 
	 * Obtiene detalle de las incidencia por id
	 * 
	 * @param id - id Objeto de la incidencia
	 * 
	 * @return Lista de incidencias
	 */
	public Incidencia consultaPorId(String id) {

		Incidencia incidencia = null;

		ObjectId objectId = null;
		// Busqueda detalle de incidencia por _id
		try {
			objectId = new ObjectId(id);
		} 
		catch (IllegalArgumentException e) {
			LogApp.excepcion(e, "El id de la incidencia es un argumento no valido.");
			return incidencia;
		}
		
		List<Document> listDocs = coleccionIncidencias
											 .aggregate(Arrays.asList(
														new Document(MATCH, new Document("_id", objectId)),
														new Document(UNWIND, "$"+FECHA_INCIDENCIA)))
											 .allowDiskUse(true).into(new ArrayList<Document>());
		
		for(Document row : listDocs) {
			String json = gson.toJson(row);
			incidencia = gson.fromJson(json, Incidencia.class);
		}
	
		return incidencia;
	}
}
