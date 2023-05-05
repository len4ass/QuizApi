package ru.len4ass.api.auth;

import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Resource
    private final JwtTokenValidator jwtTokenValidator;

    @Resource
    private final JwtClaimExtractor jwtClaimExtractor;

    @Resource
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(
            JwtTokenValidator jwtTokenValidator,
            JwtClaimExtractor jwtClaimExtractor,
            UserDetailsService userDetailsService) {
        this.jwtTokenValidator = jwtTokenValidator;
        this.jwtClaimExtractor = jwtClaimExtractor;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        var authHeaderSplitBySpace = authHeader.split("\\s+");
        if (authHeaderSplitBySpace.length != 2) {
            filterChain.doFilter(request, response);
            return;
        }

        var authType = authHeaderSplitBySpace[0];
        if (!authType.equals("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        var jwtToken = authHeaderSplitBySpace[1];
        var username = jwtClaimExtractor.extractUsername(jwtToken);
        if (username == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails user = userDetailsService.loadUserByUsername(username);
        if (jwtTokenValidator.isValidToken(jwtToken, user)) {
            var authToken = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}
