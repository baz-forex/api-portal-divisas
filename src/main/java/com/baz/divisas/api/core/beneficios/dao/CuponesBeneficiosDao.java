package com.baz.divisas.api.core.beneficios.dao;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.core.beneficios.model.CuponAlianzas;
import com.baz.divisas.api.core.beneficios.model.CuponCRM;
import com.baz.divisas.api.core.beneficios.model.CuponMasSalud;
import com.baz.divisas.api.core.beneficios.model.CuponTA;
import com.baz.divisas.api.core.clientes.model.HistoricoAltoRiesgo;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.config.PropClientes;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.DateUtils;
import com.baz.divisas.general.util.LogApp;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoBulkWriteException;
import com.mongodb.MongoException;
import com.mongodb.bulk.BulkWriteError;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.client.model.Sorts;

/**
 * The Class CuponesTiempoAireDao.
 */
@Repository
public class CuponesBeneficiosDao {

	/** The factory DB. */
	private FactoryMongoDB factoryDB;
	
	/** The middleware BD. */
	private MongoDatabase middlewareBD;
	
	/** The coleccion cupones TA. */
	private MongoCollection<CuponTA> coleccionCuponesTA;
	
	/** The coleccion historica cupones TA. */
	private MongoCollection<HistoricoAltoRiesgo> coleccionHistoricaCuponesTA;
	
	/** The coleccion cupones CRM. */
	private MongoCollection<CuponCRM> coleccionCuponesCRM;
	
	/** The coleccion historica cupones TA. */
	private MongoCollection<HistoricoAltoRiesgo> coleccionHistoricaCuponesCRM;
	
	/** The coleccion cupones Mas Salud. */
	private MongoCollection<CuponMasSalud> coleccionCuponesMasSalud;
	
	/** The coleccion historica cupones Mas Salud. */
	private MongoCollection<HistoricoAltoRiesgo> coleccionHistoricaCuponesMasSalud;
	
	/** The coleccion cupones CRM. */
	private MongoCollection<CuponAlianzas> coleccionCuponesAlianzas;
	
	/** The coleccion historica cupones TA. */
	private MongoCollection<HistoricoAltoRiesgo> coleccionHistoricaCuponesAlianzas;
	/**
	 * Instantiates a new cupones tiempo aire dao.
	 */
	public CuponesBeneficiosDao() {
		
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		factoryDB = FactoryMongoDB.getInstance();
		middlewareBD = factoryDB.getMongoClient().getDatabase(PropBD.getMiddlewareBD());
		coleccionHistoricaCuponesTA =  middlewareBD.getCollection(PropClientes.getBitacoraCuponesTA(),HistoricoAltoRiesgo.class).withCodecRegistry(pojoCodecRegistry);
		coleccionCuponesTA=  middlewareBD.getCollection(PropClientes.getCuponesTA(),CuponTA.class).withCodecRegistry(pojoCodecRegistry);
		coleccionCuponesCRM = middlewareBD.getCollection(PropClientes.getCuponesBeneficios(),CuponCRM.class).withCodecRegistry(pojoCodecRegistry);
		coleccionHistoricaCuponesCRM =  middlewareBD.getCollection(PropClientes.getBitacoraCuponesCRM(),HistoricoAltoRiesgo.class).withCodecRegistry(pojoCodecRegistry);
		coleccionCuponesMasSalud = middlewareBD.getCollection(PropClientes.getCuponesMasSalud(),CuponMasSalud.class).withCodecRegistry(pojoCodecRegistry);
		coleccionHistoricaCuponesMasSalud = middlewareBD.getCollection(PropClientes.getBitacoraCuponesMasSalud(),HistoricoAltoRiesgo.class).withCodecRegistry(pojoCodecRegistry);
		coleccionCuponesAlianzas= middlewareBD.getCollection(PropClientes.getCuponesBeneficios(),CuponAlianzas.class).withCodecRegistry(pojoCodecRegistry); 
		coleccionHistoricaCuponesAlianzas = middlewareBD.getCollection(PropClientes.getBitacoraCuponesAlianzas(),HistoricoAltoRiesgo.class).withCodecRegistry(pojoCodecRegistry);

	}
	
	
	/**
	 * Instantiates a new cupones tiempo aire dao.
	 *
	 * @param coleccionCuponesTA the coleccion cupones TA
	 * @param coleccionHistoricaCuponesTA the coleccion historica cupones TA
	 * @param coleccionCuponesCRM the coleccion cupones CRM
	 * @param coleccionHistoricaCuponesCRM the coleccion historica cupones CRM
	 * @param coleccionCuponesMasSalud the coleccion cupones Mas Salud
	 * @param coleccionHistoricaCuponesMasSalud the coleccion historica cupones Mas Salud
	 */
	public CuponesBeneficiosDao(MongoCollection<CuponTA> coleccionCuponesTA,
			MongoCollection<HistoricoAltoRiesgo> coleccionHistoricaCuponesTA,
			MongoCollection<CuponCRM> coleccionCuponesCRM,MongoCollection<HistoricoAltoRiesgo> coleccionHistoricaCuponesCRM,
			MongoCollection<CuponMasSalud> coleccionCuponesMasSalud, MongoCollection<HistoricoAltoRiesgo> coleccionHistoricaCuponesMasSalud) {
		
		super();
		this.coleccionHistoricaCuponesTA = coleccionHistoricaCuponesTA;
		this.coleccionCuponesTA=  coleccionCuponesTA;
		this.coleccionCuponesCRM = coleccionCuponesCRM;
		this.coleccionHistoricaCuponesCRM = coleccionHistoricaCuponesCRM;
		this.coleccionCuponesMasSalud = coleccionCuponesMasSalud;
		this.coleccionHistoricaCuponesMasSalud = coleccionHistoricaCuponesMasSalud;
	}
	
	
	/**
	 * Consulta carga historica.
	 *
	 * @return the list
	 */
	public List<HistoricoAltoRiesgo> consultaCargaHistorica() {
		
		return coleccionHistoricaCuponesTA.find()
				.sort(Sorts.descending("_id")).into(new ArrayList<HistoricoAltoRiesgo>());
	}


	/**
	 * Registra cupon TA.
	 *
	 * @param cupon the cupon
	 * @return true, if successful
	 */
	public boolean registraCuponTA(CuponTA cupon) {
		boolean res = false;
		try {

			coleccionCuponesTA.insertOne(cupon);
			res = true;

		} 
		catch (MongoException m) {

			LogApp.excepcion(m, "Ocurrió un error al intentar registrar el cupon de TA.");
			res = false;
		}
		return res;
	}


	/**
	 * Registra bitacora cupones TA.
	 *
	 * @param nombre the nombre
	 * @param descripcion the descripcion
	 * @param cuponesCargados the cupones cargados
	 */
	public void registraBitacoraCuponesTA(String nombre, String descripcion, int cuponesCargados) {
	try {
			
			HistoricoAltoRiesgo bloqueo = new HistoricoAltoRiesgo();
			bloqueo.setNombre(nombre);
			bloqueo.setDescripcion(descripcion);
			bloqueo.setRegistros(cuponesCargados);
			bloqueo.setFecha(DateUtils.getDateFormat(Constant.FORMATOFECHACOMPLETAEXT));
				
			coleccionHistoricaCuponesTA.insertOne(bloqueo);	

		} 
		catch (MongoException m) {
			LogApp.excepcion(m, "Ocurrió un error al intentar registrar la bitacora de la carga de cupones de TA.");
		}
		
	}


	/**
	 * Registra cupon CRM.
	 *
	 * @param cupones the cupones
	 * @return true, if successful
	 */
	public int registraCuponesCRM(List<CuponCRM> cupones) {
		
		int registrados = 0;
		try {

			coleccionCuponesCRM.insertMany(cupones, new InsertManyOptions().ordered(false));
			registrados = cupones.size();

		} 
		catch (MongoBulkWriteException e) {
			// Con ordered=false los no duplicados se insertan correctamente
			int duplicados = 0;
			for (BulkWriteError error : e.getWriteErrors()) {
				// Código 11000 = duplicate key
				if (error.getCode() == 11000) {
					duplicados++;
					LogApp.mensaje("Cupon Mas Salud duplicado: " + error.getMessage());
				}
			}
			registrados = cupones.size() - duplicados;
		}
		catch (MongoException m) {

			LogApp.excepcion(m, "Ocurrió un error al registrar cupones de Mas Salud en bulk.");
		}
		return registrados;
	}


	/**
	 * Registra bitacora cupones CRM.
	 *
	 * @param nombre the nombre
	 * @param descripcion the descripcion
	 * @param cuponRegistrados the cupon registrados
	 * @param fechaInicio the fecha inicio
	 * @param fechaFin the fecha fin
	 */
	public void registraBitacoraCuponesCRM(String nombre, String descripcion, int cuponRegistrados, String fechaInicio,
			String fechaFin) {
		try {

			HistoricoAltoRiesgo historico = new HistoricoAltoRiesgo();
			historico.setNombre(nombre);
			historico.setDescripcion(descripcion);
			historico.setRegistros(cuponRegistrados);
			historico.setFecha(DateUtils.getDateFormat(Constant.FORMATOFECHACOMPLETAEXT));

			coleccionHistoricaCuponesCRM.insertOne(historico);

		} 
		catch (MongoException m) {
			LogApp.excepcion(m, "Ocurrió un error al intentar registrar la bitacora de la carga de cupones de Dolarmania CRM.");
		}

	}
	

	/**
	 * Consulta carga historica CRM.
	 *
	 * @return the list
	 */
	public List<HistoricoAltoRiesgo> consultaCargaHistoricaCRM() {
		
		return coleccionHistoricaCuponesCRM.find()
				.sort(Sorts.descending("_id")).into(new ArrayList<HistoricoAltoRiesgo>());
	}
	
	/**
	 * Registra cupones Mas Salud en bulk.
	 *
	 * @param cupones the cupones
	 * @return the int cupones registrados
	 */
	public int registraCuponesMasSaludBulk(List<CuponMasSalud> cupones) {
		int registrados = 0;
		try {

			coleccionCuponesMasSalud.insertMany(cupones, new InsertManyOptions().ordered(false));
			registrados = cupones.size();

		} 
		catch (MongoBulkWriteException e) {
			// Con ordered=false los no duplicados se insertan correctamente
			int duplicados = 0;
			for (BulkWriteError error : e.getWriteErrors()) {
				// Código 11000 = duplicate key
				if (error.getCode() == 11000) {
					duplicados++;
					LogApp.mensaje("Cupon Mas Salud duplicado: " + error.getMessage());
				}
			}
			registrados = cupones.size() - duplicados;
		}
		catch (MongoException m) {

			LogApp.excepcion(m, "Ocurrió un error al registrar cupones de Mas Salud en bulk.");
		}
		return registrados;
	}

	/**
	 * Registra bitacora cupones Mas Salud.
	 *
	 * @param nombre the nombre
	 * @param descripcion the descripcion
	 * @param tipo the tipo
	 * @param cuponesCargados the cupones cargados
	 */
	public void registraBitacoraCuponesMasSalud(String nombre, String descripcion, String tipo, int cuponesCargados) {
		try {

			HistoricoAltoRiesgo historico = new HistoricoAltoRiesgo();
			historico.setNombre(nombre);
			historico.setDescripcion(descripcion);
			historico.setTipo(tipo);
			historico.setRegistros(cuponesCargados);
			historico.setFecha(DateUtils.getDateFormat(Constant.FORMATOFECHACOMPLETAEXT));

			coleccionHistoricaCuponesMasSalud.insertOne(historico);

		} 
		catch (MongoException m) {
			LogApp.excepcion(m, "Ocurrió un error al intentar registrar la bitacora de la carga de cupones de Mas Salud.");
		}
	}

	/**
	 * Consulta carga historica Mas Salud.
	 *
	 * @param tipo the tipo
	 * @return the list
	 */
	public List<HistoricoAltoRiesgo> consultaCargaHistoricaMasSalud(String tipo) {
		
		return coleccionHistoricaCuponesMasSalud.find(Filters.eq("tipo", tipo))
				.sort(Sorts.descending("_id")).into(new ArrayList<HistoricoAltoRiesgo>());
	}

	/**
	 * Consulta carga historica alianzas.
	 *
	 * @return the list
	 */
	public List<HistoricoAltoRiesgo> consultaCargaHistoricaAlianzas() {
		return coleccionHistoricaCuponesAlianzas.find()
				.sort(Sorts.descending("_id")).into(new ArrayList<HistoricoAltoRiesgo>());
	}

	/**
	 * Registra cupones alianzas.
	 *
	 * @param cupones the cupones
	 * @return the int
	 */
	public int registraCuponesAlianzas(List<CuponAlianzas> cupones) {
		int registrados = 0;
		try {

			coleccionCuponesAlianzas.insertMany(cupones, new InsertManyOptions().ordered(false));
			registrados = cupones.size();

		} 
		catch (MongoBulkWriteException e) {
			// Con ordered=false los no duplicados se insertan correctamente
			int duplicados = 0;
			for (BulkWriteError error : e.getWriteErrors()) {
				// Código 11000 = duplicate key
				if (error.getCode() == 11000) {
					duplicados++;
					LogApp.mensaje("Cupon Mas Salud duplicado: " + error.getMessage());
				}
			}
			registrados = cupones.size() - duplicados;
		}
		catch (MongoException m) {

			LogApp.excepcion(m, "Ocurrió un error al registrar cupones de Alianzas en bulk.");
		}
		return registrados;
	}
	
	/**
	 * Registra bitacora cupones alianzas.
	 *
	 * @param nombre the nombre
	 * @param descripcion the descripcion
	 * @param tipo the tipo
	 * @param cuponesCargados the cupones cargados
	 */
	public void registraBitacoraCuponesAlianzas(String nombre, String descripcion, int cuponRegistrados, String fechaInicio,
	String fechaFin) {
		try {

			HistoricoAltoRiesgo historico = new HistoricoAltoRiesgo();
			historico.setNombre(nombre);
			historico.setDescripcion(descripcion);
			historico.setRegistros(cuponRegistrados);
			historico.setFecha(DateUtils.getDateFormat(Constant.FORMATOFECHACOMPLETAEXT));
			
			coleccionHistoricaCuponesAlianzas.insertOne(historico);

		} 
		catch (MongoException m) {
			LogApp.excepcion(m, "Ocurrió un error al intentar registrar la bitacora de la carga de cupones de Alianzas.");
		}
	}

}
