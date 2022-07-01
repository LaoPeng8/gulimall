package org.pjj.gulimall.ware.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author PengJiaJun
 * @Date 2022/07/02 00:15
 */
@Configuration
@EnableTransactionManagement //开启事务管理 (不配置该注解 @Transactional 注解应该不会生效)
@MapperScan("org.pjj.gulimall.ware.dao")
public class MyBatisConfig {

    /**
     * mybatis-plus 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }

}
