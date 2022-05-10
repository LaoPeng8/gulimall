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
 *  2. 细节
 *      1) 命名空间: 配置隔离 (重点)
 *          默认 public(保留空间); 默认新增的所有配置都在public空间.
 *          开发 dev, 测试 test, 生产 prop 三个命名空间, 来做环境隔离 各自的配置放在各种的命名空间中
 *          注意: 需要在bootstrap.properties中配置 使用哪个命名空间下的配置 spring.cloud.nacos.config.namespace=命名空间ID
 *
 *          每一个微服务之间互相隔离配置, 每一个微服务都创建自己的命名空间, 只加载自己命名空间下的所有配置
 *
 *      2) 配置集: 所有配置的集合
 *
 *      3) 配置集ID: 类似配置文件名
 *          Data ID: 类似文件名
 *
 *      4) 配置分组: (重点)
 *          默认所有的配置集都属于 DEFAULT_GROUP; 这个配置分组
 *          1111, 618, 1212
 *      每个微服务创建自己的命名空间, 使用配置分组区分环境 dev, test, prop
 *
 *  3. 同时加载多个配置集
 *      1) 微服务任何配置信息, 任何配置文件都可以放在配置中心
 *      2) 只需要在bootstrap.properties说明加载配置中心哪些配置文件即可
 *      3) @Value, @ConfigurationProperties ...
 *      以前SpringBoot任何方法从配置文件中获取值的方法, 都能使用. (配置中心 与 本地properties同时配置 同一个配置, 配置中心优先级高)
 *
 */
@SpringBootApplication
@EnableDiscoveryClient //开始nacos服务注册与发现
@MapperScan("org.pjj.gulimall.coupon.dao")
public class GulimallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallCouponApplication.class, args);
    }

}
