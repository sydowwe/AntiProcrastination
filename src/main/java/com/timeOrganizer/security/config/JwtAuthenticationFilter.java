package com.timeOrganizer.security.config;

import com.timeOrganizer.model.dto.mappers.UserMapper;
import com.timeOrganizer.model.dto.response.UserResponse;
import com.timeOrganizer.model.entity.User;
import com.timeOrganizer.security.MyUserDetailsService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final MyUserDetailsService userDetailsService;
    private final UserMapper userMapper;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userDetailsService.loadById(jwtService.extractId(authHeader));
                if (jwtService.isTokenValid(authHeader, user)) {
                    //TODO kuknut preco je takto nastaveny ten token
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            user, null, user.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    //SKONTROLOVAT CI JE CACHE
                    this.setLoggedUserInSession(request, userMapper.convertToUserSettingsResponse(user));
                }
            }
        } catch (EntityNotFoundException | NumberFormatException e) {
            handleException(response, HttpStatus.NOT_FOUND, e.getMessage());
            return;
        } catch (AuthenticationException e) {
            handleException(response, HttpStatus.UNAUTHORIZED, e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }
    private void setLoggedUserInSession(HttpServletRequest request, UserResponse loggedUser) {
        HttpSession session = request.getSession(true);
        session.setAttribute("loggedUser", loggedUser);
    }

    private void handleException(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.getWriter().write(message);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
