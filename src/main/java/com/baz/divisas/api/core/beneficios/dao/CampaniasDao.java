package com.baz.divisas.api.core.beneficios.dao;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.or;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.pull;
import static com.mongodb.client.model.Updates.push;
import static com.mongodb.client.model.Updates.set;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.core.beneficios.dto.ReqAltaPremio;
import com.baz.divisas.api.core.beneficios.dto.ReqEliminaPremio;
import com.baz.divisas.api.core.beneficios.dto.ReqEstatusCampania;
import com.baz.divisas.api.core.beneficios.dto.ReqEstatusPremio;
import com.baz.divisas.api.core.beneficios.dto.ReqTipoCampania;
import com.baz.divisas.api.core.beneficios.model.BeanCampania;
import com.baz.divisas.api.core.beneficios.model.BeanPremio;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.config.PropCampania;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.baz.divisas.general.model.EntCorreo;
import com.baz.divisas.general.util.DateUtils;
import com.baz.divisas.general.util.MailService;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

/**
 * Conexion a mongo para catalogo de campañas
 * @author B86960
 *
 */
@Repository
public class CampaniasDao {
	//coleccion de mongo
	private MongoCollection<BeanCampania> coleccionCampania;
	//Servicio de correo
	private MailService mail;
	//Propiedades estaticas finales
	private static final String ESTATUS = "estatus";
	private static final String FECHAFIN = "fechaFin";
	private static final String FECHAMODIFICACION = "fecha_modificacion";
	private static final String MOTIVOMODIFICACION = "motivo_Modificacion";
	private static final String ALERTACAMPANIAS = "Alerta de Campañas";
	/**
	 * Constructor
	 */
	public CampaniasDao() {
		super();
		//Se usa pojo para la estructura de la coleccion
		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		//Se obtiene la instancia de la conexion de mongo
		FactoryMongoDB factoryDB = FactoryMongoDB.getInstance();
		//Se obtiene la base de middleware
		MongoDatabase middlewareBD = factoryDB.getMongoClient().getDatabase(PropBD.getMiddlewareBD());
		//Se obtiene la coleccion de campanias con pojo para estructura
		coleccionCampania = middlewareBD.getCollection(PropCampania.getCampaniasColeccion(), 
		BeanCampania.class).withCodecRegistry(pojoCodecRegistry);
		//Se instancia el servicio de correo
		mail = new MailService();
	}
	
	/**
	 * Instantiates a new campanias dao.
	 *
	 * @param coleccion the coleccion
	 * @param mail the mail
	 */
	public CampaniasDao(MongoCollection<BeanCampania> coleccion, MailService mail) {
		//Mock
		coleccionCampania = coleccion;
		//Mock
		this.mail = mail;
	}
	/**
	 * Metodo para insertar campania
	 * @param campania campania
	 */
	public void insert(BeanCampania campania){
		coleccionCampania.insertOne(campania);
	}
	
	/**
	 * Metodo para consultar campanias.
	 *
	 * @return the list
	 */
	public List<BeanCampania> consultar(){
		return coleccionCampania.find().into(new ArrayList<BeanCampania>());
	}
	
	/**
	 * Metodo para consultar campania.
	 *
	 * @param id the id
	 * @return BeanCampania the BeanCampania
	 */
	public BeanCampania consultaUnica(String id){
		return coleccionCampania.find(eq("_id",new ObjectId(id))).first();
	}
	
	
	/**
	 * Metodo para actualizar campanias.
	 *
	 * @param campania the campania
	 */
	public void actualizar(BeanCampania campania){
		coleccionCampania.updateOne(eq("_id",new ObjectId(campania.getId())), eq("$set", campania), 
				new UpdateOptions().upsert(false));
	}
	
	/**
	 * Metodo para eliminar campania.
	 *
	 * @param id the id
	 */
	public void eliminarCampania(String id){
		coleccionCampania.deleteOne(eq("_id",new ObjectId(id)));
	}
	
	/**
	 * Metodo para actualizar tipo campania.
	 *
	 * @param tipoCampania the tipo campania
	 */
	public void actualizarTipo(ReqTipoCampania tipoCampania) {
		coleccionCampania.updateOne(eq("_id",new ObjectId(tipoCampania.getIdCampania())),
		combine(
			set("permanente",tipoCampania.isPermanente()),
			set("unica", tipoCampania.isUnica()),
			set(FECHAMODIFICACION,LocalDateTime.now().toString()),
			set(MOTIVOMODIFICACION,tipoCampania.getMotivo())
		));
		
	}
	/**
	 * Metodo para actualizar estatus campania
	 * @param request request
	 */
	public void actualizarEstatus(ReqEstatusCampania request) {
		coleccionCampania.updateOne(eq("_id",new ObjectId(request.getIdCampania())),
			set(ESTATUS,request.getEstatus())
		);
		
	}
	
	/**
	 * Insert premio.
	 *
	 * @param idCampania the id campania
	 * @param premio the premio
	 */
	public void insertPremio(String idCampania, BeanPremio premio) {
		coleccionCampania.updateOne(eq("_id",new ObjectId(idCampania)), push("premios",premio));
	}
	
	/**
	 * Actualizar premio.
	 *
	 * @param request the request
	 */
	public void actualizarPremio(ReqAltaPremio request) {
		coleccionCampania.updateOne(eq("_id",new ObjectId(request.getIdCampania())), 
		set("premios.$[elem]", request.getPremio()), 
		new UpdateOptions().arrayFilters(Arrays.asList(new Document("elem._id",new ObjectId(request.getPremio().getId())))));
	}
    
	/**
	 * Eliminar premio.
	 *
	 * @param request the request
	 */
	public void eliminarPremio(ReqEliminaPremio request) {
		coleccionCampania.updateOne(eq("_id",new ObjectId(request.getIdCampania())), 
				pull("premios",eq("_id",new ObjectId(request.getIdPremio()))));
	}

	/**
	 * Actualizar estatus premio.
	 *
	 * @param request the request
	 */
	public void actualizarEstatusPremio(ReqEstatusPremio request) {
		coleccionCampania.updateOne(eq("_id",new ObjectId(request.getIdCampania())), 
		set("premios.$[elem].estatus", request.isActivo()), 
		new UpdateOptions().arrayFilters(Arrays.asList(new Document("elem._id",new ObjectId(request.getIdPremio())))));
	}

	/**
	 * Actualiza campanias fuera vigencia.
	 */
	public void actualizaCampaniasFueraVigencia() {
		//Se obtiene la fecha actual
		String fechaActual = DateUtils.getDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		//Se actualiza la vigencia
		UpdateResult resultVigencia = coleccionCampania.updateMany(
				and(or(eq(ESTATUS, "Creada"), eq(ESTATUS, "Pausada"),
						eq(ESTATUS, "Vigente")), lt(FECHAFIN, fechaActual)),
				combine(set(ESTATUS, "Terminada"), set(FECHAMODIFICACION, fechaActual),
						set(MOTIVOMODIFICACION, "Terminada por vigencia.")));
		//Se valida si actualizo por vigencia
		if (resultVigencia.getMatchedCount() > 0 && resultVigencia.getModifiedCount() > 0) {
			// SE NOTIFICA POR CORREO LA FINLIZACION DE N CAMPAÑAS
			EntCorreo correo = new EntCorreo(0, PropCampania.getDestinatariosCampaniasNotificacion(),
					ALERTACAMPANIAS,
					"Se notifica por este medio que han finalizado " + resultVigencia.getModifiedCount()
					+ " campaña(s) por termino de vigencia.");
			//Se envia el correo
			mail.enviaMail(correo);
		}
		
	}
}
