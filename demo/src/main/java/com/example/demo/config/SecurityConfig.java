package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.entity.Result;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:3000"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    config.setMaxAge(3600L);
                    return config;
                }))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/login", "/api/register", "/api/logout").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(
                        conf -> {
                            conf.loginProcessingUrl("/api/login");
                            conf.successHandler(this::handleProcess);
                            conf.failureHandler(this::handleProcess);
                            conf.permitAll();
                        })
                .logout(
                        conf -> {
                            conf.logoutUrl("/api/logout");
                            conf.logoutSuccessHandler(this::handleProcess);
                        })
                .exceptionHandling(
                        conf -> {
                            conf.accessDeniedHandler(this::handleProcess);
                            conf.authenticationEntryPoint(this::handleProcess);
                        });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private void handleProcess(
            HttpServletRequest request,
            HttpServletResponse response,
            Object exceptionOrAuthentication) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();

        if (exceptionOrAuthentication instanceof AuthenticationException authException) {
            // 如果是认证异常，则判断具体是用户名密码错误还是未登录
            if (authException instanceof BadCredentialsException) {
                // 用户名密码错误
                writer.write(Result.error(401, "用户名或密码错误").asJsonString());
            } else {
                // 未登录
                writer.write(Result.error(401, "请先登录").asJsonString());
            }
        } else if (exceptionOrAuthentication instanceof AccessDeniedException) {
            // 权限不足
            writer.write(Result.error(403, "您没有权限访问").asJsonString());
        } else if (exceptionOrAuthentication instanceof Authentication) {
            // 身份验证成功
            writer.write(Result.success((Authentication) exceptionOrAuthentication).asJsonString());
        } else {
            // 其他情况，返回通用错误信息
            writer.write(Result.error(500, "服务器内部错误").asJsonString());
        }
    }

}
