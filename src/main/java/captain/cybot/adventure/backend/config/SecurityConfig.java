package captain.cybot.adventure.backend.config;

import captain.cybot.adventure.backend.constants.ROLES;
import captain.cybot.adventure.backend.filter.CustomAuthenticationFilter;
import captain.cybot.adventure.backend.filter.CustomAuthorizationFilter;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final Algorithm algorithm;

    public SecurityConfig(RSAKeyProperties rsaKeyProperties, UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.algorithm = Algorithm.RSA512(rsaKeyProperties.publicKey(), rsaKeyProperties.privateKey());
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authManager(), algorithm);
        customAuthenticationFilter.setFilterProcessesUrl("/api/v0/login");
            return http
                    .cors()
                    .and()
                    .csrf()
                    .disable()
                    .authorizeRequests( auth -> auth
                            .antMatchers(HttpMethod.POST, "/api/v0/users", "/api/v0/login").permitAll()
                            .antMatchers(HttpMethod.GET, "/api/v0/users/**").hasAnyAuthority(ROLES.ROLE_USER.toString(), ROLES.ROLE_ADMIN.toString())
                            .antMatchers(HttpMethod.DELETE, "/api/v0/users/**").hasAnyAuthority(ROLES.ROLE_USER.toString(), ROLES.ROLE_ADMIN.toString())
                            .anyRequest().authenticated()
                    )
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilter(customAuthenticationFilter)
                    .addFilterAfter(new CustomAuthorizationFilter(algorithm), UsernamePasswordAuthenticationFilter.class)
                    .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("POST", "GET"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
