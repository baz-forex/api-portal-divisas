package com.baz.divisas.api.core.clientes.dao;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Updates.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.core.clientes.dto.ReqBloqueoClientes;
import com.baz.divisas.api.core.clientes.model.BitacoraActualizacion;
import com.baz.divisas.api.core.clientes.model.BitacoraCargaMasiva;
import com.baz.divisas.api.core.clientes.model.ClienteAltoRiesgo;
import com.baz.divisas.api.core.clientes.model.HistoricoAltoRiesgo;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.config.PropBDCatalogos;
import com.baz.divisas.general.config.PropClientes;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.DateUtils;
import com.baz.divisas.general.util.LogApp;
import com.google.gson.Gson;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.InsertOneResult;


/**
 * Clase ClientesDao.
 *
 * @author B813196
 * itoxtle
 */
@Repository
public class ClientesDao {

	/** The pojo codec registry. */
	private CodecRegistry pojoCodecRegistry;

	/** The factory DB. */
	private FactoryMongoDB factoryDB;
	
	/** The middleware BD. */
	private MongoDatabase middlewareBD;
	
	/** The catalogos divisas BD. */
	private MongoDatabase catalogosDivisasBD;
	
	/** The coleccion bitacora carga masiva. */
	private MongoCollection<BitacoraCargaMasiva> coleccionBitacoraCargaMasiva;
	
	/** The coleccion bitacora act ctes. */
	private MongoCollection<BitacoraActualizacion> coleccionBitacoraActCtes;
	
	/** The coleccion clientes agrupados. */
	private MongoCollection<Document> coleccionClientesAgrupados;
	
	/** The coleccion cat bloqueos. */
	private MongoCollection<Document> coleccionCatBloqueos;
	
	/** The coleccion carga hist alto riesgo. */
	private MongoCollection<HistoricoAltoRiesgo> coleccionCargaHistAltoRiesgo;
	
	/** The coleccion clientes alto riesgo. */
	private MongoCollection<ClienteAltoRiesgo> coleccionClientesAltoRiesgo;
	
	/** The Constant CLIENTES. */
	private static final String CLIENTES = "clientes";
	
	/** The Constant FECHAACTUALIZACION. */
	private static final String FECHAACTUALIZACION = "fecha_actualizacion";
	
	/** The Constant BLOQUEO_TIPOIDENTIFICACION. */
	private static final String BLOQUEO_TIPOIDENTIFICACION = "datos_bloqueo.tipo_identificacion";

	/** The bitacora. */
	@Autowired
	private BitacoraServiceImpl bitacora;

	/**
	 * Constructor de la clase.
	 *
	 * @param coleccionBitacoraActCtes - MockColeccion
	 * @param coleccionBitacoraCargaMasiva - MockColeccion
	 * @param colectionClientesAgrupados - MockColeccion
	 * @param coleccionCatBloqueos - MockColeccion
	 * @param bitacora - BitacoraService
	 * @param coleccionCargaHistAltoRiesgo the coleccion carga hist alto riesgo
	 * @param coleccionClientesAltoRiesgo the coleccion clientes alto riesgo
	 */
	public ClientesDao(MongoCollection<BitacoraActualizacion> coleccionBitacoraActCtes, 
			MongoCollection<BitacoraCargaMasiva> coleccionBitacoraCargaMasiva,
			MongoCollection<Document> colectionClientesAgrupados,
			MongoCollection<Document> coleccionCatBloqueos,
			BitacoraServiceImpl bitacora,
			MongoCollection<HistoricoAltoRiesgo> coleccionCargaHistAltoRiesgo,
			MongoCollection<ClienteAltoRiesgo> coleccionClientesAltoRiesgo) {
		super();		

		this.coleccionBitacoraActCtes = coleccionBitacoraActCtes;
		this.coleccionBitacoraCargaMasiva = coleccionBitacoraCargaMasiva;
		this.coleccionClientesAgrupados = colectionClientesAgrupados;
		this.coleccionCatBloqueos = coleccionCatBloqueos;
		this.bitacora = bitacora;
		this.coleccionCargaHistAltoRiesgo = coleccionCargaHistAltoRiesgo;
		this.coleccionClientesAltoRiesgo = coleccionClientesAltoRiesgo;
	}


	/**
	 * Constructor de la clase.
	 */
	public ClientesDao() {
		super();		

		pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		factoryDB = FactoryMongoDB.getInstance();

		middlewareBD = factoryDB.getMongoClient().getDatabase(PropBD.getMiddlewareBD());
		coleccionBitacoraCargaMasiva = middlewareBD.getCollection(PropClientes.getBitacoraCargaMasivaClientes(), 
				BitacoraCargaMasiva.class).withCodecRegistry(pojoCodecRegistry);
		
		coleccionBitacoraActCtes = middlewareBD.getCollection(PropClientes.getBitacoraActualizacionClientes(), 
				BitacoraActualizacion.class).withCodecRegistry(pojoCodecRegistry);	

		coleccionClientesAgrupados = middlewareBD.getCollection(PropClientes.getClientesAgrupados());
		
		coleccionCargaHistAltoRiesgo = middlewareBD.getCollection(PropClientes.getBitacoraCargaHistAltoRiesgo(), 
				HistoricoAltoRiesgo.class).withCodecRegistry(pojoCodecRegistry);		
		
		coleccionClientesAltoRiesgo = middlewareBD
				.getCollection(PropClientes.getClientesAltoRiesgo(), ClienteAltoRiesgo.class)
				.withCodecRegistry(pojoCodecRegistry);
		
		catalogosDivisasBD = factoryDB.getMongoClient().getDatabase(PropBDCatalogos.getCatalogosBD());
		coleccionCatBloqueos = catalogosDivisasBD.getCollection(PropBDCatalogos.getCatBloqueosOperativos());
		
		
	}


	/**
	 * Metodo de consulta de parametria de busquedas.
	 *
	 * @param idCliente - Id de Cliente
	 * @return - Registros de la bitacora
	 */
	public List<BitacoraActualizacion> findBitacoraActCliente(String idCliente) {

		return coleccionBitacoraActCtes.aggregate(Arrays.asList(
				match(and( eq("id_cliente", idCliente), ne("estatus", "PENDIENTE"))),
				unwind("$campos_modificados"),
				project(fields(
						include("usuario", "origen","motivo_edicion","fecha_registro","estatus"),
						computed("campoModificado", "$campos_modificados.campo"),
						computed("valorAnterior", "$campos_modificados.valor_anterior"),
						computed("valorActual", "$campos_modificados.valor_nuevo")
						)),
				sort(Sorts.descending("_id"))
				)).into(new ArrayList<BitacoraActualizacion>());
	}

	/**
	 * Metodo de registro de bitacora de actualizaciones de clientes.
	 *
	 * @param listBitacora - Lista de actualizaciones por cliente
	 */
	public void insertBitacoraActClientes(List<BitacoraActualizacion> listBitacora) {		
		coleccionBitacoraActCtes.insertMany(listBitacora);
	}

	/**
	 * Metodo de registro de bitacora de carga masiva para actualizacion de clientes.
	 *
	 * @param bitacora - Bitacora de carga para actualizacion de clientes
	 */
	public void insertBitacoraCargaMasiva(BitacoraCargaMasiva bitacora) {
		coleccionBitacoraCargaMasiva.insertOne(bitacora);
	}

	/**
	 * Metodo de consulta de Bitacora de Carga Masiva.
	 *
	 * @return List<BitacoraCargaMasiva>
	 */
	public List<BitacoraCargaMasiva> findBitacoraCargaMasiva(){

		return coleccionBitacoraCargaMasiva.find().projection(fields(exclude("errores")))
				.sort(Sorts.descending("_id")).into(new ArrayList<BitacoraCargaMasiva>());
	}

	/**
	 * Metodo de consulta de Carga Masiva.
	 *
	 * @param idCarga - Identificador de la carga masiva
	 * @return BitacoraCargaMasiva
	 */
	public BitacoraCargaMasiva findCargaMasivaById(String idCarga){

		return coleccionBitacoraCargaMasiva.find(eq("_id", new ObjectId(idCarga))).first();
	}
	
	/**
	 * Count agrupados.
	 *
	 * @return Total de grupos
	 */
	public long countAgrupados() {
		return coleccionClientesAgrupados.countDocuments();
	}

	/**
	 * Count agrupados cliente.
	 *
	 * @param idCliente - Id de cliente
	 * @return Total de grupos en los que se encuentra el cliente
	 */
	public long countAgrupadosCliente(String idCliente) {
		return coleccionClientesAgrupados.countDocuments(eq(CLIENTES,idCliente));
	}
	
	/**
	 * Consulta clientes agrupados.
	 *
	 * @param idCliente - Id de cliente
	 * @param pagina - Número de página
	 * @param registrosPorPagina - Número de registros por pagina
	 * @return Agrupados de clientes
	 */
	public List<Document> consultaClientesAgrupados(String idCliente, int pagina, int registrosPorPagina) {

		List<Document> clientesAgrupados = new ArrayList<>();

		try {

			Bson filter = new Document();
					
			if(idCliente != null && !idCliente.isEmpty()) {
				filter = eq(CLIENTES, idCliente);
			}
								
			clientesAgrupados = coleccionClientesAgrupados.find(filter)
														   .sort(Sorts.descending(FECHAACTUALIZACION))
														   .skip((pagina-1) * registrosPorPagina)
														   .limit(registrosPorPagina)
														   .into(new ArrayList<Document>());

			for (Document doc : clientesAgrupados) {
				doc.computeIfPresent("_id", (key, val) -> ((ObjectId) val).toHexString());
			}

		} 
		catch (MongoException m) {
			LogApp.excepcion(m, "Ocurrio un error al consultar los clientes agrupados.");
		}
		
		return clientesAgrupados;
	}
		
	/**
	 * Valida registro de clientes agrupados.
	 *
	 * @param idsClientes - Ids de clientes a agrupar
	 * @return Resultado
	 */
	public Document validaClientesAgrupados(List<String> idsClientes) {

		Document resultado = null;

		resultado = coleccionClientesAgrupados.aggregate(Arrays.asList(
				match(in(CLIENTES, idsClientes)),
				unwind("$"+CLIENTES),
				match(in(CLIENTES, idsClientes)),
				group( null, Accumulators.addToSet(CLIENTES, "$"+CLIENTES)),
				project(excludeId())
				)).first();

		return resultado;
	}
	

	/**
	 * Valida actualizacion de clientes agrupados.
	 *
	 * @param idGrupo - Id de grupo a actualizar
	 * @param idsClientes - Ids de clientes a agrupar
	 * @return Resultado
	 */
	public Document validaClientesAgrupados(String idGrupo, List<String> idsClientes) {

		Document resultado = null;

		resultado = coleccionClientesAgrupados.aggregate(Arrays.asList(
				match(and(in(CLIENTES, idsClientes),
						  ne("_id",new ObjectId(idGrupo)))),
				unwind("$clientes"),
				match(in(CLIENTES, idsClientes)),
				group( null, Accumulators.addToSet(CLIENTES, "$clientes")),
				project(excludeId())
				)).first();

		return resultado;
	}

	/**
	 * Registra clientes agrupados.
	 *
	 * @param idsClientes - Ids de clientes a agrupar
	 * @return Resultado
	 */
	public boolean registraClientesAgrupados(List<String> idsClientes) {

		try {

			Document grupo = new Document(CLIENTES, idsClientes)
					.append(FECHAACTUALIZACION, DateUtils.getDateFormat(PropClientes.FORMATOFECHABDCOMPLETA));

			coleccionClientesAgrupados.insertOne(grupo);

			bitacora.registrar(AccionBitacora.REGISTRAR, "Clientes agrupados: "+ idsClientes);
			
			return true;

		} 
		catch (MongoException m) {
			LogApp.excepcion(m, "Ocurrio un error al intentar registrar el agrupado de clientes.");
			return false;
		}
	}

	/**
	 * Actualiza clientes agrupados.
	 *
	 * @param idGrupo - Id del grupo a actualizar
	 * @param idsClientes - Ids de clientes a agrupar
	 * @return Resultado
	 */
	public boolean actualizaClientesAgrupados(String idGrupo, List<String> idsClientes) {

		try {

			Document grupoAnterior = coleccionClientesAgrupados.find(eq("_id", new ObjectId(idGrupo))).first();
			
			if(grupoAnterior != null) {
				
				Document grupoActual = coleccionClientesAgrupados.findOneAndUpdate(
										eq("_id", new ObjectId(idGrupo)), 
										combine(set(CLIENTES, idsClientes), 
												set(FECHAACTUALIZACION, DateUtils.getDateFormat(PropClientes.FORMATOFECHABDCOMPLETA))),
										new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));

				bitacora.registrar(AccionBitacora.EDITAR,
						"Clientes agrupados:  Anterior " + grupoAnterior.get(CLIENTES) + 
						", Actual " + grupoActual.get(CLIENTES)) ;
				
				return true;
			}
			
			return false;

		} 
		catch (MongoException m) {
			LogApp.excepcion(m, "Ocurrio un error al intentar actualizar el agrupado de clientes.");
			return false;
		}
	}


	/**
	 * Elimina clientes agrupados.
	 *
	 * @param idGrupo - Id del grupo a eliminar
	 * @return Resultado
	 */
	public Document eliminaClientesAgrupados(String idGrupo) {

		Document grupoEliminado = null;
		
		try {

			grupoEliminado = coleccionClientesAgrupados.findOneAndDelete(eq("_id", new ObjectId(idGrupo)));

			if(grupoEliminado != null) {     
				bitacora.registrar(AccionBitacora.ELIMINAR, "Grupo eliminado: "+ grupoEliminado.get(CLIENTES));
			}				
			
		} 
		catch (MongoException m) {
			LogApp.excepcion(m, "Ocurrio un error al intentar eliminar el agrupado de clientes.");
		}
		
		return grupoEliminado;
	}
	
	/**
	 * Consulta bloqueos operativos.
	 *
	 * @param tipoBloqueo - Tipo de Bloqueo
	 * @return Lista de Bloqueos
	 */
	public List<ReqBloqueoClientes> consultaBloqueosOperativos(String tipoBloqueo) {

		List<ReqBloqueoClientes> bloqueosClientes = new ArrayList<>();
		
		try {
			
			List<Document> bloqueosOperativos = coleccionCatBloqueos.aggregate(
				Arrays.asList(
					match(eq("tipo", tipoBloqueo)),
					sort(Sorts.descending("_id")),
					project(fields(
						include("tipo"),
					 	computed("cliente","$datos_bloqueo.cliente_divisas"),
				 		computed("tipoIdentificacion", new Document("$switch", 
						new Document("branches", Arrays.asList(
						new Document("case", new Document("$eq", Arrays.asList("$"+BLOQUEO_TIPOIDENTIFICACION, Constant.INE)))
							 .append("then", Constant.DESC_INE),
						new Document("case", new Document("$eq", Arrays.asList("$"+BLOQUEO_TIPOIDENTIFICACION, Constant.PASAPORTE)))
							 .append("then", Constant.DESC_PASAPORTE),
						new Document("case", new Document("$eq", Arrays.asList("$"+BLOQUEO_TIPOIDENTIFICACION, Constant.PASSPORT_CARD)))
							 .append("then", Constant.DESC_PASSPORT_CARD),
						new Document("case", new Document("$eq",Arrays.asList("$"+BLOQUEO_TIPOIDENTIFICACION, Constant.FORMA_MIGRATORIA)))
							 .append("then", Constant.DESC_FORMA_MIGRATORIA)))
						     .append("default", "$"+BLOQUEO_TIPOIDENTIFICACION))),
					 	computed("folioIdentificacion","$datos_bloqueo.folio_identificacion")
					 		))
			    )).into(new ArrayList<Document>());

			
			Gson gson = new Gson();
			for (Document doc : bloqueosOperativos) {
				ReqBloqueoClientes bloqueo = gson.fromJson(gson.toJson(doc), ReqBloqueoClientes.class);
				bloqueo.setId(doc.getObjectId("_id").toHexString());
				bloqueosClientes.add(bloqueo);
			}

		} 
		catch (MongoException m) {
			LogApp.excepcion(m, "Ocurrió un error al consultar el catalogo de bloqueos operativos.");
		}
		
		return bloqueosClientes;
	}
	
	/**
	 * Busca bloqueo operativo por id.
	 *
	 * @param idBloqueo - Id de Bloqueo
	 * @return ReqBloqueoClientes
	 */
	public ReqBloqueoClientes buscaBloqueoOperativoPorId(String idBloqueo) {

		Document doc = coleccionCatBloqueos.find(eq("_id", new ObjectId(idBloqueo))).first();
		
		if(doc != null) {
			
			ReqBloqueoClientes bloqueo = new ReqBloqueoClientes();
			
			Document datosBloqueo = (Document) doc.get("datos_bloqueo");
			
			bloqueo.setTipo(doc.getString("tipo"));
			bloqueo.setCliente(datosBloqueo.getString("cliente_divisas"));
			bloqueo.setTipoIdentificacion(datosBloqueo.getString("tipo_identificacion"));
			bloqueo.setFolioIdentificacion(datosBloqueo.getString("folio_identificacion"));
			
			return bloqueo;
		}
		
		return null;
	
	}
	
	/**
	 * Busca bloqueo operativo por tipo y datos de bloqueo.
	 *
	 * @param tipo - Tipo de Bloqueo
	 * @param datosBloqueo - Datos de Bloqueo
	 * @return resultado
	 */
	public Document buscaBloqueoOperativo(String tipo, Document datosBloqueo) {

		return coleccionCatBloqueos.find(and(eq("tipo", tipo), eq("datos_bloqueo", datosBloqueo))).first();
	
	}
	
	/**
	 * Registra bloqueo operativo.
	 *
	 * @param tipo - Tipo de Bloqueo
	 * @param datosBloqueo - Datos de Bloqueo
	 * @param descBloqueo - Descripcion de Bloqueo
	 * @return resultado
	 */
	public boolean registraBloqueoOperativo(String tipo, Document datosBloqueo, String descBloqueo) {
		
		try {
			
			Document bloqueo = new Document("datos_bloqueo", datosBloqueo)
					.append("tipo", tipo)
					.append("fecha_registro", DateUtils.getDateFormat(Constant.FORMATOFECHACOMPLETAEXT));
				
			InsertOneResult resultado =  coleccionCatBloqueos.insertOne(bloqueo);
			
			if(resultado.getInsertedId() != null) {
				bitacora.registrar(AccionBitacora.REGISTRAR, descBloqueo, null, datosBloqueo);
				return true;
			}
			else {
				return false;
			}


		} 
		catch (MongoException m) {
			LogApp.excepcion(m, "Ocurrió un error al intentar registrar el bloqueo.");
			return false;
		}
	}
	
	/**
	 * Elimina bloqueo operativo.
	 *
	 * @param idBloqueo - Id del Bloqueo
	 * @param descBloqueo - Descripcion de Bloqueo
	 * @return resultado
	 */
	public boolean eliminaBloqueoOperativo(String idBloqueo, String descBloqueo) {
		
		try {
			
			Document bloqueo = coleccionCatBloqueos.findOneAndDelete(eq("_id", new ObjectId(idBloqueo)));
			
			if(bloqueo != null) {
				bitacora.registrar(AccionBitacora.ELIMINAR, descBloqueo, bloqueo.get("datos_bloqueo"), null);
				return true;
			}
			else {
				return false;
			}

		} 
		catch (MongoException m) {
			LogApp.excepcion(m, "Ocurrió un error al intentar eliminar el bloqueo.");
			return false;
		}
	}

	
	
	/**
	 * Registra cliente alto riesgo.
	 *
	 * @param tipo the tipo
	 * @param datosBloqueo the datos bloqueo
	 * @param descBloqueo the desc bloqueo
	 * @return true, if successful
	 */
	public boolean registraClienteAltoRiesgo(ClienteAltoRiesgo cliente) {
		boolean res = false;
		try {

			coleccionClientesAltoRiesgo.insertOne(cliente);
			res = true;

		} 
		catch (MongoException m) {
			if(m.getCode() == 11000) {
				coleccionClientesAltoRiesgo.updateOne(eq("_id", cliente.getId()),
				combine(set("nombre", cliente.getNombre()),
						set("apellidoPaterno", cliente.getApellidoPaterno()),
						set("apellidoMaterno", cliente.getApellidoMaterno()),
						set("fechaNacimiento", cliente.getFechaNacimiento())),
				new UpdateOptions().upsert(true)); 
				res = true;
			}
			else {
				LogApp.excepcion(m, "Ocurrió un error al intentar registrar el cliente.");
			}
		}
		return res;
	}
	
	
	/**
	 * Registra bitacora cliente alto riesgo.
	 *
	 * @param nombreArchivo the nombre archivo
	 * @param descArchivo the desc archivo
	 * @param registros the registros
	 */
	public void registraBitacoraClienteAltoRiesgo(String nombreArchivo, String descArchivo, int registros) {
		
		try {
			
			HistoricoAltoRiesgo bloqueo = new HistoricoAltoRiesgo();
			bloqueo.setNombre(nombreArchivo);
			bloqueo.setDescripcion(descArchivo);
			bloqueo.setRegistros(registros);
			bloqueo.setFecha(DateUtils.getDateFormat(Constant.FORMATOFECHACOMPLETAEXT));
				
			coleccionCargaHistAltoRiesgo.insertOne(bloqueo);	

		} 
		catch (MongoException m) {
			LogApp.excepcion(m, "Ocurrió un error al intentar registrar la bitacora de la carga de clientes de alto riesgo.");
		}
	}

	/**
	 * Consulta carga historica alto riesgo.
	 *
	 * @return the list
	 */
	public List<HistoricoAltoRiesgo> consultaCargaHistoricaAltoRiesgo() {
		
		return coleccionCargaHistAltoRiesgo.find()
				.sort(Sorts.descending("_id")).into(new ArrayList<HistoricoAltoRiesgo>());
	}
}
