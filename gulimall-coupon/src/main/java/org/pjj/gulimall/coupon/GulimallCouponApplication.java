package org.pjj.gulimall.coupon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *  1. 如何使用Nacos作为配置中心统一管理配置
 *      1) 引入依赖: spring-cloud-starter-alibaba-nacos-config
 *      2) 创建一个 bootstrap.properties 中配置 最核心的两项:
 *          spring.application.name=gulimall-coupon # 服务名
 *          spring.cloud.nacos.config.server-addr:127.0.0.1:8848 #nacos地址
 *      3) 在nacos中新建配置 DataID为 服务名.properties然后在该配置中写配置
 *      4) 如果需要动态刷新配置 则还需要
 *          @RefreshScope //刷新配置, 使其能从nacos配置中心动态获取配置文件的值 (没有该注解, 则不能从nacos动态读取配置)
 *          与
 *          @Value("${配置名}") //获取配置的值
 *      注意: 配置中心 与 本地properties同时配置 同一个配置, 配置中心优先级高
 *
 *  2.
 */
@SpringBootApplication
@EnableDiscoveryClient //开始nacos服务注册与发现
@MapperScan("org.pjj.gulimall.coupon.dao")
public class GulimallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallCouponApplication.class, args);
    }

}
