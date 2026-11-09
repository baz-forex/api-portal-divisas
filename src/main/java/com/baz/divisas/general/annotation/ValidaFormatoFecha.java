package com.baz.divisas.general.annotation;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.LogApp;


public class ValidaFormatoFecha implements ConstraintValidator<FormatoFechaCorta, String> {

	@Override
	public boolean isValid(String fecha, ConstraintValidatorContext context) {

		SimpleDateFormat format = new SimpleDateFormat(Constant.FORMATOFECHACORTA);
		format.setLenient(false);

		try {

			ParsePosition pp  = new ParsePosition(0);

			if(fecha != null && !fecha.isEmpty()) {
				
				format.parse(fecha, pp);

				if(Constant.FORMATOFECHACORTA.length() != pp.getIndex()) {
					return false;
				}
			}

			return true;

		}
		catch(RuntimeException e){
			LogApp.excepcion(e, "Error al parsear fecha: "+ fecha);
			return false;
		}
	}

}
