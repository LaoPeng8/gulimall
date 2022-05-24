package org.pjj.gulimall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author PengJiaJun
 * @Date 2022/05/24 17:40
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 1. 配置跨域
        corsConfiguration.addAllowedHeader("*"); // 允许哪些请求头 跨域
        corsConfiguration.addAllowedMethod("*"); // 允许哪些请求方式 跨域
        corsConfiguration.addAllowedOrigin("*"); // 允许哪些请求来源 跨域
        corsConfiguration.setAllowCredentials(true); // 是否允许携带cookie跨域

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);// /** 表示任意路径都允许跨域

        return new CorsWebFilter(source);
    }

}
