package com.example.task_2.config;

import com.example.task_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private  CustomUserDetails customUserDetails;
    private JwtAuthEntryPoint jwtAuthEntryPoint;
@Autowired
    public SecurityConfig(CustomUserDetails customUserDetails,JwtAuthEntryPoint jwtAuthEntryPoint) {
        this.customUserDetails = customUserDetails;
        this.jwtAuthEntryPoint= jwtAuthEntryPoint;
    }
@Bean
  public   SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    http.authorizeHttpRequests(configurer->
            configurer
            .requestMatchers(HttpMethod.POST,"/api/auth/**").permitAll())
    ;
    http.httpBasic(Customizer.withDefaults());
    http.csrf(csrf-> csrf.disable());
//    http.exceptionHandling((Customizer<ExceptionHandlingConfigurer<HttpSecurity>>) jwtAuthEntryPoint);
    http.exceptionHandling(exceptionHandlingConfigurer ->
    exceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthEntryPoint));
    http .sessionManagement(sessionManagementConfigurer ->
            sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
  http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
}
@Bean
public  JwtAuthFilter jwtAuthFilter(){
    return  new JwtAuthFilter();
}
@Bean
    public  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
    return authenticationConfiguration.getAuthenticationManager();
}
@Bean
    PasswordEncoder passwordEncoder(){
    return  new BCryptPasswordEncoder();
}


}
