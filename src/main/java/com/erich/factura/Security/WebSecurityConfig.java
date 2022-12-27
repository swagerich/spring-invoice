package com.erich.factura.Security;

import com.erich.factura.Configuration.SecurityConfig;
import com.erich.factura.Handler.LoginSuccesHandler;
import com.erich.factura.Security.Jwt.JwtRequestFilter;
import com.erich.factura.Security.Jwt.Impl.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final SecurityConfig securityConfiguration;

    private final LoginSuccesHandler loginSuccesHandler;
    private final JwtToken jwtToken;
    private final AuthenticationConfiguration configuration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/listar", "/swagger-ui-invoice.html/**")
                .permitAll()
                .requestMatchers("/listar-rest/**").hasAnyRole("ADMIN")
                .requestMatchers("/ver/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/uploads/**").hasAnyRole("USER")
                .requestMatchers("/form/**").hasAnyRole("ADMIN")
                .requestMatchers("/delete/**").hasAnyRole("ADMIN")
                .requestMatchers("/invoice/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                /*.and().formLogin()
                .successHandler(loginSuccesHandler).loginPage("/login")
                .permitAll()
                .and().logout()
                .permitAll()
                .and().exceptionHandling().accessDeniedPage("/error_403");*/
                .and().addFilter(new JwtRequestFilter(configuration.getAuthenticationManager(), jwtToken));


        http.authenticationProvider(securityConfiguration.authenticationProvider());
        return http.build();
    }
    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        return http.build();
    }*/

   /* @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        AuthenticationConfiguration configuration = new AuthenticationConfiguration();
        return configuration.getAuthenticationManager();
    }
*/
}
