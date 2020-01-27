package com.asellion.product.security;


import lombok.EqualsAndHashCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * ProductService -
 *
 * @author Mehdi Shahdoost
 * @version 2020/1/27
 * @since 1.0.0
 */
@Component
@EqualsAndHashCode
public class AuthEntryPoint implements AuthenticationEntryPoint, Serializable {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException authException) throws IOException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}
}
