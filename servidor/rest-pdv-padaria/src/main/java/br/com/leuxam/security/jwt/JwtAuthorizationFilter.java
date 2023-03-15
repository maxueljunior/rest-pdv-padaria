package br.com.leuxam.security.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.leuxam.model.Permission;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthorizationFilter extends OncePerRequestFilter{
	
	@Autowired
    private JwtTokenProvider tokenProvider;
	
	public JwtAuthorizationFilter(JwtTokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = getTokenFromRequest(request);
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            List<Permission> permissoes = tokenProvider.getRolesWithToken(token);
            if (!permissoes.isEmpty()) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, permissoes);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
	}

	private String getTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
	}

}
