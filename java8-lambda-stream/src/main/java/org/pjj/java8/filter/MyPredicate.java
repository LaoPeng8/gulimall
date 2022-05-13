package org.pjj.java8.filter;

/**
 * @author PengJiaJun
 * @Date 2022/5/13 17:13
 */
@FunctionalInterface //被该注解修饰的接口, 只能有一个抽象方法
public interface MyPredicate<T> {

    public boolean test(T t);

}
