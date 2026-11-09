package com.baz.divisas.api.core.beneficios.util;

import java.util.ArrayList;

import com.baz.divisas.api.core.beneficios.model.BeanCampania;
import com.baz.divisas.api.core.beneficios.model.BeanCatalogo;
import com.baz.divisas.api.core.beneficios.model.BeanPremio;

/**
 * The Class CamposGenericos.
 */
public class CamposGenericos {

	private static final String PERFILINDETERMINADO = "INDETERMINADO";
	private static final String PERFILDESLEAL = "DESLEAL";
	private static final String PERFILLEAL = "LEAL";

	/**
	 * Metodo para llenar las actividades economicas si vienen vacias.
	 *
	 * @param request the request
	 */
	public static void actividadesEconomicas(BeanCampania request) {
		if(null == request.getPremios()){
			return;
		}
		for (BeanPremio premio : request.getPremios()) {
			asignarActividadEconomica(premio);
		}
	}
	
	/**
	 * Asignar actividad economica.
	 *
	 * @param premio the premio
	 */
	public static void asignarActividadEconomica(BeanPremio premio) {
		if( null == premio.getActividadEconomica() || premio.getActividadEconomica().isEmpty())
		{
			premio.setActividadEconomica(obtenerActividades());
		}
	}
	private static ArrayList<BeanCatalogo> obtenerActividades(){
		ArrayList<BeanCatalogo> actividades = new ArrayList<>();
		actividades.add(obtenerCatalogo("1-1", "AMA DE CASA"));
		actividades.add(obtenerCatalogo("1-2","ESTUDIANTE"));
		actividades.add(obtenerCatalogo("1-4","DESEMPLEADO"));
		actividades.add(obtenerCatalogo("2-1","EMPLEADO DE GOBIERNO"));
		actividades.add(obtenerCatalogo("2-2","EMPLEADO EMPRESA PRIVADA"));
		actividades.add(obtenerCatalogo("2-33","MESERO"));
		actividades.add(obtenerCatalogo("2-34","SERVICIO DE LIMPIEZA / DOMESTICO"));
		actividades.add(obtenerCatalogo("4-28","COMERCIANTE"));
		actividades.add(obtenerCatalogo("4-29","TAXISTA/CHOFER"));
		actividades.add(obtenerCatalogo("4-30","NEGOCIO PROPIO"));
		actividades.add(obtenerCatalogo("4-31","PROFESIONISTA INDEPENDIENTE"));
		actividades.add(obtenerCatalogo("4-32","AGRICULTOR"));
		actividades.add(obtenerCatalogo("4-27","OFICIO"));
		actividades.add(obtenerCatalogo("8-1","PENSIONADO/JUBILADO"));
		return actividades;
	}
	private static BeanCatalogo obtenerCatalogo(String id, String descripcion){
		BeanCatalogo cat = new BeanCatalogo();
		cat.setId(id);
		cat.setDescripcion(descripcion);
		return cat;
	}
	
	/**
	 * Perfil cliente.
	 *
	 * @param request the request
	 */
	public static void perfilCliente(BeanCampania request) {
		if(null == request.getPremios()){
			return;
		}
		for (BeanPremio premio : request.getPremios()) {
			asignarPerfilCliente(premio);
		}
	}
	
	/**
	 * Asignar perfil cliente.
	 *
	 * @param premio the premio
	 */
	public static void asignarPerfilCliente(BeanPremio premio) {
		if(null == premio.getPerfilCliente() 
				|| premio.getPerfilCliente().isEmpty() 
				|| premio.getPerfilCliente().contains("NINGUNO"))
		{
			premio.setPerfilCliente(obtenerPerfilesGenericos());
		}
		else{
			premio.getPerfilCliente().add(PERFILLEAL);
			premio.getPerfilCliente().add(PERFILDESLEAL);
			premio.getPerfilCliente().add(PERFILINDETERMINADO);
		}
	}
	private static ArrayList<String> obtenerPerfilesGenericos() {
		ArrayList<String> list = new ArrayList<>();
		list.add(PERFILLEAL);
		list.add(PERFILDESLEAL);
		list.add(PERFILINDETERMINADO);
		return list;
	}
}
