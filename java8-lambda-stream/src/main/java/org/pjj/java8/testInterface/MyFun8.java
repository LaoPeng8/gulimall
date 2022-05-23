package org.pjj.java8.testInterface;

/**
 * Java 8 中允许接口中包含具有具体实现的方法， 该方法称为 “默认方法”，默认方法使用 default 关键字修饰。
 * @author PengJiaJun
 * @Date 2022/05/23 14:52
 */
@FunctionalInterface
public interface MyFun8<T> {

    T fun(T t);

    /**
     * Java 8 中允许接口中包含具有具体实现的方法， 该方法称为 “默认方法”，默认方法使用 default 关键字修饰。
     * @return
     */
    default String getHello() {
        return "HELLO MyFun8";
    }

    static void show() {
        System.out.println("MyFun8<T>接口中的 静态方法 show()");
    }
}
