package org.pjj.common.valid.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * 一个自定义注解 可以关联多个 自定义校验器
 *
 * ConstraintValidator<ListValue, Integer> 就是处理 ListValue注解 与 校验值是Integer类型
 * ConstraintValidator<ListValue, Long> 就是处理 ListValue注解 与 校验值是Long类型
 *
 *
 * @author PengJiaJun
 * @Date 2022/06/01 17:43
 */
public class ListValueConstraintValidator implements ConstraintValidator<ListValue, Integer> {

    private Set<Integer> set = new HashSet<>();//存放 注解中 vals 的值

    /**
     * 初始化方法
     * @param constraintAnnotation 校验注解, 可以通过校验注解获取 注解的属性值
     */
    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] vals = constraintAnnotation.vals();// @ListValue(vals = {0, 1}) 即 int[] vals = {0, 1}
        for(int val : vals) {
            set.add(val);
        }
    }


    /**
     * 判断是否校验成功
     * @param value 需要校验的值, 即 校验注解写在属性上需要对属性值进行校验, 这个属性值就是 value
     * @param context 需要校验的上下文环境信息
     * @return
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        // 判断 value 值, 是否包含在 set 集合中, 即判断 需要校验的值 是否 包含在 自定义注解上定义的 vals
        if(set.contains(value)) {
            return true;
        }

        return false;
    }
}
