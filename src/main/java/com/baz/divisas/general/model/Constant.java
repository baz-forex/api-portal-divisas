package com.baz.divisas.general.model;

/**
 * Constantes generales
 * @author B813196
 * itoxtle
 */
public final class Constant {
	
	public static final String COMPRA_EFECTIVO = "410";
	public static final String COMPRA_ABONO = "412";
	public static final String VENTA_EFECTIVO = "411";
	public static final String VENTA_CARGO = "413";
	
	public static final String DESC_COMPRA = "COMPRA";
	public static final String DESC_VENTA = "VENTA";
	
	public static final String DESC_COMPRA_EFECTIVO = "COMPRA EFECTIVO";
	public static final String DESC_COMPRA_ABONO = "COMPRA ABONO CUENTA";
	public static final String DESC_VENTA_EFECTIVO = "VENTA EFECTIVO";
	public static final String DESC_VENTA_CARGO =  "VENTA CARGO CUENTA";
	
	public static final String INE = "1";
	public static final String PASAPORTE = "2";
	public static final String PASSPORT_CARD = "37";
	public static final String FORMA_MIGRATORIA = "38";
	
	public static final String DESC_INE = "INE";
	public static final String DESC_PASAPORTE = "PASAPORTE";
	public static final String DESC_PASSPORT_CARD = "PASSPORT CARD";
	public static final String DESC_FORMA_MIGRATORIA = "FORMA MIGRATORIA";
	
	public static final String AUTHORIZATION = "Authorization";
	public static final String BEARER = "Bearer ";
	public static final int LENGTH_BEARER = 7;
	public static final String USER_AGENT = "User-Agent";
	public static final String X_ID_MODULO = "x-id-modulo";
	public static final String X_ID_USUARIO = "x-id-usuario";
	public static final String IP = "ip";
	
	public static final String CLIENTID = "ClientId";
	public static final String CLIENTID_MW = "CLIENTE_WEB";
	public static final String CLIENTID_MWADMIN = "CLIENTE_ADMIN";
	
	public static final String GRANT_TYPE = "grant_type";
	public static final String CLIENT_CREDENTIALS = "client_credentials";
	public static final String CLIENT_ID = "client_id";
	public static final String CLIENT_SECRET = "client_secret";

	public static final String FORMATOFECHACOMPLETAEXT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
	public static final String FORMATOFECHACOMPLETA = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMATOFECHACORTA = "yyyy-MM-dd";
	public static final String FORMATOHORA = "HH:mm:ss";
	
	public static final String API_FORMAT_CODIGO = "{0}.Portal-Divisas.{0}";
	public static final String API_FORMAT_INFO = "https://baz-developer.bancoazteca.com.mx/info#{0}.Portal-Divisas.{0}";
	
	public static final String ID_ACCESO_INVALIDO = "El x-idAcceso está expirado o no es válido, "
												  + "favor de solicitar uno nuevo";
	public static final String ID_REGION_INCORRECTO = "El ID de la Region no es correcto.";
	public static final String ID_SEGMENTO_INCORRECTO = "El ID del Segmento no es correcto.";
	
	public static final String COLON = ":";

	public static final int INT_0 = 0;
	public static final int INT_1 = 1;
	public static final int INT_2 = 2;
	public static final int INT_3 = 3;
	public static final int INT_4 = 4;
	public static final int INT_5 = 5;
	public static final int INT_6 = 6;
	public static final int INT_7 = 7;
	public static final int INT_8 = 8;
	public static final int INT_9 = 9;
	public static final int INT_10 = 10;
	public static final int INT_11 = 11;
	public static final int INT_12 = 12;
	public static final int INT_13 = 13;
	public static final int INT_14 = 14;
	public static final int INT_15 = 15;
	public static final int INT_16 = 16;
	public static final int INT_17 = 17;
	public static final int INT_18 = 18;
	public static final int INT_19 = 19;
	public static final int INT_20 = 20;
	public static final int INT_21 = 21;
	public static final int INT_22 = 22;
	public static final int INT_23 = 23;
	public static final int INT_24 = 24;
	public static final int INT_25 = 25;
	public static final int INT_26 = 26;
	public static final int INT_27 = 27;
	public static final int INT_28 = 28;
	public static final int INT_29 = 29;
	public static final int INT_30 = 30;
	public static final int INT_31 = 31;
	public static final int INT_32 = 32;
	public static final int INT_33 = 33;
	public static final int INT_34 = 34;
	public static final int INT_35 = 35;
	public static final int INT_36 = 36;
	public static final int INT_37 = 37;
	public static final int INT_38 = 38;
	public static final int INT_39 = 39;
	public static final int INT_40 = 40;
	public static final int INT_41 = 41;
	public static final int INT_42 = 42;
	public static final int INT_43 = 43;
	public static final int INT_44 = 44;
	public static final int INT_45 = 45;
	public static final int INT_46 = 46;
	public static final int INT_47 = 47;
	public static final int INT_48 = 48;
	public static final int INT_49 = 49;
	public static final int INT_50 = 50;
	public static final int INT_100 = 100;
	public static final int INT_256 = 256;
	public static final int INT_50000 = 50000;

	/** Numero maximo de segmentos permitidos en un path SCIM (CWE-606). */
	public static final int MAX_PATH_SEGMENTS = 20;

	/** Numero maximo de resultados a procesar por incidencia en el reporte (CWE-606). */
	public static final int MAX_RESULTADOS_INCIDENCIA = 50000;
	
	public static final String SIN_INFORMACION = "No existe informacion.";
	public static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()=+<>?";
	
	public static final String ACTIVO ="activo";
	public static final String DESCRIPCION ="descripcion";
	public static final String NO_SE_ENCONTRO_ESTRATEGIA_MGS ="No se encontro la estrategia solicitada. ";



	/**
	 * Constructor 
	 * de la clase
	 */
	private Constant() {
		super();
	}


}
