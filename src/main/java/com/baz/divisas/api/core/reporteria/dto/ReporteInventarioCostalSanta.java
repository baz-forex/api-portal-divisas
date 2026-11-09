package com.baz.divisas.api.core.reporteria.dto;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.baz.divisas.api.core.reporteria.model.InventarioCostalSanta;

/**
 * The Class ReporteInventarioCostalSanta.
 */
public class ReporteInventarioCostalSanta {

	/**
	 * Genera excel.
	 *
	 * @param inventarios the inventarios
	 * @return the byte array input stream
	 */
	public ByteArrayInputStream generaExcel(List<InventarioCostalSanta> inventarios) {
		byte[] bytesCSV = null;
		StringBuilder sbf = new StringBuilder();
		sbf.append("FECHA ÚLTIMA ENTREGA").append(",").append("REGIÓN").append(",").append("STOCK ASIGNADO").append(",")
				.append("STOCK ENTREGADO").append(",").append("STOCK RESTANTE").append("\n");
		for (InventarioCostalSanta inventario : inventarios) {

			sbf.append(validaCampo(inventario.getFechaUltimaEntrega()));
			sbf.append(",");
			sbf.append(inventario.getId());
			sbf.append(",");
			sbf.append(inventario.getStock());
			sbf.append(",");
			sbf.append(inventario.getStockEntregado());
			sbf.append(",");
			sbf.append(inventario.getStockRestante());
			sbf.append("\n");
		}

		bytesCSV = (sbf.toString().getBytes(StandardCharsets.ISO_8859_1));
		return new ByteArrayInputStream(bytesCSV);
	}

	/**
	 * Valida campo.
	 *
	 * @param valor the valor
	 * @return the string
	 */
	private String validaCampo(Object valor) {
		if(valor != null) {
			return valor.toString();
		}
		else {
			return " ";
		}
	}
}
