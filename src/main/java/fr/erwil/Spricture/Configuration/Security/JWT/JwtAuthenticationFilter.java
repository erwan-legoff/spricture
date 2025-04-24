package fr.erwil.Spricture.Configuration.Security.JWT;

import fr.erwil.Spricture.Application.User.IUserRepository;
import fr.erwil.Spricture.Application.User.User;
import fr.erwil.Spricture.Configuration.Security.UserDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * OncePerRequestFilter means that the filter won't be used for other dispatches like async or error.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final IJwtTokenProvider jwtTokenProvider;

    private final UserDetailsService userDetailsService;

    private final IUserRepository userRepository;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService, IJwtTokenProvider jwtTokenProvider, IUserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);
        // We don't want to try connecting with an empty token
        if(!StringUtils.hasText(token)){
            logger.info("No token.");
            filterChain.doFilter(request, response);
            return;
        }
        // The token must be valid (not expired or malformed)
        if(!jwtTokenProvider.validateToken(token)){
            logger.info("Invalid token.");
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = extractUserDetails(token);
        Optional<User> user = userRepository.findByPseudoAndIsValidatedTrue(userDetails.getUsername());
        if(user.isEmpty()) {
            logger.info("Account not validated.");
            filterChain.doFilter(request, response);
            return;
        }

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
     * Extracts the JWT token from the Authorization header or the jwt cookie.
     * @param request the HTTP request to extract the token from
     * @return the token if found and valid, or null
     */
    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        if (request.getCookies() == null){
            return null;
        }
        List<Cookie> cookies = new ArrayList<>(List.of(request.getCookies()));
        return cookies.stream()
                .filter(cookie -> "jwt".equals(cookie.getName()))
                .map(Cookie::getValue)
                .filter(StringUtils::hasText)
                .findFirst()
                .orElse(null);

    }

}
