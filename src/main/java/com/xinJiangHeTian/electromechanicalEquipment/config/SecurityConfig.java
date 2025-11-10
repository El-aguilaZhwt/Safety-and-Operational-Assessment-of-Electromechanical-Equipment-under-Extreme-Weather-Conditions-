package com.xinJiangHeTian.electromechanicalEquipment.config;

import com.xinJiangHeTian.electromechanicalEquipment.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * @author El aguila
 * @version 1.0
 * @description: cors配置和接口白名单
 * @date 2025/11/5 10:30
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 禁用 CSRF
                .csrf(csrf -> csrf.disable())

                // 启用 CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 会话管理 - 无状态
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 授权配置
                .authorizeHttpRequests(authz -> authz
                        // 公开接口
                        .requestMatchers(
//                                "/weather/**",
//                                "/user/**",
//                                "/device/**",
                                "/auth/login",
                                "/auth/register",
                                "/auth/validate",
                                "/auth/**",
                                "/posts/public/**",
                                "/albums/public/**",
                                "/photos/public/**",
                                "/test/**",
                                "/error",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/webjars/**"
                        ).permitAll()

                        // 管理员接口
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // 需要认证的接口
                        .anyRequest().authenticated()
                )

                // 添加 JWT 过滤器
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}
