package com.baz.divisas.general.util;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class LogApp {

	private static final Logger LOGGER = LogManager.getLogger("apiPortalDivisas");
	private static final String LOG_MSJ_PETICION = "[{0}|{1}] {2} {3}\",\"servicios\":[{4}],\"TiempoTotal\":{5}";
	private static final String ERROR = "ERROR {0}\",\"servicios\":[{1}],\"TiempoTotal\":{2}";

	private LogApp() {
		super();
	}

	public static void excepcion(Exception e, String msg) {

		StringBuilder error = new StringBuilder();

		try {
			
			if (e.getStackTrace() == null) {
				error.append(clean(msg));
				LOGGER.error(error);
				return;
			}
			
			for (StackTraceElement ste : e.getStackTrace()) {
				
				if (ste.getClassName().contains("com.gs") || ste.getClassName().contains("com.baz")) {
					error.append("(" + ste.getClassName() + ")" + " METODO -->> "
							    + ste.getMethodName() + " LINEA -->> "
							    + ste.getLineNumber() + " EXCEPTION -->> " + clean(e.toString())
							    + " -->> " + clean(msg));
					break;
				}
				
			}

		} 
		catch (RuntimeException e1) {
			error.append("ERROR -->> " + e1 + " al construir mensaje de excepcion --->>" + e);
		}

		String msjError = MessageFormat.format(ERROR, error, "", 0);
		LOGGER.error(msjError);

	}
	
	public static void mensaje(String msg, Exception e) {
		String mensaje = clean(msg) +": "+ clean(e.getMessage()) +"\""; 
		LOGGER.info(mensaje);
	}
	
	

	public static void mensaje(String msg) {
		String mensaje = clean(msg) + "\""; 
		LOGGER.info(mensaje);
	}
	
	public static void mensajePeticion(String usuario, HttpServletRequest request, HttpServletResponse response) {
						
		String mensaje = MessageFormat.format(LOG_MSJ_PETICION, 
											  clean(usuario), 
											  request.getRemoteAddr(), 
											  request.getServletPath(), 
											  response.getStatus(), "", "", Long.toString(getTiempoDuracion(request)));
		LOGGER.info(mensaje);
	}
	
	public static void mensajePeticion(String usuario, HttpServletRequest request, String msj) {
		
		String mensaje = MessageFormat.format(LOG_MSJ_PETICION, 
											  clean(usuario), 
											  request.getRemoteAddr(), 
											  request.getServletPath(), 
											  clean(msj), "", Long.toString(getTiempoDuracion(request)));
		LOGGER.info(mensaje);
	}

	
	public static void mensajePeticion(String usuario, String ip, String path, String msj, long tiempoDuracion) {
		
		String mensaje = MessageFormat.format(LOG_MSJ_PETICION, 
											  clean(usuario), 
											  clean(ip), 
											  clean(path), 
											  clean(msj), "", Long.toString(tiempoDuracion));
		LOGGER.info(mensaje);
	}

	
	private static String clean(String cadena) {
		if (cadena == null) {
			return "";
		}
		return cadena.replace("\n", "").replace("\r", "").replace("\t", "").replace("\"", "\\\"");
	}
	
	private static long getTiempoDuracion(HttpServletRequest request) {
	
		long tiempoDuracion = 0;
		
		try {
			
			Object tiempoInicial = request.getAttribute("tiempoInicial");
			
			if(tiempoInicial != null) {
				tiempoDuracion = (System.currentTimeMillis() - (long) tiempoInicial) ;
			}
						
		}
		catch (RuntimeException e) {
			LogApp.excepcion(e, "Ocurrio un error al obtener el tiempo de duracion");
		}
		
		return tiempoDuracion;
	}

}
