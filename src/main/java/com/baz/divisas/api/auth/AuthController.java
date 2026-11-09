package com.baz.divisas.api.auth;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baz.divisas.api.auth.model.AuthClient;
import com.baz.divisas.api.auth.service.AuthClientServiceImpl;
import com.baz.divisas.general.config.PropApp;
import com.baz.divisas.general.factory.FactoryMongoDB;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.scim.rest.Controller;
import com.baz.divisas.security.jwt.JwtUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controlador de autenticacion
 * 
 * @author B813196
 *
 */
@RestController
@RequestMapping("")
@Api(tags = "Autenticacion")
public class AuthController extends Controller {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private AuthClientServiceImpl authClientService;

	/**
	 * Constructor de la clase
	 */
	public AuthController() {
		super();
	}

	/**
	 * Metodo para crear un Cliente Api Divisas
	 * @param client - Cliente Api Divisas
	 * @return - Cliente Api Divisas
	 */
	@PostMapping(path = "/api/auth/client", produces = {"application/json", "application/json"})
	public ResponseEntity<AuthClient> createAuthClient(@RequestBody AuthClient client) {
		AuthClient authUser = authClientService.createAuthClient(client.getClientId());
		return new ResponseEntity<>(authUser, HttpStatus.OK);
	}


	/**
	 * Metodo de generacion de token de autenticacion
	 * @param entity - Request
	 * @param request HttpRequest
	 * @return ResponseEntity
	 */
	@PostMapping(path="/scim/v2/token", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ApiOperation(value = "Genera token de autenticacion", 
	notes="grant_type=client_credentials&"
			+ "client_id=444956495341535f504f5254414c&client_secret=604c6337303544723631375358306846")
	public ResponseEntity<Map<String, Object>> tokenInfo(@RequestBody(required = false) MultiValueMap<String, 
			Object> entity, HttpServletRequest request){

		ResponseEntity<Map<String, Object>> resp;
		
		if (entity != null && entity.containsKey(Constant.GRANT_TYPE) && 
				entity.get(Constant.GRANT_TYPE).get(0).equals(Constant.CLIENT_CREDENTIALS)) {

			String credentials = authClientService.extractCredentials(entity.toSingleValueMap());

			if (credentials != null && credentials.contains(Constant.COLON)) {
				
				String reqClientSecret = authClientService.parseClientSecret(entity.toSingleValueMap());
				AuthClient client = authClientService.getAuthClient(credentials);

				if (client != null && BCrypt.checkpw(reqClientSecret, client.getClientSecret())) {

					String[] cc = credentials.split(Constant.COLON);

					Map<String, Object> response = new HashMap<>();

					response.put("access_token", jwtUtils.generateJwtToken(cc[0]));
					response.put("token_type", Constant.BEARER.trim());
					response.put("expires_in", jwtUtils.getJwtExpirationMs());

					resp = new ResponseEntity<>(response, HttpStatus.OK);

				} 
				else {
					resp = showError(HttpStatus.FORBIDDEN.value(), "invalid client_id/client_secret");
				}

			} 
			else {
				// No se refleja el valor de "credentials" (deriva de entrada del cliente,
				// decodificada de Hex sin validar) en la respuesta: evita reflejar datos
				// no confiables en el cuerpo del error (reflected input / SAST Open_Redirect
				// taint flow desde "entity" hasta el ResponseEntity de showError).
				String message = "client_id/client_secret not found or incorrect, "
						+ "make sure it is part of the payload or present in the Authorization header";
				resp = showError(HttpStatus.FORBIDDEN.value(), message);
			}
		} 
		else {
			resp = showError(HttpStatus.FORBIDDEN.value(), "grant type not found in body or is not of type client_credentials");
		}
		
		return resp;

	}


	/**
	 * Metodo de actualizacion de contraseña
	 * 
	 * @param idAcceso - Id Acceso
	 * @param request - Request Actualiza contraseña
	 * @return - ResponseEntity
	 * @throws GeneralSecurityException - Encriptado/Desencriptado
	 */
	@GetMapping("/validaEstatus")
	@ApiOperation(value = "Valida estatus del servicio")
	public ResponseEntity<String> validaEstatus(){

		try {

			if(PropApp.isLoadProp()) {			  
				FactoryMongoDB.validaConexionMongo();
				return ResponseEntity
						.status(HttpStatus.OK)                 
						.body("{\"codigo\": \"200.Portal-Divisas.200\"," + 
								"\"mensaje\": \"Operación exitosa.\"}");
			}
			else {

				return ResponseEntity
						.status(HttpStatus.INTERNAL_SERVER_ERROR)                 
						.body("{\"codigo\": \"500.Portal-Divisas.500\"," + 
								"\"mensaje\": \"No se cargo correctamente la configuracion.\"}");
			}

		}
		catch(RuntimeException e) {

			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)                 
					.body("{\"codigo\": \"500.Portal-Divisas.500\"," + 
							"\"mensaje\": \""+e+"\"}");
		} 

	}

}
