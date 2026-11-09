package com.baz.divisas.api.core.clientes.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

import com.baz.divisas.api.core.clientes.dao.ClientesDao;
import com.baz.divisas.api.core.clientes.model.BitacoraActualizacion;
import com.baz.divisas.api.core.clientes.model.BitacoraCargaMasiva;
import com.baz.divisas.api.core.clientes.model.CampoModificado;
import com.baz.divisas.api.core.clientes.model.ErrorCargaMasiva;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.DateUtils;
import com.baz.divisas.general.util.LogApp;
import com.baz.divisas.general.util.SecurityUtils;
import com.baz.divisas.security.services.UserDetailsImpl;

/**
 * Clase ArchivoCargaMasiva
 * @author B813196
 * itoxtle
 */
@Service
public class ArchivoCargaMasiva {
	
	private static final String[] titles = {
			"CADENA",
			"DESCRIPCIÓN",
			"FILA"
	};
	
	private static final int NUM_PIPES = 4;
	private static final String NOMBRE = "nombre";
	private static final String APEPATERNO = "apellido_paterno";
	private static final String APEMATERNO = "apellido_materno";
	private static final String FECHANACIMIENTO = "fecha_nacimiento";
	private static final String CALLE = "calle";
	private static final String COLONIA = "colonia";
	private static final String CP = "codigo_postal";
	private static final String POBLACION = "poblacion";
	private static final String ESTADO = "estado";
	private static final String FOLIOINE = "folio_ine";
	private static final String BANDERAMV = "bandera_mv";
	
	private static final List<String> camposValidos = Arrays.asList(NOMBRE, APEPATERNO, APEMATERNO, 
			FECHANACIMIENTO, CALLE, COLONIA, CP, POBLACION, ESTADO, FOLIOINE, BANDERAMV);
	
	private ClientesDao clientesDao;
	private SecurityUtils securityService;
	
	/**
	 * Constructor de la clase.
	 *
	 * @param clientesDao - Clientes Dao
	 * @param securityService - SecurityService
	 */
	@Autowired
	public ArchivoCargaMasiva(ClientesDao clientesDao, SecurityUtils securityService) {
		super();
		this.clientesDao = clientesDao;
		this.securityService = securityService;
	}

	/**
	 * Metodo para procesar archivo de carga masiva de actualizacion
	 * @param nombreArchivo - Nombre del archivo de la carga
	 * @param lineas - Lineas del archivo
	 * 
	 * @return List<ErrorCargaMasiva>
	 */
	public void procesar(String nombreArchivo, List<String> lineas) {
		
		Set<String> lineasProcesadas = new HashSet<>();
		List<ErrorCargaMasiva> errores = new ArrayList<>();
		Map<String, BitacoraActualizacion> mapBitacoraActualizacion = new HashMap<>();
		
		BitacoraCargaMasiva bitacoraCarga = new BitacoraCargaMasiva();
		bitacoraCarga.setIdCarga(new ObjectId().toHexString());
		bitacoraCarga.setFechaCarga(DateUtils.getDateFormat(Constant.FORMATOFECHACOMPLETA));
		bitacoraCarga.setNombreArchivo(nombreArchivo);
		
		UserDetailsImpl user = securityService.getUserSesion();
		
		if(user.getIdUserSesion() != null) {
			bitacoraCarga.setUsuario(user.getIdUserSesion());
			bitacoraCarga.setNombreUsuario(user.getNombre() +" "+ user.getApellidoPaterno() +" "+ user.getApellidoMaterno());
		}

		for (int i = 0; i < lineas.size(); i++) {
			
			int numLinea = i+1;
			String linea = lineas.get(i);
			String[] columnas = linea.split(",");
			
			if(columnas.length != NUM_PIPES) {
				errores.add(new ErrorCargaMasiva(numLinea, linea, "layout_invalido"));
				continue;
			}
			
			String idCliente = columnas[Constant.INT_0].trim();
			String campo = columnas[Constant.INT_1].trim();
			String valorAnterior = columnas[Constant.INT_2].trim();
			String valorNuevo = columnas[Constant.INT_3].trim();
			
			if(validaLinea(numLinea, linea, campo, valorNuevo, lineasProcesadas, errores)) {
				
				CampoModificado campoModificado = new CampoModificado();
				
				campoModificado.setCampo(campo);
				campoModificado.setValorAnterior(valorAnterior);
				campoModificado.setValorNuevo(valorNuevo);
				
				if(campo.equals(FECHANACIMIENTO)) {
					campoModificado.setValorAnterior(DateUtils.changeDateFormat(valorAnterior,"yyyyMMdd",Constant.FORMATOFECHACORTA));
					campoModificado.setValorNuevo(DateUtils.changeDateFormat(valorNuevo,"yyyyMMdd",Constant.FORMATOFECHACORTA));
				}
				
				BitacoraActualizacion bitacora = mapBitacoraActualizacion.get(idCliente);
				
				if(bitacora == null) {
					
					bitacora = new BitacoraActualizacion();
					bitacora.setIdCarga(new ObjectId(bitacoraCarga.getIdCarga()));
					bitacora.setEstatus("PENDIENTE");
					bitacora.setOrigen("BATCH");
					bitacora.setUsuario("813196");
					bitacora.setIdCliente(idCliente);
					bitacora.setFechaRegistro(DateUtils.getDateFormat(Constant.FORMATOFECHACOMPLETA));
					bitacora.setMotivo("Modificado por BATCH");
					bitacora.getCamposModificados().add(campoModificado);
					
					mapBitacoraActualizacion.put(idCliente, bitacora);
				}
				else {
					bitacora.getCamposModificados().add(campoModificado);
				}
			}

		}
		
		List<BitacoraActualizacion> listBitacora = mapBitacoraActualizacion.entrySet()
												   .stream().map(Entry::getValue).collect(Collectors.toList());
		
		if(!listBitacora.isEmpty()) {
			clientesDao.insertBitacoraActClientes(listBitacora);
		}
			
		bitacoraCarga.setRegistrosTotales(lineas.size());
		bitacoraCarga.setRegistrosCorrectos(lineas.size() - errores.size());
		bitacoraCarga.setRegistrosErroneos(errores.size());
		bitacoraCarga.setErrores(errores);
		
		clientesDao.insertBitacoraCargaMasiva(bitacoraCarga);

	}
	
	private static boolean validaLinea(int numLinea, String linea, String campo, String valor, 
			Set<String> lineasProcesadas, List<ErrorCargaMasiva> errores) {
		
		boolean valido = false;
			
		if(camposValidos.contains(campo)) {
			
			if(validaFormatoCampo(campo, valor)) {
				
				String uid = campo + valor;
				
				if(!lineasProcesadas.contains(uid)) {
					lineasProcesadas.add(uid);
					valido = true;
				}
				else {
					errores.add(new ErrorCargaMasiva(numLinea, linea, "registro_duplicado"));
				}
					
			}
			else {
				errores.add(new ErrorCargaMasiva(numLinea, linea, "formato_campo_invalido"));
			}
		}
		else {
			errores.add(new ErrorCargaMasiva(numLinea, linea, "campo_invalido"));
		}

		return valido;

	}
	
	private static boolean validaFormatoCampo(String campo, String valor) {
		
		boolean valido = false;
		
		String regexNombre = "^[a-zA-ZñÑ\\s]{3,25}$";
		
		Map<String, String> map = new  HashMap<>();
		map.put(NOMBRE, regexNombre);
		map.put(APEPATERNO, regexNombre);
		map.put(APEMATERNO, regexNombre);
		map.put(FECHANACIMIENTO, "^((19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01]))$");
		map.put(CALLE, "^[a-zA-Z0-9ñÑ\\s/-]{3,60}$");
		map.put(COLONIA, "^[a-zA-Z0-9ñÑ\\s]{4,60}$");
		map.put(CP, "^[\\d]{5,6}$");
		map.put(POBLACION, "^[a-zA-Z0-9ñÑ\\s]{4,60}$");
		map.put(ESTADO, "^[a-zA-ZñÑ\\s]{1,85}$");
		map.put(FOLIOINE, "^[\\d]{13}$");
		map.put(BANDERAMV, "^[0-2]{1}$");

		valido = valor.matches(map.get(campo));
		
		return valido;
	}
	
	/**
	 * Metodo para generar reporte de Errores de Carga Masiva
	 * @param bitacora - Btacora de carga masiva
	 * @return ByteArrayInputStream
	 */
	public ByteArrayInputStream generaReporteExcelErrores(BitacoraCargaMasiva bitacora) {

		try(Workbook wb = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			
			Sheet  sheet = wb.createSheet("ReporteCargaMasiva");
			wb.setSheetName(0, "ReporteCargaMasiva");
			PrintSetup printSetup = sheet.getPrintSetup();
			printSetup.setLandscape(true);
			sheet.setFitToPage(true);
			sheet.setHorizontallyCenter(true);

			// title row
			CellStyle headerStyle = wb.createCellStyle();
			Font font = wb.createFont();
			font.setBold(true);
			headerStyle.setFont(font);

			Row headerRow = sheet.createRow(0);
			for (int i = 0; i < titles.length; ++i) {
				String header = titles[i];
				Cell cell = headerRow.createCell(i);
				cell.setCellStyle(headerStyle);
				cell.setCellValue(header);
			}
			
			if(bitacora != null && bitacora.getErrores() != null) {
				
				for (int i = 0; i < bitacora.getErrores().size(); ++i) {
					Row dataRow = sheet.createRow(i + 1);
					ErrorCargaMasiva bit = bitacora.getErrores().get(i);

					dataRow.createCell(0).setCellValue(bit.getCadena());
					dataRow.createCell(1).setCellValue(bit.getDescripcion());
					dataRow.createCell(Constant.INT_2).setCellValue(bit.getLinea());
				}
				
			}

			// finally set column widths, the width is measured in units of 1/256th of a
			// character width
			sheet.setColumnWidth(0, Constant.INT_100 * Constant.INT_256);
			sheet.setColumnWidth(1, Constant.INT_30  * Constant.INT_256);
			sheet.setColumnWidth(Constant.INT_2, Constant.INT_10  * Constant.INT_256);
			
			wb.write(out);
			
			return new ByteArrayInputStream(out.toByteArray());
			
		}
		catch (IOException e) {
			LogApp.excepcion(e, "Fallo al generar reporte de errores de carga masiva");
			return null;
		}
		
	}
	
}
