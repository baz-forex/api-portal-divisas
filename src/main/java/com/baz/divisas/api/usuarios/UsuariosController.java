package com.baz.divisas.api.usuarios;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baz.divisas.api.usuarios.model.Usuario;
import com.baz.divisas.api.usuarios.services.UsuariosServiceImpl;
import com.baz.divisas.general.dto.ResponseApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controlador de usuarios
 * @author B813196
 *
 */
@RestController
@RequestMapping("/api/usuarios")
@Api(tags = "Usuarios")
@Validated
public class UsuariosController {
	
  @Autowired
  private UsuariosServiceImpl usuariosService;
  
  /**
   * Constructor de la clase
   */
  public UsuariosController() {
	  super();
  }
  
  /**
   * Metodo que devuelve los usuarios registrados
   * @return ResponseEntity
   */
  @GetMapping(value = "")
  @ApiOperation(value = "Devuelve los usuarios registrados")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> getUsuarios() {
    
	  ResponseApi resultado = usuariosService.getUsuarios();

	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
/**
   * Metodo que devuelve la información del usuario por id
   * @param idUsuario - Identificador de usuario
   * @return ResponseEntity
   */
  @GetMapping(value = "/{idUsuario:^[1-9][0-9]*}")
  @ApiOperation(value = "Devuelve la información del usuario")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> getUsuario(@PathVariable
		  										@Size(min = 4, max=10, message="Longitud de minimo {min}, máximo {max}") 
  												String idUsuario) {
    
	  ResponseApi resultado = usuariosService.getUsuarioById(idUsuario);
	  	  
	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
  /**
   * Metodo que devuelve el perfil de accesos del usuario
   * @param idUsuario - Identificador de usuario
   * @return ResponseEntity
   */
  @GetMapping(value = "/perfil/{idUsuario:^[1-9][0-9]*}")
  @ApiOperation(value = "Devuelve el perfil de accesos del usuario")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> getPerfilUsuario(@PathVariable
													  @Size(min = 4, max=10, message="Longitud de minimo {min}, máximo {max}") 
													  String idUsuario) {
    
	  ResponseApi resultado = usuariosService.getPerfilUsuarioById(idUsuario);
	  	  
	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
  /**
   * Metodo que devuelve los modulos disponibles para registrar un usuario
   * @return ResponseEntity
   */
  @GetMapping(value = "/modulos")
  @ApiOperation(value = "Devuelve los modulos disponibles para registrar un usuario")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> getModulos() {
    
	  ResponseApi resultado = usuariosService.getModulos();
	  	  
	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
  /**
   * Metodo de registro de usuario
   * @param usuario - Nuevo usuario
   * @return ResponseEntity
   */
  @PostMapping(value = "registrar")
  @ApiOperation(value = "Registrar un nuevo usuario")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> registraUsuario(@Valid @RequestBody Usuario usuario) {
    
	  ResponseApi resultado = usuariosService.registraUsuario(usuario);
	  	  
	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
  /**
   * Metodo de actualizacion de usuario
   * @param usuario - Usuario
   * @return ResponseEntity
   */
  @PutMapping(value = "/actualizar")
  @ApiOperation(value = "Actualizar un usuario")
  @ApiResponses(value = { 
		  @ApiResponse(code = 401, message = "Unauthorized"),
		  @ApiResponse(code = 404, message = "Not Found")})
  public ResponseEntity<ResponseApi> actualizaUsuario(@Valid @RequestBody Usuario usuario) {
    
	  ResponseApi resultado = usuariosService.actualizaUsuario(usuario);
	  	  
	  return new ResponseEntity<>(resultado, resultado.getHttpStatus());
  }
  
}
