package br.com.ifpe.educamente_api.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.ifpe.educamente_api.modelo.acesso.Conta;
import br.com.ifpe.educamente_api.modelo.acesso.Perfil;
import br.com.ifpe.educamente_api.modelo.seguranca.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

        private final AuthenticationProvider authenticationProvider;
        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter,
                        AuthenticationProvider authenticationProvider) {
                this.authenticationProvider = authenticationProvider;
                this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                .csrf(c -> c.disable())
                                .authorizeHttpRequests(authorize -> authorize

                                                .requestMatchers(HttpMethod.POST, "/api/usuario").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/api/auth").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/api/funcionario").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/api/redefinir/*").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api-docs/*").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/swagger-ui/*").permitAll()

                                                .requestMatchers(HttpMethod.POST, "/api/notificacao/*").hasAnyAuthority(
                                                                Perfil.ROLE_USUARIO,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                                                // PERMISSÕES DE ACESSO DE USUARIO
                                                .requestMatchers(HttpMethod.PUT, "/api/usuario/*").hasAnyAuthority(
                                                                Perfil.ROLE_USUARIO,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                                                .requestMatchers(HttpMethod.DELETE, "/api/usuario/*").hasAnyAuthority(
                                                                Perfil.ROLE_USUARIO,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                                                .requestMatchers(HttpMethod.GET, "/api/usuario").hasAnyAuthority(
                                                                Perfil.ROLE_USUARIO,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                                                .requestMatchers(HttpMethod.GET, "/api/usuario/*").hasAnyAuthority(
                                                                Perfil.ROLE_USUARIO,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                                                // PERMISSÕES DE ACESSO DE FUNCIONARIO
                                                .requestMatchers(HttpMethod.PUT, "/api/funcionario/*").hasAnyAuthority(
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                                                .requestMatchers(HttpMethod.DELETE, "/api/funcionario/*")
                                                .hasAnyAuthority(
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                                                .requestMatchers(HttpMethod.GET, "/api/funcionario").hasAnyAuthority(
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                                                .requestMatchers(HttpMethod.GET, "/api/funcionario/*").hasAnyAuthority(
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                                                // PERMISSÕES DE ACESSO DE SUGESTAO
                                                .requestMatchers(HttpMethod.PUT, "/api/sugestao/*").hasAnyAuthority(
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                                                .requestMatchers(HttpMethod.DELETE, "/api/sugestao/*").hasAnyAuthority(
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                                                .requestMatchers(HttpMethod.GET, "/api/sugestao").hasAnyAuthority(
                                                                Perfil.ROLE_USUARIO,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                                                .requestMatchers(HttpMethod.GET, "/api/sugestao/*").hasAnyAuthority(
                                                                Perfil.ROLE_USUARIO,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                                                .requestMatchers(HttpMethod.POST, "/api/sugestao/*").hasAnyAuthority(
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                                                // PERMISSÕES DE ACESSO DE COMENTARIO
                                                .requestMatchers(HttpMethod.PUT, "/api/comentario/*").hasAnyAuthority(
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN, Perfil.ROLE_USUARIO)

                                                .requestMatchers(HttpMethod.DELETE, "/api/comentario/*")
                                                .hasAnyAuthority(
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN, Perfil.ROLE_USUARIO)

                                                .requestMatchers(HttpMethod.GET, "/api/comentario").hasAnyAuthority(
                                                                Perfil.ROLE_USUARIO,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                                                .requestMatchers(HttpMethod.GET, "/api/comentario/*").hasAnyAuthority(
                                                                Perfil.ROLE_USUARIO,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                                                .requestMatchers(HttpMethod.POST, "/api/comentario/*").hasAnyAuthority(
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN, Perfil.ROLE_USUARIO)

                                                // PERMISSÕES DE ACESSO DE ALIMENTACAO
                                                .requestMatchers(HttpMethod.DELETE, "/api/alimentacao/*")
                                                .hasAnyAuthority(
                                                                Perfil.ROLE_USUARIO,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                                                .requestMatchers(HttpMethod.GET, "/api/alimentacao").hasAnyAuthority(
                                                                Perfil.ROLE_USUARIO,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                                                .requestMatchers(HttpMethod.GET, "/api/alimentacao/*").hasAnyAuthority(
                                                                Perfil.ROLE_USUARIO,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                                                // PERMISSÕES DE ACESSO DE SAUDEMENTAL
                                                .requestMatchers(HttpMethod.DELETE, "/api/saudemental/*")
                                                .hasAnyAuthority(
                                                                Perfil.ROLE_USUARIO,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                                                .requestMatchers(HttpMethod.GET, "/api/saudemental").hasAnyAuthority(
                                                                Perfil.ROLE_USUARIO,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                                                .requestMatchers(HttpMethod.GET, "/api/saudemental/*").hasAnyAuthority(
                                                                Perfil.ROLE_USUARIO,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                                                // PERMISSÕES DE ACESSO DE COMPORTAMENTO
                                                .requestMatchers(HttpMethod.DELETE, "/api/comportamento/*")
                                                .hasAnyAuthority(
                                                                Perfil.ROLE_USUARIO,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN
                                                                )

                                                .requestMatchers(HttpMethod.GET, "/api/comportamento").hasAnyAuthority(
                                                                Perfil.ROLE_USUARIO,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                                                .requestMatchers(HttpMethod.GET, "/api/comportamento/*")
                                                .hasAnyAuthority(
                                                                Perfil.ROLE_USUARIO,
                                                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                                                .anyRequest().authenticated()

                                )
                                .sessionManagement((session) -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        public CorsConfigurationSource corsConfigurationSource() {

                CorsConfiguration configuration = new CorsConfiguration();

                configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                configuration.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }
}