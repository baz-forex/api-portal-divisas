package com.baz.divisas.api.core.reporteria.services;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.baz.divisas.api.core.reporteria.model.GanadorGolDeOro;

/**
 * The Class ReporteGolDeOro.
 */
public class ReporteGolDeOro {


	/**
	 * Genera excel.
	 *
	 * @param listaGanadores the lista ganadores
	 * @param bloqueos the bloqueos
	 * @return the byte array input stream
	 */
	public ByteArrayInputStream generaExcel(Iterable<GanadorGolDeOro> listaGanadores, List<String> bloqueos) {

		byte[] bytesCSV = null;
		StringBuilder sbf = new StringBuilder();

		for (GanadorGolDeOro ganador : listaGanadores) {
			if (!bloqueos.contains(ganador.getNombreCompleto().trim() + "-" + ganador.getFechaNacimiento())) {
				// Filtrar nombres por la lista de rechazos
				for (int x = 0; x < Integer.valueOf(ganador.getGolesEntregados()); x++) {

					sbf.append(ganador.getIdCliente());
					sbf.append(",");
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
					sbf.append(ganador.getCaja());
					sbf.append(",");
					sbf.append(ganador.getMontoDivisa());
					sbf.append(",");
					sbf.append(ganador.getAcumuladoBanca());
					sbf.append(",");
					sbf.append(ganador.getTipoOperacion());
					sbf.append(",");
					sbf.append(ganador.getClienteUnico());
					sbf.append("\n");
				}
			}
		}

		bytesCSV = (sbf.toString().getBytes(StandardCharsets.ISO_8859_1));
		return new ByteArrayInputStream(bytesCSV);

	}

}
