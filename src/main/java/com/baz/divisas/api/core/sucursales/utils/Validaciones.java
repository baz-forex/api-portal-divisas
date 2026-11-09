package com.baz.divisas.api.core.sucursales.utils;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Clase Validaciones
 * 
 * @author 1096526
 *
 */
public final class Validaciones {
	private static final Pattern NUMERIC_PATTERN = Pattern.compile("\\d+");
	private static final Pattern ALPHANUMERIC_PATTERN = Pattern.compile("^[a-zA-Z0-9]*$");
	private static final Pattern NUMERIC_PATTERN_SUC = Pattern.compile("^0*\\d{4,5}$|^0*\\d{6}$");
	private static final int VEINTICUATRO = 24;
	private static final int SEIS = 6;

	/**
	 * Constructor de la clase
	 */
	private Validaciones() {
		super();
	}

	/**
	 * Metodo para validar los campos de paginacion
	 * 
	 * @return cadena con el pepredicado de la validación
	 */
	public static Predicate<String> validaPaginacion() {
		Predicate<String> isNumeric = input -> NUMERIC_PATTERN.matcher(input).matches();
		Predicate<String> isGreaterThanZero = (String input) -> {
    if (isNumeric.test(input)) {
      int number = Integer.parseInt(input);
      return number > 0;
			}
    return false;
		};

		Predicate<String> isNotNull = Objects::nonNull;

		return isNotNull.and(isNumeric).and(isGreaterThanZero);
	}

	/**
	 * @return cadena con el pepredicado de la validación
	 */
	public static Predicate<String> validaID() {
		Predicate<String> isAlphanumeric = input -> ALPHANUMERIC_PATTERN.matcher(input).matches();
		Predicate<String> hasLength24 = input -> input.length() == VEINTICUATRO;
		Predicate<String> isNotNull = Objects::nonNull;

		return isNotNull.and(isAlphanumeric).and(hasLength24);
	}

	/**
	 * @return cadena con el pepredicado de la validación
	 */
	public static Predicate<String> validaCampo() {
		Predicate<String> isNotNull = Objects::nonNull;
		Predicate<String> isNotEmpty = input -> !input.isEmpty();

		return isNotNull.and(isNotEmpty);
	}

	/**
	 * @return cadena con el pepredicado de la validación
	 */
	public static Predicate<List<String>> validaCampoSucursal() {

		Predicate<List<String>> matchesPattern = (List<String> input) -> {
		for (String s : input) {
      if (!NUMERIC_PATTERN_SUC.matcher(s).matches() || s.length() > SEIS) {
        return false;
				}
			}
    return true;
		};

		Predicate<List<String>> isNotNull = Objects::nonNull;

		return isNotNull.and(matchesPattern);
	}

}
