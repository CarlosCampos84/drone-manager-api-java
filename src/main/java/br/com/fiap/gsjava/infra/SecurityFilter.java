package br.com.fiap.gsjava.infra;

import br.com.fiap.gsjava.repository.UsuarioRepository;
import br.com.fiap.gsjava.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final UsuarioRepository repository;

    public SecurityFilter(TokenService tokenService, UsuarioRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = getBearerToken(request);
        if (jwt != null) {
            String email = tokenService.getSubject(jwt);
            UserDetails user = repository.findByEmail(email);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getBearerToken(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization");

        if (authToken != null) {
            return authToken.replace("Bearer ", "");
        }
        return null;
    }
}
