package org.pjj.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1、整合MyBatis-Plus
 *      1) 引入依赖
 *          <dependency>
 *             <groupId>com.baomidou</groupId>
 *             <artifactId>mybatis-plus-boot-starter</artifactId>
 *             <version>3.2.0</version>
 *         </dependency>
 *      2) 配置
 *          1、配置数据源
 *              1. 导入数据库驱动
 *              2. application.yml中配置数据源相关信息
 *          2、配置MyBatis-Plus
 *              1. 配置 @mapperScan 扫描 Mapper接口(Dao接口)(扫描 @Mapper 注解)
 *              2. 配置 MyBatis-Plus sql映射文件位置 application.yml
 * @author PengJiaJun
 * @Date 2022/5/7 14:15
 */
@SpringBootApplication
@MapperScan("org.pjj.gulimall.product.dao")
public class GulimallProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }
}
