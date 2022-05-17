package org.pjj.java8.fun;

/**
 * @author PengJiaJun
 * @Date 2022/5/14 10:05
 */
@FunctionalInterface
public interface MyFun3<T, R> {

    public R getValue(T t1, T t2);

}
