package com.baz.divisas.api.usuarios.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.bitacora.model.AccionBitacora;
import com.baz.divisas.api.bitacora.services.BitacoraServiceImpl;
import com.baz.divisas.api.usuarios.dao.ModulosDao;
import com.baz.divisas.api.usuarios.dao.UsuariosDao;
import com.baz.divisas.api.usuarios.dto.UsuarioResponse;
import com.baz.divisas.api.usuarios.model.Menu;
import com.baz.divisas.api.usuarios.model.Modulo;
import com.baz.divisas.api.usuarios.model.Nodo;
import com.baz.divisas.api.usuarios.model.Permisos;
import com.baz.divisas.api.usuarios.model.Usuario;
import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;

/**
 * Servicio de usuarios
 * @author B813196
 *
 */
@Service
public class UsuariosServiceImpl {
	
	private UsuariosDao usuariosDao;	
	private ModulosDao modulosDao;
	private BitacoraServiceImpl bitacora;
	
	/**
	 * Constructor de la clase
	 * @param usuariosDao - DAO Usuarios
	 * @param modulosDao  - DAO Modulos
	 * @param mailService - Servicio Mail
	 * @param bitacora    - Servicio Bitacora
	 */
	@Autowired
	public UsuariosServiceImpl(UsuariosDao usuariosDao, ModulosDao modulosDao, BitacoraServiceImpl bitacora) {
		super();
		this.usuariosDao = usuariosDao;
		this.modulosDao = modulosDao;
		this.bitacora = bitacora;
	}

	/**
	 * Metodo de consulta de usuarios
	 * @return - ResponseApi
	 */
	public ResponseApi getUsuarios() {
		
		ResponseApi resultado;
				
		List<Usuario> usuarios = usuariosDao.findAll();
		
		if (!usuarios.isEmpty()) {

			resultado = new ResponseApi(EstatusResponse.OK, usuarios);
						
		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
	    								Arrays.asList("No existen usuarios registrados."));
		}
		
		return resultado;
	}
	
	/**
	 * Metodo de consulta de usuario por id
	 * @param idUsuario - Identificador de usuario
	 * @return - ResponseApi
	 */
	public ResponseApi getUsuarioById(String idUsuario) {
		
		ResponseApi resultado;
				
		Usuario usuario = usuariosDao.findById(idUsuario);
		
		if (usuario != null) {
			
			// Consulta la lista de Modulos
			List<Nodo> modulos = modulosDao.getModulos();
							
			// Crea el Menu
			Menu menuUsuario = crearMenu(modulos);
			
			usuario.getModulos().forEach(modulo -> setPermisosMenu(menuUsuario.getNodos(), 
																   modulo.getId(), modulo.getPermisos(), 0));				
			
			usuario.setModulos(new ArrayList<>());
			
			UsuarioResponse usuarioResp = new UsuarioResponse(usuario, menuUsuario);
			resultado = new ResponseApi(EstatusResponse.OK, usuarioResp);
						
		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
	    								Arrays.asList("Usuario no encontrado."));
		}
		
		return resultado;
	}
	
	/**
	 * Metodo de consulta de perfil de accesos del usuario
	 * @param idUsuario - Identificador de usuario
	 * @return ResponseApi
	 */
	public ResponseApi getPerfilUsuarioById(String idUsuario) {
		
		ResponseApi resultado;
				
		Usuario usuario = usuariosDao.findById(idUsuario);
		
		if (usuario != null) {
					
			List<String> rutasUsuario = usuario.getModulos().stream().map(Modulo::getId).collect(Collectors.toList());
			
			// Consulta la lista de Modulos del Usuario
			List<Nodo> modulos = getModulosUsuario(rutasUsuario);
							
			// Crea el Menu
			Menu menuUsuario = crearMenu(modulos);

			// Settea los permisos en cada Modulo del Usuario
			usuario.getModulos().forEach(modulo -> setPermisosMenu(menuUsuario.getNodos(), 
																   modulo.getId(), modulo.getPermisos(), 0));			

			usuario.setModulos(new ArrayList<>());
			UsuarioResponse usuarioResp = new UsuarioResponse(usuario, menuUsuario);
			
			resultado = new ResponseApi(EstatusResponse.OK, usuarioResp);

						
		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
	    								Arrays.asList("Usuario no encontrado."));
		}
		
		return resultado;
	}
	
	/**
	 * Metodo de consulta de modulos
	 * @return ResponseApi
	 */
	public ResponseApi getModulos() {
		
		ResponseApi resultado;
				
		// Consulta la lista de Modulos
		List<Nodo> modulos = modulosDao.getModulos();
		
		if(modulos != null && !modulos.isEmpty()){
							
			// Crea el Menu
			Menu menu = crearMenu(modulos);
						
			UsuarioResponse usuarioResp = new UsuarioResponse(null, menu);
			resultado = new ResponseApi(EstatusResponse.OK, usuarioResp);
			
		}
		else {
			resultado = new ResponseApi(EstatusResponse.SIN_RESULTADOS,
					Arrays.asList("No existen modulos registrados."));
		}		
		
		return resultado;
	}
	
	/**
	 * Metodo de registro de usuario
	 * @param usuario - Nuevo Usuario
	 * @return Resultado
	 */
	public ResponseApi registraUsuario(@Valid Usuario usuario) {
		
		ResponseApi resultado;
		
		if(usuariosDao.findById(usuario.getId()) == null) {
			
			List<Modulo> modulosActivos = getModulosActivos(usuario.getModulos());
			
			if(!modulosActivos.isEmpty()) {
				
				usuario.setModulos(modulosActivos);
				
				if(usuariosDao.insertUsuario(usuario)) {
					
					resultado = new ResponseApi(EstatusResponse.OK,
												"La contraseña temporal del usuario "+ usuario.getId() +" es: "+ usuario.getId());
				}
				else {
					resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
							Arrays.asList("No fue posible registrar el usuario."));	
				}
			}
			else {
				resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
						Arrays.asList("modulos: Se requiere al menos un permiso activo."));	
			}

		}
		else {
			
			resultado = new ResponseApi(EstatusResponse.PETICION_INCORRECTA,
					Arrays.asList("Ya existe un usuario registrado con el mismo id."));			
		}	
		
		return resultado;
	}
	
	/**
	 * Metodo de actualizacion de usuario
	 * @param usuario - Usuario
	 * @return Resultado
	 */
	public ResponseApi actualizaUsuario(@Valid Usuario usuario) {
		
		ResponseApi resultado;
		
		List<Modulo> modulosActivos = getModulosActivos(usuario.getModulos());
		
		usuario.setModulos(modulosActivos);
		
		Usuario usuarioActual = usuariosDao.findById(usuario.getId());
			
		if (usuariosDao.updateUsuario(usuario)) {

			resultado = new ResponseApi(EstatusResponse.OK);

			bitacora.registrar(AccionBitacora.EDITAR, "Actualización del usuario: "+ usuario.getId(), 
							   usuarioActual, usuario);
			
		} 
		else {
			resultado = new ResponseApi(EstatusResponse.ERROR_INTERNO,
					Arrays.asList("No fue posible actualizar el usuario."));
		}
		
		

		return resultado;
	}

	/**
	 * Metodo de consulta de modulos activos del usuario
	 * @param rutas - Rutas de acceso del usuario
	 * @return Lista de modulos
	 */
	private List<Nodo> getModulosUsuario(List<String> rutas) {

		ArrayList<String> idsModulos = new ArrayList<>();

		// Extrae los modulos de las Rutas del Usuario
		for (String ruta : rutas) {
			StringBuilder sb = new StringBuilder();
			for (String nivelRuta : ruta.split("/")) {
				if(sb.toString().isEmpty()) {
					sb.append(nivelRuta );
				}
				else {
					sb.append("/" + nivelRuta );
				}

				idsModulos.add(sb.toString());
			}
		}

		// Consulta la lista de Modulos correspondiente a las Rutas
		return modulosDao.getModulosById(idsModulos);
	}
	
	private static Menu crearMenu(List<Nodo> modulos) {
		
		Menu menu = new Menu();
		modulos.forEach( modulo -> agregarNodoMenu(modulo ,0 , menu.getNodos()) );

		return menu;
	}

	private static boolean agregarNodoMenu(Nodo nodo, int pos, List<Nodo> menu) {
		
		for (Nodo nodoMenu : menu) {
					
			if (nodoMenu.getId().equals(nodo.getParentId())) {
				
				if (nodoMenu.getNodos() == null) {
					nodoMenu.setNodos(new ArrayList<Nodo>());
				}
				
				if( "MODULE".equals(nodo.getTipo())) {
					nodo.setPermisos(new Permisos());
				}
				return nodoMenu.getNodos().add(nodo);
				
			} 
			else {
				
				if (nodoMenu.getNodos() != null && agregarNodoMenu(nodo, pos+1, nodoMenu.getNodos())) {
					return true;
				}
			}
		}
		
		if (pos != 0) {
			return false;
		}
		
		return menu.add(nodo);
	}

	private static boolean setPermisosMenu(List<Nodo> menu, String idModulo, Permisos permisos, int pos) {

		for (Nodo nodoMenu : menu) {
			
			if (nodoMenu.getId().equals(idModulo) && "MODULE".equals(nodoMenu.getTipo())) {
				nodoMenu.setPermisos(permisos);
				return true;
			} 
			else {
				if (nodoMenu.getNodos() != null && !nodoMenu.getNodos().isEmpty() && 
					setPermisosMenu(nodoMenu.getNodos(), idModulo, permisos, pos+1)) {
					return true;
				}
			}
		}
		
		return pos == 0;
	}
	
	private static List<Modulo> getModulosActivos(List<Modulo> modulos) {
		
		List<Modulo> modulosActivos = new ArrayList<>();
		
		for (Modulo modulo : modulos) {
			
			Permisos permisos = modulo.getPermisos();
			
			if(permisos.isConsultar() || permisos.isEditar() || permisos.isRegistrar() || permisos.isBorrar()) {
				modulosActivos.add(modulo);
			}
		}
		
		return modulosActivos;
	}
	
}
