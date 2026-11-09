package com.baz.divisas.api.core.configuraciones.dao;

import static com.mongodb.client.model.Aggregates.addFields;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Aggregates.sort;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Filters.or;
import static com.mongodb.client.model.Projections.computed;
import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Updates.addToSet;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.pull;
import static com.mongodb.client.model.Updates.set;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.catalogos.model.CatPaisNacionalidad;
import com.baz.divisas.api.core.configuraciones.model.CampoRestringido;
import com.baz.divisas.api.core.configuraciones.model.Notificacion;
import com.baz.divisas.api.core.configuraciones.model.ParametriaBusquedas;
import com.baz.divisas.api.core.configuraciones.model.SegmentoSucursal;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.config.PropBDCatalogos;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.google.gson.Gson;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;


/**
 * Clase ConfiguracionesDao
 * 
 * @author B813196
 * itoxtle
 *
 */
@Repository
public class ConfiguracionesDao {
	
	private FactoryMongoDB factoryDB;
	private MongoDatabase middlewareBD;
	private MongoDatabase  catalogosBD;
	private MongoCollection<Document> coleccionNegociosMW;
	private MongoCollection<Notificacion> coleccionNotificacionesMW;
	private MongoCollection<Document> coleccionBitacoraNotificacionesMW;
	private MongoCollection<CampoRestringido> coleccionDiccionario;
	private MongoCollection<SegmentoSucursal> coleccionSegmentacionSucursal;
	private MongoCollection<Document> collectionCatNacionalidades;
	private MongoCollection<Document>collectionCatIdentificaciones;
	private MongoCollection<CatPaisNacionalidad> collectionCatNacionalidadesPojo;
	
	private CodecRegistry pojoCodecRegistry;
	
	private static final String PALABRAS_RESTRINGIDAS = "palabras_restringidas";
	private static final String ACTIVO = "activo";
	private static final String FECHAINICIO = "fechaInicio";
	private static final String FECHAFIN = "fechaFin";
	private static final String SUCURSAL = "sucursales";
	
	/**
	 * Constructor de la clase
	 */
	public ConfiguracionesDao() {
		super();		
		pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
							CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		
		factoryDB = FactoryMongoDB.getInstance();
		
		middlewareBD = factoryDB.getMongoClient().getDatabase(PropBD.getMiddlewareBD());
		coleccionNegociosMW = middlewareBD.getCollection(PropBD.getNegociosMW());
		coleccionNotificacionesMW =  middlewareBD.getCollection(PropBD.getNotificacionesMW(),
												 Notificacion.class).withCodecRegistry(pojoCodecRegistry);		
		coleccionBitacoraNotificacionesMW = middlewareBD.getCollection(PropBD.getBitacoraNotificacionesMW());
		catalogosBD = factoryDB.getMongoClient().getDatabase(PropBDCatalogos.getCatalogosBD());
		coleccionDiccionario =  catalogosBD.getCollection(PropBDCatalogos.getDiccionarioColeccion(),
											CampoRestringido.class).withCodecRegistry(pojoCodecRegistry);
		coleccionSegmentacionSucursal = catalogosBD.getCollection(PropBDCatalogos.getCatSegmentacionSucursalColeccion(), 
													SegmentoSucursal.class).withCodecRegistry(pojoCodecRegistry);
		collectionCatNacionalidades = catalogosBD.getCollection(PropBDCatalogos.getCatNacionalidadesColeccion());
		
		collectionCatIdentificaciones = catalogosBD.getCollection(PropBDCatalogos.getCatIdentificaciones());
		
		collectionCatNacionalidadesPojo = catalogosBD.getCollection(PropBDCatalogos.getCatNacionalidadesColeccion(),CatPaisNacionalidad.class)
		        .withCodecRegistry(pojoCodecRegistry);

		
	}
		
	
	/**
	 * Metodo de consulta de parametria de busquedas
	 * 
	 * @return - ParametriaBusquedas
	 */
	public ParametriaBusquedas findParametriaBusquedas() {
		
		ParametriaBusquedas parametria = null;

		Document resultado = coleccionNegociosMW.aggregate(Arrays.asList(
										match(eq("_id", "DIV")),
										project(fields(												
										   excludeId(),
										   computed("busquedaClientes.limiteCoincidenciaNombre", "$porcentajes_BCD.limiteCoincidenciaNombreCD"),
										   computed("busquedaClientes.precisionNombre", "$porcentajes_BCD.precisionBusquedaCD"),
										   computed("busquedaClientes.valorPrimarioNombres","$porcentajes_BCD.prioridadNombresC1CD"),
										   computed("busquedaClientes.valorPrimarioApellidos", "$porcentajes_BCD.prioridadApellidosC1CD"),
										   computed("busquedaClientes.valorSecundarioNombres", "$porcentajes_BCD.prioridadNombresC2CD"),
										   computed("busquedaClientes.valorSecundarioApellidos", "$porcentajes_BCD.prioridadApellidosC2CD"),
										   computed("busquedaListaNegra.limiteCoincidenciaNombre", "$porcentajes_BCD.limiteCoincidenciaNombreLN"),
										   computed("busquedaListaNegra.precisionNombre", "$config_lista_negra.precision_minima_busqueda"),						
										   computed("busquedaListaNegra.valorPrimarioNombres", "$porcentajes_BCD.prioridadNombresLNC1"),
										   computed("busquedaListaNegra.valorPrimarioApellidos", "$porcentajes_BCD.prioridadApellidosLNC1"),
										   computed("busquedaListaNegra.valorSecundarioNombres", "$porcentajes_BCD.prioridadNombresLNC2"),
										   computed("busquedaListaNegra.valorSecundarioApellidos", "$porcentajes_BCD.prioridadApellidosLNC2"),
										   computed("penalizaciones.fechaNacimiento", "$config_lista_negra.penalizacion_fecha_nacimiento"),
										   computed("penalizaciones.desorden", "$porcentajes_BCD.penalizacionDesorden"),
										   computed("penalizaciones.parametroFaltante", "$porcentajes_BCD.penalizacionSinParametro")))
									 )).first();
		
		if(resultado != null) {			
			Gson gson = new Gson();
			String docJson = gson.toJson(resultado);
			parametria = gson.fromJson(docJson, ParametriaBusquedas.class);
		}
		
		return parametria;
	}
	
	
	/**
	 * Metodo de actualizacion de parametria de busquedas
	 * @param parametria - ParametriaBusquedas
	 * @return resultado
	 */
	public boolean updateParametriaBusquedas(ParametriaBusquedas parametria) {
		
		UpdateResult resultado = coleccionNegociosMW.updateOne(eq("_id", "DIV"),
						combine(
						set("porcentajes_BCD.limiteCoincidenciaNombreCD", parametria.getBusquedaClientes().getLimiteCoincidenciaNombre()),
						set("porcentajes_BCD.precisionBusquedaCD", parametria.getBusquedaClientes().getPrecisionNombre()),
						set("porcentajes_BCD.prioridadNombresC1CD", parametria.getBusquedaClientes().getValorPrimarioNombres()),
						set("porcentajes_BCD.prioridadApellidosC1CD", parametria.getBusquedaClientes().getValorPrimarioApellidos()),
						set("porcentajes_BCD.prioridadNombresC2CD", parametria.getBusquedaClientes().getValorSecundarioNombres()),
						set("porcentajes_BCD.prioridadApellidosC2CD", parametria.getBusquedaClientes().getValorSecundarioApellidos()),
						set("porcentajes_BCD.limiteCoincidenciaNombreLN",parametria.getBusquedaListaNegra()
																				   .getLimiteCoincidenciaNombre()),
						set("config_lista_negra.precision_minima_busqueda", parametria.getBusquedaListaNegra().getPrecisionNombre()),
						set("config_lista_negra.penalizacion_fecha_nacimiento", parametria.getPenalizaciones().getFechaNacimiento()),
						set("porcentajes_BCD.prioridadNombresLNC1", parametria.getBusquedaListaNegra().getValorPrimarioNombres()),
						set("porcentajes_BCD.prioridadApellidosLNC1", parametria.getBusquedaListaNegra().getValorPrimarioApellidos()),
						set("porcentajes_BCD.prioridadNombresLNC2", parametria.getBusquedaListaNegra().getValorSecundarioNombres()),
						set("porcentajes_BCD.prioridadApellidosLNC2", parametria.getBusquedaListaNegra().getValorSecundarioApellidos()),
						set("porcentajes_BCD.penalizacionDesorden", parametria.getPenalizaciones().getDesorden()),
						set("porcentajes_BCD.penalizacionSinParametro", parametria.getPenalizaciones().getParametroFaltante())));
		
		return resultado.wasAcknowledged();
	}
	
	
	/**
	 * Metodo de consulta de Campos Restringidos
	 * @return Lista de campos restringidos
	 */
	public List<CampoRestringido> findCamposRestringidos(){
				
		return coleccionDiccionario.find(eq(ACTIVO, true))
								   .projection(exclude(PALABRAS_RESTRINGIDAS))
								   .into(new ArrayList<CampoRestringido>());	
	}
	
	
	/**
	 * Metodo de consulta de Campo Restringido por Id
	 * @param idCampo - Identificador de campo
	 * @return Campos restringido
	 */
	public CampoRestringido findCamposRestringidoById(String idCampo){
		
		return coleccionDiccionario.find(eq("_id", idCampo)).first();

	}
	
	   /**
     * Metodo de consulta de Campo de la identidad por Id
     * @param idIdentificacion - id de la identificacion
     * @return  Document
     */
    public Document findIdIdentificacion(String idIdentificacion){
        
        return collectionCatIdentificaciones.find(eq("id_cliente_amigo", idIdentificacion)).first();

    }
	
	/**
	 * Metodo para agregar una cadena restringida al campo
	 * @param idCampo - Identificador de campo
	 * @param restriccion - Cadena restringida
	 * @return Campos restringido
	 */
	public UpdateResult addRestriccionCampo(String idCampo, String restriccion){
				
		return coleccionDiccionario.updateOne(eq("_id", idCampo), 
											  addToSet(PALABRAS_RESTRINGIDAS, restriccion));

	}
	
	
	/**
	 * Metodo para agregar una cadena restringida al campo
	 * @param idCampo - Identificador de campo
	 * @param restriccion - Cadena restringida
	 * @return Campos restringido
	 */
	public UpdateResult removeRestriccionCampo(String idCampo, String restriccion){
				
		return coleccionDiccionario.updateOne(eq("_id", idCampo), 
											  pull(PALABRAS_RESTRINGIDAS, restriccion));

	}
	
	
	/**
	 * Metodo que actualiza las restricciones de Paises o Nacionalidades
	 * @param paisNacionalidad - Pais o Nacionalidad
	 * @return Paise Restringido
	 */
	public CatPaisNacionalidad updateRestriccionPaisNacionalidad(CatPaisNacionalidad paisNacionalidad) {
							
		CatPaisNacionalidad paisNacionalidadAnterior = null;
		
		FindOneAndUpdateOptions optionBefore = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.BEFORE);
		
		Document resultado = collectionCatNacionalidades.findOneAndUpdate(eq("_id", paisNacionalidad.getId() ), 
									        	  					   combine(set("restriccion_pais", paisNacionalidad.isRestriccionPais()),
									        	  						       set("restriccion_nacionalidad", paisNacionalidad.isRestriccionNacionalidad()),
									        	  						       set("restriccion_exclusion.identificacion.primaria", paisNacionalidad.getRestriccionExclusion().getIdentificacion().getPrimaria()),
									        	  						       set("restriccion_exclusion.identificacion.adicional", paisNacionalidad.getRestriccionExclusion().getIdentificacion().getAdicional())),
									        	  					   optionBefore
									        	  					  
									        	  					);
		
		if(resultado != null) {
			
			Gson gson = new Gson();
			paisNacionalidadAnterior = gson.fromJson(gson.toJson(resultado), CatPaisNacionalidad.class);
			
		}
		
		return paisNacionalidadAnterior;

	}
	
	
	   /**
     * Metodo que actualiza las restricciones de Paises o Nacionalidades
     * @param paisNacionalidad - Pais o Nacionalidad
     * @return Paise Restringido
     */
    public CatPaisNacionalidad buscaPaisRestriccion(CatPaisNacionalidad paisNacionalidad) {
                            
        return collectionCatNacionalidadesPojo.find(eq("_id", paisNacionalidad.getId())).first();

    }
	
	
	
	
	/**
	 * Metodo de consulta de segmentos por Tipo
	 * @param tipoSegmento - Tipo del segmento de sucursales
	 * @return Segmentos ListaSucursales
	 */
	public List<SegmentoSucursal> findSegmentosSucursalByTipo(String tipoSegmento){
		
		return coleccionSegmentacionSucursal.find(eq("tipo", tipoSegmento))
											.into(new ArrayList<SegmentoSucursal>());
	}
	
	
	/**
	 * Metodo de consulta de segmento por Id
	 * @param idSegmento - Id del segmento de sucursales
	 * @return Segmentos ListaSucursales
	 */
	public SegmentoSucursal findSegmentoSucursalById(String idSegmento){

		return coleccionSegmentacionSucursal.find(eq("_id", new ObjectId(idSegmento))).first();

	}
	
	
	/**
	 * Metodo de consulta de segmento tipo y id
	 * @param tipo     - Tipo del segmento de sucursales
	 * @param idRegion - Id Region del segmento de sucursales
	 * @return Segmentos ListaSucursales
	 */
	public SegmentoSucursal findSegmentoSucursalByIdRegion(String tipo, String idRegion){

		return coleccionSegmentacionSucursal.find(and(eq("tipo", tipo), eq("id_region", idRegion))).first();

	}
	
	
	/**
	 * Metodo de registro de segmento
	 * @param segmento - Segmento a registrar
	 * @return InsertOneResult
	 */
	public InsertOneResult insertSegmentoSucursal(SegmentoSucursal segmento){

		return coleccionSegmentacionSucursal.insertOne(segmento);

	}
	
	
	/**
	 * Metodo de actualización de segmento
	 * @param segmento - Segmento a actualizar
	 * @return - UpdateResult
	 */
	public UpdateResult updateSegmentoSucursal(SegmentoSucursal segmento){

		return coleccionSegmentacionSucursal.replaceOne(eq("_id", new ObjectId(segmento.getId())), segmento);

	}
	
	
	/**
	 * Metodo de actualización de estatus del segmento
	 * @param idSegmento - Id del Segmento a actualizar
	 * @param activo - Estatus
	 * @return - UpdateResult
	 */
	public UpdateResult updateEstatusSegmentoSucursal(String idSegmento, boolean activo){

		return coleccionSegmentacionSucursal.updateOne(eq("_id", new ObjectId(idSegmento)), set(ACTIVO, activo));

	}
	
	
	/**
	 * Metodo de eliminación de degmento
	 * @param idSegmento - Identificador del segmento a eliminar
	 * @return SegmentoSucursal
	 */
	public SegmentoSucursal removeSegmentoSucursal(String idSegmento){
		
		return coleccionSegmentacionSucursal.findOneAndDelete(eq("_id", new ObjectId(idSegmento)));

	}
	
	
	/**
	 * Metodo de consulta de Notificaciones por estatus
	 * @param estatus - Estatus de la Notificacion
	 * @param sucursal - Sucursal de la Notificacion
	 * @return Notificaciones
	 */
	public List<Notificacion> findNotificaciones(String estatus, String sucursal) {

		List<Notificacion> notificaciones = new ArrayList<>();
		LocalDate fechaActual = LocalDate.now();
		List<Bson> andFilters = new ArrayList<>();

		switch(estatus){

		case "VIGENTES":
        andFilters.add(lte(FECHAINICIO, fechaActual));
        andFilters.add(gte(FECHAFIN, fechaActual));
        estatus = "Vigente";
        break;

		case "NO_VIGENTES":
        andFilters.add(lt(FECHAFIN, fechaActual));
        estatus = "No Vigente";
        break;

		case "PROXIMAS":
        andFilters.add(gt(FECHAINICIO, fechaActual));
        estatus = "Próxima";
        break;

		default:
        return notificaciones;

		}
		
		if(sucursal != null && !sucursal.isEmpty()) {
			andFilters.add(or(eq("generalSucursales", true), in(SUCURSAL, sucursal)));
		}
		
		notificaciones = coleccionNotificacionesMW.aggregate(Arrays.asList( 
														match( and(andFilters) ), 
														project(fields(exclude(SUCURSAL, "imagen"))),
														addFields(new Field<>("estatus", estatus)),
												        sort(Sorts.ascending("fechaRegistro"))
													    )).into(new ArrayList<Notificacion>());
				
		return notificaciones;
	}

	
	/**
	 * Metodo de consulta de notificacion por Id
	 * @param idNotificacion - Identificador de la notificacion
	 * @param excludeFields - Campos excluidos en la consulta
	 * @return Notificacion
	 */
	public Notificacion findNotificacionById(String idNotificacion, List<String> excludeFields){

		Notificacion notificacion = coleccionNotificacionesMW.find(eq("_id", new ObjectId(idNotificacion)))
															 .projection(Projections.exclude(excludeFields)).first();
		
		if(notificacion != null) {
			notificacion.setEstatusNotificacion();
		}
		
		return notificacion;

	}
	
	
	/**
	 * Metodo de registro de Notificacion
	 * @param notificacion - Notificacion
	 * @return InsertOneResult
	 */
	public InsertOneResult insertNotificacion(Notificacion notificacion){

		return coleccionNotificacionesMW.insertOne(notificacion);

	}
	
	
	/**
	 * Metodo de actualizacion de Notificacion
	 * @param notificacion - Notificacion
	 * @param imagenActual - Imagen actual de la notificacion
	 * @return UpdateResult
	 */
	public UpdateResult updateNotificacion(Notificacion notificacion){

		UpdateResult result = coleccionNotificacionesMW.updateOne(eq("_id", new ObjectId(notificacion.getId())),
												   combine(
													  set("nombre", notificacion.getNombre()),
													  set(FECHAINICIO, notificacion.getFechaInicio()),
													  set(FECHAFIN, notificacion.getFechaFin()),
													  set("tiempoSegundos", notificacion.getTiempoSegundos()),
													  set(ACTIVO, notificacion.isActivo()),
													  set("generalSucursales", notificacion.isGeneralSucursales()),
													  set(SUCURSAL, notificacion.getSucursales()),
													  set("imagen", notificacion.getImagen())
													 )
												  );
		
		if(result.getMatchedCount() > 0 && result.getModifiedCount() > 0) {
			coleccionNotificacionesMW.updateOne(eq("_id", new ObjectId(notificacion.getId())), 
					set("fechaActualizacion", LocalDateTime.now()));
		}
		
		return result;

	}
	
	
	/**
	 * Metodo de actualización de estatus de la notificacion
	 * @param idNotificacion - Id de la Notificacion
	 * @param activo - Estatus
	 * @return - UpdateResult
	 */
	public UpdateResult updateEstatusNotificacion(String idNotificacion, boolean activo){

		return coleccionNotificacionesMW.updateOne(eq("_id", new ObjectId(idNotificacion)), set(ACTIVO, activo));

	}
	
	
	/**
	 * Metodo de reinicio de visualizaciones de una notificacion
	 * @param idNotificacion - Id de la notificacion
	 * @return DeleteResult
	 */
	public DeleteResult resetNotificacion(String idNotificacion){

		return coleccionBitacoraNotificacionesMW.deleteMany(eq("id_notificacion", new ObjectId(idNotificacion)));

	}

}
