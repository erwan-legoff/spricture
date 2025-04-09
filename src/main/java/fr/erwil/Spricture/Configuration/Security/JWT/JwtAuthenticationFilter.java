package fr.erwil.Spricture.Configuration.Security.JWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * OncePerRequestFilter means that the filter won't be used for other dispatches like async or error.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final IJwtTokenProvider jwtTokenProvider;

    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService, IJwtTokenProvider jwtTokenProvider) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);
        // We don't want to try connecting with an empty token
        if(!StringUtils.hasText(token)){
            filterChain.doFilter(request, response);
            return;
        }
        // The token must be valid (not expired or malformed)
        if(!jwtTokenProvider.validateToken(token)){
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = extractUserDetails(token);

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request, userDetails);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    /**
     * Create an authentication usable for the current request context thanks to the userDetails
     * and add tracing details from the request
     * @param request the current request used to add details
     * @param userDetails the user to authenticate
     * @return an authentication usable for the current request context
     */
    private static UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication;
    }

    /**
     *
     * @param token the token to extract the user from
     * @return the user to authenticate
     */
    private UserDetails extractUserDetails(String token) {
        String username = jwtTokenProvider.extractUserName(token);

        return userDetailsService.loadUserByUsername(username);
    }

    /**
     * Will try to extract the Token from the token
     * @param request the request to extract the token from
     * @return the token if ok or null
     */
    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (!StringUtils.hasText(header) || !header.startsWith("Bearer ")) {
            logger.info("Token is empty in header.");
            return null;
        }
        return header.substring(7);
    }

}
