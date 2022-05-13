package org.pjj.java8.fun;

/**
 * 被 @FunctionalInterface 修饰的接口, 接口中 只能有一个抽象方法, 如一个没有或有多个 编译就会报错
 * @author PengJiaJun
 * @Date 2022/5/13 22:24
 */
@FunctionalInterface //接口中只有一个抽象方法的接口, 称为函数式接口. 可以使用注解 @FunctionalInterface 修饰 可以检查是否是函数式接口
public interface MyFun {

    public Integer getValue(Integer num);

}
