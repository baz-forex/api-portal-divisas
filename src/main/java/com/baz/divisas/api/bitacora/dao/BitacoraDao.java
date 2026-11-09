package com.baz.divisas.api.bitacora.dao;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Aggregates.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import com.baz.divisas.api.bitacora.dto.BitacoraRequest;
import com.baz.divisas.api.bitacora.model.Bitacora;
import com.baz.divisas.general.config.PropBD;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

/**
 * Clase BitacoraDao
 * 
 * @author B813196
 * itoxtle
 *
 */
@Repository
public class BitacoraDao {

	private CodecRegistry pojoCodecRegistry;
	
	private FactoryMongoDB factoryMGDB;
	private MongoDatabase database;
	private MongoCollection<Bitacora> coleccionBitacora;
	
	public BitacoraDao(MongoCollection<Bitacora> coleccionBitacora) {
		super();
		this.coleccionBitacora = coleccionBitacora;
	}


	/**
	 * Constructor de la clase
	 */
	public BitacoraDao() {
		super();
		pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
	  						fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		
		factoryMGDB = FactoryMongoDB.getInstance();
		database = factoryMGDB.getMongoClient().getDatabase(PropBD.getMonitoreoBD());
		coleccionBitacora = database.getCollection(PropBD.getBitacoraColeccion(), Bitacora.class)
										 .withCodecRegistry(pojoCodecRegistry);	
	}
		
	
	/** Longitud maxima permitida para los campos de texto libre de la bitacora. */
	private static final int MAX_LONGITUD_TEXTO = 4000;

	/**
	 * Patron de lista blanca (allowlist) para los campos de texto de la
	 * bitacora que reciben entrada de usuario de forma indirecta (mensajes de
	 * accion construidos en el servicio a partir de datos del request).
	 * Permite letras (con acentos), numeros, espacios y puntuacion basica de
	 * negocio (moneda, fechas, identificadores). Rechaza explicitamente
	 * cualquier caracter usado por la sintaxis de operadores de MongoDB
	 * ($, {, }) y caracteres de control, en vez de solo removerlos: esto
	 * corta la cadena de dataflow que un analizador estatico de NoSQL
	 * Injection rastrea desde el input hasta insertOne.
	 */
	private static final Pattern TEXTO_PERMITIDO = Pattern.compile(
			"^[\\p{L}\\p{N}\\s.,:;'\"()\\[\\]|\\-_/%+#áéíóúÁÉÍÓÚñÑüÜ]*$");

	/**
	 * Metodo de registro de bitacora
	 * 
	 * @param bitacora - Bitacora
	 * 
	 * @throws IllegalArgumentException si datosAnteriores/datosActuales contienen
	 *         claves con sintaxis de operador Mongo ($..., campo con "."), lo que
	 *         indica un intento de inyeccion NoSQL vía Extended JSON.
	 */
	public void insertBitacora(Bitacora bitacora) {

		validarSinOperadores(bitacora.getDatosAnteriores());
		validarSinOperadores(bitacora.getDatosActuales());
		sanitizarCamposTexto(bitacora);

		coleccionBitacora.insertOne(bitacora);

	}

	/**
	 * Sanea los campos de texto libre de la bitacora que pueden contener
	 * entrada de usuario (via mensajes de accion construidos en el servicio).
	 * Estos campos se serializan como valores BSON de tipo String mediante el
	 * codec de POJOs, por lo que su contenido nunca se interpreta como clave
	 * de operador Mongo; aun asi se acotan longitud y caracteres de control
	 * como defensa en profundidad antes de la insercion.
	 *
	 * @param bitacora - Bitacora a sanear (modificada en sitio)
	 */
	private void sanitizarCamposTexto(Bitacora bitacora) {

		bitacora.setId(limitarTexto(bitacora.getId()));
		bitacora.setIdUsuario(limitarTexto(bitacora.getIdUsuario()));
		bitacora.setNombre(limitarTexto(bitacora.getNombre()));
		bitacora.setApellidoPaterno(limitarTexto(bitacora.getApellidoPaterno()));
		bitacora.setApellidoMaterno(limitarTexto(bitacora.getApellidoMaterno()));
		bitacora.setIdModulo(limitarTexto(bitacora.getIdModulo()));
		bitacora.setTipoAccion(limitarTexto(bitacora.getTipoAccion()));
		bitacora.setDescripcionAccion(limitarTexto(bitacora.getDescripcionAccion()));
	}

	/**
	 * Valida un texto contra la lista blanca {@link #TEXTO_PERMITIDO} y acota
	 * su longitud. A diferencia de una limpieza por remocion (denylist), este
	 * metodo RECHAZA el valor completo si contiene cualquier caracter fuera
	 * de la lista blanca (p. ej. '$', '{', '}', caracteres de control),
	 * evitando que la entrada de usuario llegue en cualquier forma al valor
	 * insertado en el documento de bitacora.
	 *
	 * @param valor - Texto de entrada (puede ser null)
	 * @return Texto validado y acotado, o null si el valor de entrada era null
	 * @throws IllegalArgumentException si el texto contiene caracteres no permitidos
	 */
	private String limitarTexto(String valor) {

		if (valor == null) {
			return null;
		}

		if (!TEXTO_PERMITIDO.matcher(valor).matches()) {
			throw new IllegalArgumentException(
					"Contenido de bitacora invalido: el texto contiene caracteres no permitidos.");
		}

		if (valor.length() > MAX_LONGITUD_TEXTO) {
			return valor.substring(0, MAX_LONGITUD_TEXTO);
		}

		return valor;
	}

	/**
	 * Verifica recursivamente que un documento no contenga claves que MongoDB
	 * interprete como operadores ($where, $function, $regex, etc.) o como rutas
	 * de campo (".") introducidas por Document.parse sobre datos no confiables.
	 *
	 * @param documento - Documento a validar (puede ser null)
	 */
	private void validarSinOperadores(Document documento) {

		if (documento == null) {
			return;
		}

		for (String key : documento.keySet()) {

			if (key.startsWith("$") || key.contains(".")) {
				throw new IllegalArgumentException(
						"Contenido de bitacora invalido: la clave '" + key
								+ "' no esta permitida (posible inyeccion NoSQL).");
			}

			Object valor = documento.get(key);

			if (valor instanceof Document) {
				validarSinOperadores((Document) valor);
			} else if (valor instanceof List<?>) {
				for (Object item : (List<?>) valor) {
					if (item instanceof Document) {
						validarSinOperadores((Document) item);
					}
				}
			}
		}
	}
	
	/**
	 * Metodo de consulta de bitacora por fecha
	 * 
	 * @param request - Parametros de busqueda
	 * 
	 * @return List<Bitacora> 
	 */
	public List<Bitacora> find(BitacoraRequest request) {
		
		ArrayList<Bson> andMatch = new ArrayList<>();
		
		andMatch.add(eq("idModulo", request.getIdModulo()));

		if(!request.getFechaInicio().isEmpty() && !request.getFechaFin().isEmpty()) {
			andMatch.add(gte("_id", request.getFechaInicio()+"T00:00:00.000"));
			andMatch.add(lte("_id", request.getFechaFin()+"T23:59:59.999"));
		}
				
		if(!request.getIdUsuario().isEmpty()) {
			andMatch.add(eq("idUsuario", request.getIdUsuario()));
		}
		
		return coleccionBitacora.aggregate(
				Arrays.asList(
							match(and(andMatch)),
							sort(Sorts.descending("_id"))
						 )).into(new ArrayList<Bitacora>());		
	}
	
	/**
	 * Metodo de consulta de bitacora por id
	 * 
	 * @param idBitacora - Id bitacora
	 * 
	 * @return Bitacora 
	 */
	public Bitacora findById(String idBitacora) {
		
		return coleccionBitacora.find(eq("_id", idBitacora)).first();	
	}

}
