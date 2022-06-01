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
 * 3、JSR303
 *      1) 添加依赖 (不知道老师为什么不用加, 而我不加就没有, 但是我明明通过 按两下Shift搜索@NotNull却可以搜到, 很奇怪)
 *          <dependency>
 *             <groupId>jakarta.validation</groupId>
 *             <artifactId>jakarta.validation-api</artifactId>
 *         </dependency>
 *         <dependency> 不加入该依赖 JSR303校验注解不生效, 很奇怪
 *             <groupId>org.hibernate</groupId>
 *             <artifactId>hibernate-validator</artifactId>
 *             <version>6.2.0.Final</version>
 *         </dependency>
 *
 *      2) 给Bean添加校验注解(给实体类中的属性加校验注解) javax.validation.constraints 这个包下的都是校验注解 @NotNull, @NotBlank, @NotEmpty, ...
 *          例如:  表示 这个属性name不能为空, 空字符串也不行 最少要有一个字符
 *              @NotBlank
 * 	            private String name;
 *
 * 	    3) 在需要使用校验的地方 加@Valid注解  只有在标注了 @Valid 后 实体类中的 @NotNull等JSR303校验注解才会生效
 * 	        例如: 接收一个 BrandEntity brand 除了在 BrandEntity 内部给需要校验的属性加上 @NotNull 等注解
 * 	            还需要 在需要对 BrandEntity 类型的对象 真正需要校验的地方加注解 @Valid, 否则属性上的 @NotNull 等注解是不会生效的.
 * 	            public R save(@RequestBody @Valid BrandEntity brand)
 *
 * 	    4) 效果: 校验错误后会响应简单的错误信息, 具体的错误信息 在控制台打印 级别为 WARN (会抛出异常)
 *
 * 	    5) 在需要校验的bean后紧跟一个BindingResult, 就可以获取到校验的结果
 * 	        例如: public R save(@Valid @RequestBody BrandEntity brand, BindingResult bindingResult)
 * 	            校验结果全封装到 bindingResult中了. 可以根据bindingResult对象得到全部校验错误信息, 然后处理后返回
 *
 * 	    6) 定义统一异常处理类 GulimallExceptionControllerAdvice.java 统一处理错误信息, 并返回
 *
 * 	    7) 分组校验
 * 	        1. @NotBlank(message = "品牌名必须提交", groups = {AddGroup.class, UpdateGroup.class}) // 做品牌新增 和 品牌修改时 name 都不能为空
 * 	        给校验注解 标注什么情况需要进行什么注解
 * 	        2.@Validated(value = AddGroup.class)
 * 	        3. 默认没有指定分组的校验注解 @NotBlank 等... 在分组校验情况下 不会生效, 只会在没使用分组校验下生效即使用 @Valid
 *
 *      8) 自定义校验
 *          1. 编写一个自定义的校验注解
 *          2. 编写一个自定义的校验器 ConstraintValidator (一个校验注解 可以对应多个 自定义校验器, 处理多种不同类型的校验值)
 *          3. 关联自定义的校验器和校验注解
 *                  @Documented
 *                  @Constraint(validatedBy = {ListValueConstraintValidator.class}) // 让 自定义注解 与 自定义校验器 产生关联
 *                  @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
 *                  @Retention(RUNTIME)
 *                  public @interface ListValue
 *
 * 4、统一异常处理
 *      @ControllerAdvice
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
