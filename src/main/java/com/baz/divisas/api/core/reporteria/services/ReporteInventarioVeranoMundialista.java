package com.baz.divisas.api.core.reporteria.services;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.baz.divisas.api.core.reporteria.model.InventarioVeranoMundialista;


/**
 * The Class ReporteInventarioVeranoMundialista.
 */
public class ReporteInventarioVeranoMundialista {

	/**
	 * Genera exel.
	 *
	 * @param stock the stock
	 * @return the byte array input stream
	 */
	public ByteArrayInputStream generaExcel(List<InventarioVeranoMundialista> stock) {
		
		byte[] bytesCSV = null;
		StringBuilder sbf = new StringBuilder();
		sbf.append("SUCURSAL").append(",").append("STOCK ASIGNADO LLAVERO").append(",").append("STOCK ENTREGADO LLAVERO").append(",")
				.append("STOCK RESTANTE LLAVERO").append(",").append("STOCK ASIGNADO PELOTA ANTIESTRÉS").append(",")
				.append("STOCK ENTREGADO PELOTA ANTIESTRÉS").append(",").append("STOCK RESTANTE PELOTA ANTIESTRÉS")
				.append(",").append("STOCK ASIGNADO MOCHILA").append(",")
				.append("STOCK ENTREGADO MOCHILA").append(",").append("STOCK RESTANTE MOCHILA").append(",")
				.append("STOCK ASIGNADO LONCHERA").append(",").append("STOCK ENTREGADO LONCHERA")
				.append(",").append("STOCK RESTANTE LONCHERA").append("\n");
		for (InventarioVeranoMundialista inventario : stock) {

			sbf.append(inventario.getId());
			sbf.append(",");
			sbf.append(inventario.getStock1());
			sbf.append(",");
			sbf.append(inventario.getStockEntregado1());
			sbf.append(",");
			sbf.append(inventario.getStockRestante1());
			sbf.append(",");
			sbf.append(inventario.getStock2());
			sbf.append(",");
			sbf.append(inventario.getStockEntregado2());
			sbf.append(",");
			sbf.append(inventario.getStockRestante2());
			sbf.append(",");
			sbf.append(inventario.getStock3());
			sbf.append(",");
			sbf.append(inventario.getStockEntregado3());
			sbf.append(",");
			sbf.append(inventario.getStockRestante3());
			sbf.append(",");
			sbf.append(inventario.getStock4());
			sbf.append(",");
			sbf.append(inventario.getStockEntregado4());
			sbf.append(",");
			sbf.append(inventario.getStockRestante4());
			sbf.append("\n");
		}

		bytesCSV = (sbf.toString().getBytes(StandardCharsets.ISO_8859_1));
		return new ByteArrayInputStream(bytesCSV);

	}
}
