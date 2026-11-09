package com.baz.divisas.api.tef.competencias.dao;


import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.tef.sucursales.model.Sucursal;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.Sorts;


/**
 * The Class CargaCompetenciaDao.
 *
 * @author 1023825
 */
@Repository
public class CargaCompetenciaDao {

	private FactoryMongoDB factoryDB;

	private MongoDatabase terminalFinancieroBD;

	private MongoCollection<Document> coleccionCompetencias;

	private MongoCollection<Sucursal> coleccionSucursal;

	/**
	 * Constructor de la clase
	 */
	public CargaCompetenciaDao() {
		super();
		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		
		factoryDB = FactoryMongoDB.getInstance();
		terminalFinancieroBD = factoryDB.getMongoClient().getDatabase(PropBD.getTerminalFinancieroBD());

		coleccionCompetencias = terminalFinancieroBD.getCollection(PropBD.getCompetenciasPromoEspacio());

		coleccionSucursal = terminalFinancieroBD.getCollection(PropBD.getSucursales(), Sucursal.class)
				.withCodecRegistry(pojoCodecRegistry);

	}

	/**
	 * Constructor de la clase.
	 *
	 * @param coleccion - Mock Collection
	 * @param coleccionSuc - Mock Collection
	 */
	public CargaCompetenciaDao(MongoCollection<Document> coleccion, MongoCollection<Sucursal> coleccionSuc) {
		super();
		this.coleccionCompetencias = coleccion;
		this.coleccionSucursal = coleccionSuc;
	}
	/**
	 * Busca competencias.
	 *
	 * @param fecha the fecha
	 * @return the list
	 */
	public List<Document> buscaCompetencias(String fecha){


		String fechaInicio = fecha.replace("\"", "") + "T00:00:00.000Z";
		String fechaFin = fecha.replace("\"", "") + "T23:59:59.999Z";

		Instant instantNow = Instant.parse(fechaInicio);
		Date timestampNow = Date.from(instantNow);

		Instant instantTomorrow = Instant.parse(fechaFin);
		Date timestampTomorrow = Date.from(instantTomorrow);

		Document match = new Document();
		match.append("fecha_registro", new Document("$gte",timestampNow).append("$lte",
				timestampTomorrow));

		return coleccionCompetencias.aggregate(Arrays.asList(match(match), unwind("$resultado")))
				.into(new ArrayList<Document>());
	}

	/**
	 * Busca competencia agrupadas.
	 *
	 * @return Competencias
	 */
	public List<Document> buscaCompetenciaAgrupadas(){

		Document project = new Document();

		project.append("_id", 0).append("tipo.nombreCompetencia", "$resultado.negociocompetencia");

		Document projectGroup = new Document();

		projectGroup.append("count", 0).append("_id", 0);

		Document count = new Document();
		count.append("$sum", 1);

		return coleccionCompetencias.aggregate(Arrays.asList(
				unwind("$resultado"),
				project(project),
				group(new Document("nombreCompetencia","$tipo.nombreCompetencia"),Accumulators.sum("count", 1)),
				addFields(new Field<>("nombreCompetencia", "$_id.nombreCompetencia")),
				project(projectGroup)
				)).into(new ArrayList<Document>());

	}

	/**
	 * Busca sucursales.
	 *
	 * @param competencias the competencias
	 * @return the list
	 */
	public List<Sucursal> buscaSucursales(Iterable<Document> competencias){

		List<Integer>listaSucursal = new ArrayList<>();
		for (Document listDocument : competencias) {
			Document resultado= (Document) listDocument.get("resultado");
			listaSucursal.add(Integer.valueOf(resultado.get("idSucursal").toString()));
		}

		return coleccionSucursal.aggregate(Arrays.asList(match(in("sucursal", listaSucursal))))
				.into(new ArrayList<Sucursal>());

	}


	/**
	 * Busca sucursales CeCo.
	 *
	 * @param listaCeCo - CeCos
	 * @param fecha - Fecha
	 * @return the list
	 */
	public List<Document> buscaSucursalesCeCo(List<Integer> listaCeCo, String fecha){

		String fechaInicio = fecha.replace("\"", "") + "T00:00:00.000Z";
		String fechaFin = fecha.replace("\"", "") + "T23:59:59.999Z";

		Instant instantNow = Instant.parse(fechaInicio);
		Date timestampNow = Date.from(instantNow);

		Instant instantTomorrow = Instant.parse(fechaFin);
		Date timestampTomorrow = Date.from(instantTomorrow);

		Document filterFecha = new Document();

		filterFecha.append("fecha_registro", new Document("$gte", timestampNow).append("$lte", timestampTomorrow));


		Document filtroId = new Document();
		filtroId.append("competencias.sucursal", new Document("$in", listaCeCo));

		Document project = new Document();

		project.append("_id", 0).append("id", "$_id").
		append("idSucursal", "$resultado.idSucursal").
		append("tipo.nombreCompetencia" , "$resultado.negociocompetencia").
		append("estado" , "$resultado.estado").
		append("ciudad" , "$resultado.ciudad").
		append("municipio" , "$competencias.nomMunicipio").
		append("tipo.dolarCompra", "$resultado.dolarCompra").
		append("tipo.dolarVenta" , "$resultado.dolarVenta").
		append("tipo.canadienseCompra" , "$resultado.canadienseCompra").
		append("tipo.canadienseVenta" , "$resultado.canadienseVenta").
		append("tipo.euroCompra" , "$resultado.euroCompra").
		append("tipo.euroVenta" , "$resultado.euroVenta");

		Document projectGrup = new Document();

		projectGrup.append("_id",0).append("id","$_id.id").append("sucursal","$_id.sucursal").
		append("estado","$_id.estado").append("municipio","$_id.municipio").
		append("ciudad","$_id.ciudad").append("competencias","$competencias.tipo");

		return coleccionCompetencias.aggregate(Arrays.asList(	
				match(filterFecha),
				unwind("$resultado"),
				lookup("sucursales","resultado.idSucursal", "sucursal", "competencias"),
				unwind("$competencias"),
				match(filtroId),
				project(project),
				sort(Sorts.descending("sucursal")))).into(new ArrayList<Document>());
	}	


	/**
	 * Busca sucursales sin CeCo.
	 *
	 * @param listaCeCo - CeCos
	 * @param fecha - Fecha
	 * @return the list
	 */
	public List<Document> buscaSucursalesSinCeCo(Iterable<Document> listaCeCo, String fecha){

		Document filtroId = new Document();
		List<Integer>listaSucursal = new ArrayList<>();
		for (Document listDocument : listaCeCo) {
			listaSucursal.add(Integer.valueOf(listDocument.get("idSucursal").toString()));
		}

		filtroId.append("resultado.idSucursal", new Document("$in", listaSucursal));

		String fechaInicio = fecha.replace("\"", "") + "T00:00:00.000Z";
		String fechaFin = fecha.replace("\"", "") + "T23:59:59.999Z";

		Instant instantNow = Instant.parse(fechaInicio);
		Date timestampNow = Date.from(instantNow);

		Instant instantTomorrow = Instant.parse(fechaFin);
		Date timestampTomorrow = Date.from(instantTomorrow);

		Document filterFecha = new Document();

		filterFecha.append("fecha_registro", new Document("$gte", timestampNow).append("$lte", timestampTomorrow));

		Document project = new Document();

		project.append("_id", 0).append("id", "$_id").
		append("idSucursal", "$resultado.idSucursal").
		append("tipo.nombreCompetencia" , "$resultado.negociocompetencia").
		append("estado" , "$resultado.estado").
		append("ciudad" , "$resultado.ciudad").
		append("tipo.dolarCompra", "$resultado.dolarCompra").
		append("tipo.dolarVenta" , "$resultado.dolarVenta").
		append("tipo.canadienseCompra" , "$resultado.canadienseCompra").
		append("tipo.canadienseVenta" , "$resultado.canadienseVenta").
		append("tipo.euroCompra" , "$resultado.euroCompra").
		append("tipo.euroVenta" , "$resultado.euroVenta");

		Document projectGrup = new Document();

		projectGrup.append("_id",0).append("id","$_id.id").append("idSucursal","$_id.idSucursal").
		append("estado","$_id.estado").
		append("ciudad","$_id.ciudad").append("competencias","$competencias.tipo");

		return coleccionCompetencias.aggregate(Arrays.asList(	
				match(filterFecha),
				unwind("$resultado"),
				match(filtroId),
				project(project) 
				,group(new Document("id","$id").append("idSucursal","$idSucursal").append("estado","$estado").
						append("ciudad","$ciudad"), push("competencias", "$$ROOT")),
				project(projectGrup),
				sort(Sorts.descending("idSucursal")))).into(new ArrayList<Document>());
	}	

}
