package com.baz.divisas.api.tef.parametria.dto;

import com.baz.divisas.api.apigee.dto.ResultadoApi;

/**
 * The Class ResConsultaAsignaciones.
 */
public class ResConsultaAsignaciones extends ResultadoApi{

	
		/** The resultado. */
		private ConsultaAsignaciones resultado;
		
		/**
		 * Instantiates a new res consulta asignaciones.
		 */
		public ResConsultaAsignaciones() {
			super();
		}

		/**
		 * Gets the resultado.
		 *
		 * @return the resultado
		 */
		public ConsultaAsignaciones getResultado() {
			return resultado;
		}

		/**
		 * Sets the resultado.
		 *
		 * @param resultado the new resultado
		 */
		public void setResultado(ConsultaAsignaciones resultado) {
			this.resultado = resultado;
		}
}
