package com.baz.divisas.api.core.reporteria.dao;

import static com.mongodb.client.model.Sorts.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.core.beneficios.model.CuponMasSalud;
import com.baz.divisas.api.core.reporteria.model.EmpleadosBloqueados;
import com.baz.divisas.api.core.reporteria.model.GanadorCostalSanta;
import com.baz.divisas.api.core.reporteria.model.GanadorGolDeOro;
import com.baz.divisas.api.core.reporteria.model.GanadorPromoDigital;
import com.baz.divisas.api.core.reporteria.model.GanadorVeranoMundialista;
import com.baz.divisas.api.core.reporteria.model.InventarioCostalSanta;
import com.baz.divisas.api.core.reporteria.model.InventarioMundialVerde;
import com.baz.divisas.api.core.reporteria.model.InventarioVeranoMundialista;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.config.PropCampania;
import com.baz.divisas.general.config.PropClientes;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.baz.divisas.general.util.LogApp;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Projections;

/**
 * Clase OperacionesDao.
 *
 * @author itoxtlem
 */
@Repository
public class BeneficiosDao {

	/** The pojo codec registry. */
	private CodecRegistry pojoCodecRegistry;
	
	/** The factory DB. */
	private FactoryMongoDB factoryDB;
	
	/** The middleware BD. */
	private MongoDatabase middlewareBD;
	
	/** The collection ganadores promo digital. */
	private MongoCollection<GanadorPromoDigital> collectionGanadoresPromoDigital;
	
	/** The collection ganadores gol de oro. */
	private MongoCollection<GanadorGolDeOro> collectionGanadoresGolDeOro;
	
	/** The collection inventario mundial verde. */
	private MongoCollection<InventarioMundialVerde> collectionInventarioMundialVerde;
	
	/** The collection empleados bloqueados. */
	private MongoCollection<EmpleadosBloqueados> collectionEmpleadosBloqueados;
	
	/** The collection ganadores verano mundialista. */
	private MongoCollection<GanadorVeranoMundialista> collectionGanadoresVeranoMundialista;
	
	/** The collection inventarioverano mundialista. */
	private MongoCollection<InventarioVeranoMundialista> collectionInventarioveranoMundialista;
	
	/** The collection cupones Mas Salud. */
	private MongoCollection<CuponMasSalud> collectionCuponesMasSalud;
	
	/** The collection ganadores verano mundialista. */
	private MongoCollection<GanadorCostalSanta> collectionGanadoresCostalSanta;
	
	/** The collection inventarioverano mundialista. */
	private MongoCollection<InventarioCostalSanta> collectionInventarioCostalSanta;
	/**
	 * Constructor de la clase.
	 */
	public BeneficiosDao() {
		super();
		
		pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		
		factoryDB = FactoryMongoDB.getInstance();
		middlewareBD = factoryDB.getMongoClient().getDatabase(PropBD.getMiddlewareBD());
		collectionGanadoresPromoDigital = middlewareBD.getCollection(PropCampania.getGanadoresPromocionDigital(),GanadorPromoDigital.class)
		        .withCodecRegistry(pojoCodecRegistry);
		collectionGanadoresGolDeOro = middlewareBD.getCollection(PropCampania.getGanadoresGolDeOro(),GanadorGolDeOro.class)
		        .withCodecRegistry(pojoCodecRegistry);
		collectionInventarioMundialVerde = middlewareBD.getCollection(PropCampania.getInventarioMundialVerde(),InventarioMundialVerde.class)
		        .withCodecRegistry(pojoCodecRegistry);
		collectionEmpleadosBloqueados = middlewareBD.getCollection(PropCampania.getEmpleadosBloqueadosGolOro(),EmpleadosBloqueados.class)
		        .withCodecRegistry(pojoCodecRegistry);
		collectionGanadoresVeranoMundialista = middlewareBD.getCollection(PropCampania.getOperacionesVeranoMundialista(),GanadorVeranoMundialista.class)
		        .withCodecRegistry(pojoCodecRegistry);
		collectionInventarioveranoMundialista  = middlewareBD.getCollection(PropCampania.getInventarioVeranoMundialista(),InventarioVeranoMundialista.class)
		        .withCodecRegistry(pojoCodecRegistry);
		collectionCuponesMasSalud = middlewareBD.getCollection(PropClientes.getCuponesMasSalud(),CuponMasSalud.class)
		        .withCodecRegistry(pojoCodecRegistry);
		collectionGanadoresCostalSanta = middlewareBD.getCollection(PropClientes.getOperacionesCostalSanta(),GanadorCostalSanta.class)
		        .withCodecRegistry(pojoCodecRegistry);
		collectionInventarioCostalSanta= middlewareBD.getCollection(PropClientes.getInventarioCostalSanta(),InventarioCostalSanta.class)
		        .withCodecRegistry(pojoCodecRegistry);
		
	}
	
	/**
	 * Constructor de la clase.
	 *
	 * @param collectionGanadoresPromoDigital the collection ganadores promo digital
	 * @param collectionGanadoresGolDeOro the collection ganadores gol de oro
	 * @param collectionInventarioMundialVerde the collection inventario mundial verde
	 * @param collectionEmpleadosBloqueados the collection empleados bloqueados
	 * @param collectionGanadoresVeranoMundialista the collection ganadores verano mundialista
	 * @param collectionInventarioveranoMundialista the collection inventario verano mundialista
	 * @param collectionCuponesMasSalud the collection cupones Mas Salud
	 */
	public BeneficiosDao(MongoCollection<GanadorPromoDigital> collectionGanadoresPromoDigital,
			MongoCollection<GanadorGolDeOro> collectionGanadoresGolDeOro,
			MongoCollection<InventarioMundialVerde> collectionInventarioMundialVerde,
			MongoCollection<EmpleadosBloqueados> collectionEmpleadosBloqueados,
			MongoCollection<GanadorVeranoMundialista> collectionGanadoresVeranoMundialista,
			MongoCollection<InventarioVeranoMundialista> collectionInventarioveranoMundialista,
			MongoCollection<CuponMasSalud> collectionCuponesMasSalud,
			MongoCollection<GanadorCostalSanta> collectionGanadoresCostalSanta,
			MongoCollection<InventarioCostalSanta> collectionInventarioCostalSanta) {
		super();
		this.collectionGanadoresPromoDigital = collectionGanadoresPromoDigital;
		this.collectionGanadoresGolDeOro = collectionGanadoresGolDeOro;
		this.collectionInventarioMundialVerde = collectionInventarioMundialVerde;
		this.collectionEmpleadosBloqueados = collectionEmpleadosBloqueados;
		this.collectionGanadoresVeranoMundialista = collectionGanadoresVeranoMundialista;
		this.collectionInventarioveranoMundialista = collectionInventarioveranoMundialista;
		this.collectionCuponesMasSalud = collectionCuponesMasSalud;
		this.collectionInventarioCostalSanta = collectionInventarioCostalSanta;
	}

	
	/**
	 * Consulta Ganadores Promocion Digital.
	 *
	 * @param filtro - Filtro de consulta
	 * @return Lista de ganadores
	 */
	public List<GanadorPromoDigital> consultaGanadoresPromoDigital(Document filtro) {

		List<GanadorPromoDigital> ganadores = new ArrayList<>();

		try {
			
			ganadores = collectionGanadoresPromoDigital.find(filtro).sort(ascending("fechaRegistro"))
												   .allowDiskUse(true).into(new ArrayList<GanadorPromoDigital>());
			
		} 
		catch (MongoException e) {
	
			LogApp.excepcion(e, "Ocurrió un error al consultar Operaciones");
		}
		
		return ganadores;

	}

	/**
	 * Consulta ganadores gol de oro.
	 *
	 * @param filtro the filtro
	 * @return the list
	 */
	public List<GanadorGolDeOro> consultaGanadoresGolDeOro(Document filtro) {
		List<GanadorGolDeOro> ganadores = new ArrayList<>();

		try {
			
			ganadores = collectionGanadoresGolDeOro.find(filtro).sort(ascending("fechaOperacion"))
												   .allowDiskUse(true).into(new ArrayList<GanadorGolDeOro>());
			
		} 
		catch (MongoException e) {
	
			LogApp.excepcion(e, "Ocurrió un error al consultar Operaciones de gol de oro");
		}
		
		return ganadores;
	}

	/**
	 * Consulta inventario mundial verde.
	 *
	 * @return the list
	 */
	public List<InventarioMundialVerde> consultaInventarioMundialVerde() {
		List<InventarioMundialVerde> inventario = new ArrayList<>();

		try {
			
			inventario = collectionInventarioMundialVerde.find().allowDiskUse(true)
					.into(new ArrayList<InventarioMundialVerde>());
			
		} 
		catch (MongoException e) {
	
			LogApp.excepcion(e, "Ocurrió un error al consultar Operaciones de gol de oro");
		}
		
		return inventario;
	}

	/**
	 * Consulta empleados bloqueados.
	 *
	 * @return the list
	 */
	public List<String> consultaEmpleadosBloqueados() {
		List<EmpleadosBloqueados> empleados = new ArrayList<>();
		List<String> empleadosBloqueados = new ArrayList<>();
		try {

			empleados = collectionEmpleadosBloqueados
					.aggregate(
							Arrays.asList(Aggregates.project(Projections.fields(Projections.computed("nombreCompleto",
									new Document("$concat",
											(Arrays.asList("$nombreCompleto", "-", "$fechaNacimiento"))))))))
					.allowDiskUse(true).into(new ArrayList<EmpleadosBloqueados>());
			if (null != empleados) {
				for (EmpleadosBloqueados emple : empleados) {
					empleadosBloqueados.add(emple.getNombreCompleto());
				}
			}

		} 
		catch (MongoException e) {

			LogApp.excepcion(e, "Ocurrió un error al consultar empleados bloqueados");
		}

		return empleadosBloqueados;
	}

	/**
	 * Consulta ganadores verano mundialista.
	 *
	 * @param filtro the filtro
	 * @return the list
	 */
	public List<GanadorVeranoMundialista> consultaGanadoresVeranoMundialista(Document filtro) {
		List<GanadorVeranoMundialista> ganadores = new ArrayList<>();

		try {
			
			ganadores = collectionGanadoresVeranoMundialista.find(filtro).sort(ascending("fechaOperacion"))
												   .allowDiskUse(true).into(new ArrayList<GanadorVeranoMundialista>());
			
		} 
		catch (MongoException e) {
	
			LogApp.excepcion(e, "Ocurrió un error al consultar Operaciones de verano mundialista");
		}
		
		return ganadores;
	}

	/**
	 * Consulta inventario verano mundialista.
	 *
	 * @return the list
	 */
	public List<InventarioVeranoMundialista> consultaInventarioVeranoMundialista() {
		List<InventarioVeranoMundialista> inventario = new ArrayList<>();

		try {
			
			inventario = collectionInventarioveranoMundialista.find().allowDiskUse(true)
					.into(new ArrayList<InventarioVeranoMundialista>());
			
		} 
		catch (MongoException e) {
	
			LogApp.excepcion(e, "Ocurrió un error al consultar el inventario de verano mundialista");
		}
		
		return inventario;
	}

	/**
	 * Consulta cupones Mas Salud entregados.
	 *
	 * @return the list
	 */
	public List<CuponMasSalud> consultaCuponesMasSalud() {
		List<CuponMasSalud> cupones = new ArrayList<>();

		try {
			Document filtro = new Document("estado", "entregado");
			cupones = collectionCuponesMasSalud.find(filtro)
					.sort(descending("fechaEntrega", "horaEntrega"))
					.allowDiskUse(true).into(new ArrayList<CuponMasSalud>());
			
		} 
		catch (MongoException e) {
	
			LogApp.excepcion(e, "Ocurrió un error al consultar cupones de Mas Salud");
		}
		
		return cupones;
	}

	/**
	 * Consulta inventario costal santa.
	 *
	 * @return the list
	 */
	public List<InventarioCostalSanta> consultaInventarioCostalSanta() {
		List<InventarioCostalSanta> inventario = new ArrayList<>();

		try {
			
			inventario = collectionInventarioCostalSanta.find().allowDiskUse(true)
					.into(new ArrayList<InventarioCostalSanta>());
			
		} 
		catch (MongoException e) {
	
			LogApp.excepcion(e, "Ocurrió un error al consultar el inventario de costal de santa");
		}
		
		return inventario;
	}

	/**
	 * Consulta ganadores costal santa.
	 *
	 * @param filtro the filtro
	 * @return the list
	 */
	public List<GanadorCostalSanta> consultaGanadoresCostalSanta(Document filtro) {
		List<GanadorCostalSanta> ganadores = new ArrayList<>();

		try {
			
			ganadores = collectionGanadoresCostalSanta.find(filtro).sort(ascending("fechaOperacion"))
												   .allowDiskUse(true).into(new ArrayList<GanadorCostalSanta>());
			
		} 
		catch (MongoException e) {
	
			LogApp.excepcion(e, "Ocurrió un error al consultar Operaciones de costal de santa");
		}
		
		return ganadores;
	}

}
