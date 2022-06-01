package org.pjj.common.valid.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义注解 规定属性的值只能是 vals 中的值
 *
 * JSR303规范规定 自定义注解必须要有 以下三个属性
 * 	String message() default "{javax.validation.constraints.NotEmpty.message}";
 * 	Class<?>[] groups() default { };
 * 	Class<? extends Payload>[] payload() default { };
 *
 */
@Documented
@Constraint(validatedBy = {ListValueConstraintValidator.class}) // 让 自定义注解 与 自定义校验器 产生关联, 可以关联多个自定义校验器, 比如再关联一个处理Long型的校验器
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface ListValue {

    // 错误信息是从 ValidationMessages.properties 中取出的
    String message() default "{org.pjj.gulimall.common.valid.annotation.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    int[] vals() default { };

}
