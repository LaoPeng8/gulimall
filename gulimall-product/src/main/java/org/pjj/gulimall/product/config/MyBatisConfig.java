package org.pjj.gulimall.product.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author PengJiaJun
 * @Date 2022/06/04 17:04
 */
@Configuration
@EnableTransactionManagement
@MapperScan("org.pjj.gulimall.product.dao")
public class MyBatisConfig {

    /**
     * mybatis-plus 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作, true调会到首页, false继续请求 默认false
        paginationInterceptor.setOverflow(true);
        // 设置最大单页限制数量, 默认500一条, -1 不受限制
        paginationInterceptor.setLimit(1000);

        return paginationInterceptor;
    }

}
