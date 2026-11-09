package com.baz.divisas.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.LogApp;
import com.baz.divisas.general.util.SecurityUtils;
import com.baz.divisas.security.services.UserDetailsImpl;
import com.baz.divisas.security.services.UserDetailsServiceImpl;

import io.jsonwebtoken.JwtException;

public class AuthTokenFilter extends OncePerRequestFilter {
		
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private SecurityUtils securityService;

	/**
	 * Constructor de la clase
	 */
	public AuthTokenFilter() {
		super();
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = "";
		try {

			request.setAttribute("tiempoInicial", System.currentTimeMillis());
			
			jwt = jwtUtils.parseJwt(request);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				
				String username = jwtUtils.getUserNameFromJwtToken(jwt);
				String ip = jwtUtils.getIpFromJwtToken(jwt);
				String navegador = jwtUtils.getNavegadorFromJwtToken(jwt);

				UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(username);
								
				if(request.getRemoteAddr().equals(ip) && request.getHeader(Constant.USER_AGENT).equals(navegador)) {
					
					userDetails.setIdModulo(request.getHeader(Constant.X_ID_MODULO));
					userDetails.setIdUserSesion(request.getHeader(Constant.X_ID_USUARIO));
					
					UsernamePasswordAuthenticationToken authentication =
							new UsernamePasswordAuthenticationToken(
									userDetails,
									null,
									userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					securityService.setUserSesion(authentication);
				}

			}
		} 
		catch (JwtException e) {
			LogApp.mensaje("Invalid JWT", e);
			request.setAttribute("expired", e.getMessage());
		}		 
		catch (RuntimeException e) {
			LogApp.excepcion(e, "Ocurrio un error en la autenticacion del usuario");
		}

		filterChain.doFilter(request, response);
		
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request)
			throws ServletException {
		String path = request.getRequestURI();
		return path.contains("/reset-contrasenia");
	}
	
}
