package com.baz.divisas.api.core.reporteria.services;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import com.baz.divisas.api.core.reporteria.model.GanadorVeranoMundialista;

public class ReporteVeranoMundialista {
	
	public ByteArrayInputStream generaExcel(Iterable<GanadorVeranoMundialista> listaGanadores) {

		byte[] bytesCSV = null;
		StringBuilder sbf = new StringBuilder();
		sbf.append("NOMBRE COMPLETO").append(",").append("FECHA NACIMIENTO").append(",").append("SUCURSAL").append(",")
				.append("NÚMERO AUTORIZACIÓN").append(",").append("NÚMERO OPERACIÓN").append(",")
				.append("FECHA OPERACIÓN").append(",").append("CAJERO").append(",").append("MONTO DIVISA").append(",")
				.append("SKU").append("\n");
		for (GanadorVeranoMundialista ganador : listaGanadores) {

			sbf.append(ganador.getNombreCompleto());
			sbf.append(",");
			sbf.append(ganador.getFechaNacimiento());
			sbf.append(",");
			sbf.append(ganador.getSucursal());
			sbf.append(",");
			sbf.append(ganador.getNumeroAutorizacion());
			sbf.append(",");
			sbf.append(ganador.getNumeroOperacion());
			sbf.append(",");
			sbf.append(ganador.getFechaOperacion());
			sbf.append(",");
			sbf.append(ganador.getCajero());
			sbf.append(",");
			sbf.append(ganador.getMontoDivisa());
			sbf.append(",");
			sbf.append(ganador.getSku());
			sbf.append("\n");
		}

		bytesCSV = (sbf.toString().getBytes(StandardCharsets.ISO_8859_1));
		return new ByteArrayInputStream(bytesCSV);

	}

}
