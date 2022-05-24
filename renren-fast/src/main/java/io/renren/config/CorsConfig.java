/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

// gateway中配置了跨域, 这里就不单独配置了
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*") // 将 springboot版本由 2.6.6 改为 2.3.7.RELEASE 后, allowedOriginPatterns("*") 失效, 修改后ok
////            .allowedOriginPatterns("*")
//            .allowCredentials(true)
//            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//            .maxAge(3600);
//    }
}