package ozone.com.eproz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final ProblemDetailSecurityHandler problemHandler;

    public SecurityConfig(ProblemDetailSecurityHandler problemHandler) {
        this.problemHandler = problemHandler;
    }

    @Bean
    public UserDetailsService userDetailsService(
            PasswordEncoder encoder,
            @Value("${pis.security.officer-password}") String officerPassword,
            @Value("${pis.security.approver-password}") String approverPassword,
            @Value("${pis.security.admin-password}") String adminPassword) {

        return new InMemoryUserDetailsManager(
                User.withUsername("officer")
                        .password(encoder.encode(officerPassword))
                        .authorities("ROLE_OFFICER")
                        .build(),
                User.withUsername("approver")
                        .password(encoder.encode(approverPassword))
                        .authorities("ROLE_APPROVER")
                        .build(),
                User.withUsername("admin")
                        .password(encoder.encode(adminPassword))
                        .authorities("ROLE_ADMIN", "ROLE_APPROVER", "ROLE_OFFICER")
                        .build());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/error", "/favicon.ico", "/actuator/health",
                                "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
                                "/swagger-resources/**", "/webjars/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/staffs").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/staffs").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/users").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(basic -> basic.authenticationEntryPoint(problemHandler))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(problemHandler)
                        .accessDeniedHandler(problemHandler))
                .build();
    }
}
