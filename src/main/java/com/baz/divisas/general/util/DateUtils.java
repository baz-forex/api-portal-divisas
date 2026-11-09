package com.baz.divisas.general.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Clase de Utilerias relacionadas con eechas
 * 
=======
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Clase de Utilerias
 * relacionadas con fechas
 * @author B813196
 * itoxtle
 */
public final class DateUtils {

	private DateUtils() {
		super();
	}

	/**
	 * Funcion que devuelve la fecha actual en el formato especificado.
	 * 
	 * @param format: Cadena de formato en el que se creara la fecha.
	 * @return: Cadena con la fecha formateada. Si ocurre alguna excepcion regresa
	 *          una cadena vacia.
	 */
	public static String getDateFormat(String format) {

		String dateFormat = "";

		try {
			LocalDateTime now = LocalDateTime.now();
			dateFormat = now.format(DateTimeFormatter.ofPattern(format));
		} 
		catch (RuntimeException e) {
			LogApp.excepcion(e, "Error al generar fecha con el formato: " + format);
		}

		return dateFormat;

	}
	
	/**
	 * Funcion que cambia el formato de una fecha
	 * 
	 * @param anio: Cadena con el valor de la fecha año.
	 * @param mes:  Cadena de formato actual de la fecha mes.
	 * @param dia:  Cadena de formato que se le aplicara a la fecha dia. 
	 * @param fechaInicial: boolean de la fecha inicio.
	 * @return: Cadena con la fecha formateada. Si ocurre alguna excepcion regresa
	 *          una cadena vacia.
	 */
	public static String getFecIniFin(int anio, int mes, int dia, boolean fechaInicial){
		Calendar cal=GregorianCalendar.getInstance();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		
		if (fechaInicial) {
			cal.set(anio, mes, dia);

			return format.format(new Date(cal.getTimeInMillis()));
		} 
		else{
			cal.set(anio, mes, dia);
			cal.set(anio, mes, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			return format.format(new Date(cal.getTimeInMillis()));
		}
		
	}

	/**
	 * Funcion que cambia el formato de una fecha
	 * 
	 * @param date:   			Cadena con el valor de la fecha.
	 * @param currentFormat: 	Cadena de formato actual de la fecha.
	 * @param newFormat: 		Cadena de formato que se le aplicara a la fecha.
	 * @return: 	 			Cadena con la fecha formateada.
	 * 				 			Si ocurre alguna excepcion regresa una cadena vacia.
	 */
	public static String changeDateFormat(String date, String currentFormat, String newFormat){

		String newDate = "";

		try {		
			
			if(date != null && !date.isEmpty()){				
				Date dateToChange; 
				ParsePosition pp  = new ParsePosition(0);
				SimpleDateFormat df = new SimpleDateFormat(currentFormat);			
				df.setLenient(false);
				dateToChange = df.parse(date, pp);							

				df = new SimpleDateFormat(newFormat);
				df.setLenient(false);
				newDate = df.format(dateToChange);
			}
			
		}
		catch(RuntimeException e){
			LogApp.mensaje("Ocurrio un error al cambiar el formato de la fecha", e);
		}

		return newDate;
	}
	
	/**
	 * Metodo getDateFormat - date
	 * 
	 * @param format - retorna la fecha con formato
	 */
	public static String getDateFormato(String format) {

		String dateFormat = "";

		try {

			dateFormat = ZonedDateTime.now().format(DateTimeFormatter.ofPattern(format));
		} 
		catch (RuntimeException e) {
			return "";
		}

		return dateFormat;

	}
	
}
