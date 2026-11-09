package com.baz.divisas.api.core.reporteria.dto;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.baz.divisas.api.core.reporteria.model.GanadorCostalSanta;

/**
 * The Class ReporteCostalSanta.
 */
public class ReporteCostalSanta {

	/**
	 * Genera excel.
	 *
	 * @param ganadores the ganadores
	 * @return the byte array input stream
	 */
	public ByteArrayInputStream generaExcel(List<GanadorCostalSanta> ganadores) {
		byte[] bytesCSV = null;
		StringBuilder sbf = new StringBuilder();
		sbf.append("FECHA OPERACIÓN").append(",").append("NOMBRE DEL CLIENTE").append(",").append("CORREO ELECTRONICO").append(",")
				.append("REGIÓN").append(",").append("SUCURSAL").append(",")
				.append("MONTO OPERADO").append(",").append("NÚMERO DE OPERACIÓN").append("\n");
		for (GanadorCostalSanta ganador : ganadores) {

			sbf.append(ganador.getFechaOperacion());
			sbf.append(",");
			sbf.append(ganador.getNombreCompleto());
			sbf.append(",");
			sbf.append(ganador.getCorreo());
			sbf.append(",");
			sbf.append(ganador.getRegion());
			sbf.append(",");
			sbf.append(ganador.getSucursal());
			sbf.append(",");
			sbf.append(ganador.getMontoDivisa());
			sbf.append(",");
			sbf.append(ganador.getNumeroOperacion());
			sbf.append("\n");
		}

		bytesCSV = (sbf.toString().getBytes(StandardCharsets.ISO_8859_1));
		return new ByteArrayInputStream(bytesCSV);
	}

}
