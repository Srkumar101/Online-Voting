package com.aaen.OnlineVotingSystem.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @author Mohammad Enayatullah
 *
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AuthenticationException e) throws IOException, ServletException {

		final String expired = (String) httpServletRequest.getAttribute("expired");

		if (expired != null)
			httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "TokenExpired");
		else
			httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");

	}
}
