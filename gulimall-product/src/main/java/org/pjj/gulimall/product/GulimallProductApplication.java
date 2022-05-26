package org.pjj.gulimall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

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
 *
 * 2、逻辑删除
 *      1) 配置全局的逻辑删除规则 (如果 逻辑删除值 默认于mybatis默认的一样 可以省略该配置)
 *      mybatis-plus:
 *        global-config:
 *          db-config:
 *            logic-delete-field: flag # 全局逻辑删除的实体字段名(mybatis-plus 3.3.0后, 配置后可以忽略不配置步骤 2) (一般还是使用 步骤2 @TableLogic)
 *            logic-delete-value: 1 # 逻辑已删除值(默认为 1)
 *            logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)
 *
 *      2) 实体类字段上加上@TableLogic注解
 *      @TableLogic
 *      private Integer deleted;
 *
 *      3) 配置逻辑删除 删除的组件Bean (mybatis-plus 3.1.1 开始不需要这一步)
 *
 * @author PengJiaJun
 * @Date 2022/5/7 14:15
 */
@SpringBootApplication
@EnableDiscoveryClient //开启nacos服务注册与发现
@MapperScan("org.pjj.gulimall.product.dao")
public class GulimallProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(GulimallProductApplication.class, args);
    }
}
