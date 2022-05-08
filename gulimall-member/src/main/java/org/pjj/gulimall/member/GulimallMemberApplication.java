package org.pjj.gulimall.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 1、想要远程调用别的服务
 *      1) 引入open-feign
 *      2) 编写一个接口, 告诉SpringCloud这个接口想要远程调用服务
 *          1. 声明接口的每一个方法都是调用哪个远程服务的哪个请求 @FeignClient(value = "gulimall-coupon") @RequestMapping("/coupon/coupon/member/list")
 *      3) 开启远程调用功能 @EnableFeignClients
 */
@SpringBootApplication
@EnableDiscoveryClient //开始nacos服务注册与发现
@EnableFeignClients(basePackages = "org.pjj.gulimall.member.feign") //开启Feign
@MapperScan("org.pjj.gulimall.member.dao")
public class GulimallMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallMemberApplication.class, args);
    }

}
