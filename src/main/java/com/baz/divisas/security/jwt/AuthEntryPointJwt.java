package com.baz.divisas.security.jwt;

import java.io.IOException;
import java.util.Arrays;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.baz.divisas.general.dto.EstatusResponse;
import com.baz.divisas.general.dto.ResponseApi;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		
		String mensaje = "";
		EstatusResponse estatus;
		
		if(request.getAttribute("expired") != null) {
			mensaje = "Token invalido";
			estatus = EstatusResponse.PETICION_INCORRECTA;
		}
		else {
			mensaje = "Autenticacion requerida para acceder al recurso.";
			estatus = EstatusResponse.NO_AUTORIZADO;
		}
				
		ResponseApi resp = new ResponseApi(estatus, Arrays.asList(mensaje));

		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), resp);
	}
	

}
