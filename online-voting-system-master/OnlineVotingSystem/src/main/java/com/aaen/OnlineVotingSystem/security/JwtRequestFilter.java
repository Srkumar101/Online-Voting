package com.aaen.OnlineVotingSystem.security;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aaen.OnlineVotingSystem.service.BlacklistedJwtTokenService;

/**
 * @author Mohammad Enayatullah
 *
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	// private final ObjectMapper mapper = new ObjectMapper();
	private static final Logger log = LoggerFactory.getLogger(JwtRequestFilter.class.getName());

	@Autowired
	private BlacklistedJwtTokenService blacklistedJwtTokenService;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String authorizationHeader = request.getHeader("Authorization");

		String userId = null;
		String token = null;

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

			token = authorizationHeader.substring(7);

			if (!token.equals("null")) {

				try {
					userId = jwtUtil.extractId(token);
				} catch (Exception e) {
					log.info("JWT Token Expired, Please login again");
				}
			}
		}

		if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			if (jwtUtil.validateToken(token)) {

				if (!blacklistedJwtTokenService.getBlacklistedJwtTokenIsExistByTokenName(token)) {

					Collection<? extends GrantedAuthority> privileges = jwtUtil.extractPrivileges(token);

					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userId, null, privileges);
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}

			} else {

				Map<String, Object> errorDetails = new HashMap<>();
				errorDetails.put("message", "Token expire");
				log.info("JWT Token Expired" + errorDetails);
			}
		}
		chain.doFilter(request, response);
	}
}
