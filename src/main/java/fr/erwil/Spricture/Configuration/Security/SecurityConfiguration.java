package fr.erwil.Spricture.Configuration.Security;

import fr.erwil.Spricture.Configuration.Security.JWT.JwtAuthenticationEndpoint;
import fr.erwil.Spricture.Configuration.Security.JWT.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationEndpoint authEndpoint;
    private final JwtAuthenticationFilter   authFilter;

    public SecurityConfiguration(JwtAuthenticationEndpoint authEndpoint,
                                 JwtAuthenticationFilter authFilter) {
        this.authEndpoint = authEndpoint;
        this.authFilter   = authFilter;
    }

    /* ─────────────────────────  PASS  ───────────────────────── */

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* ───────────────────────── FILTER CHAIN ─────────────────── */

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            CorsConfigurationSource corsSource) throws Exception {

        http
                /* CORS & CSRF */
                .cors(c -> c.configurationSource(corsSource))
                .csrf(AbstractHttpConfigurer::disable)

                /* Stateless JWT */
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                /* Routes publiques / protégées */
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user").permitAll()
                        .anyRequest().authenticated())

                /* Gestion 401 / 403 via le même bean */
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(authEndpoint)
                        .accessDeniedHandler(authEndpoint))

                /* Pas de httpBasic (on est full JWT) */
                .httpBasic(AbstractHttpConfigurer::disable);

        /* Filtre JWT avant UsernamePasswordAuthenticationFilter */
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /* ───────────────────────── AUTH MANAGER ─────────────────── */

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg)
            throws Exception {
        return cfg.getAuthenticationManager();
    }
}
