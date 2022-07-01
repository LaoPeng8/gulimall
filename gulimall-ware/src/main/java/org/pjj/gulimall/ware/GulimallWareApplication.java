package org.pjj.gulimall.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement //开启事务管理 (之前product服务中也没该注解 为什么 @Transactional 也生效了???)
@SpringBootApplication
@EnableDiscoveryClient //开启nacos服务注册与发现
@MapperScan("org.pjj.gulimall.ware.dao")
public class GulimallWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallWareApplication.class, args);
    }

}
