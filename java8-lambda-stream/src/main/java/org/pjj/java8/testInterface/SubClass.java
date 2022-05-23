package org.pjj.java8.testInterface;

/**
 * 接口 MyFun8 中有默认方法 getHello()
 * 父类 MyParentClass 中有方法 getHello()
 *
 * 那么本类中 的 getHello() 方法 到底是来自父类的getHello() 还是 接口中的 getHello()?
 *      根据 ”类优先” 原则, getHello()方法应该是来自父类的getHello()方法
 *
 * 如果不继承父类, 实现两个接口 两个接口中都有同一个默认方法 default String getHello()
 * 那么 本类 必须实现 getHello()方法, 可以自己实现, 也可以调用接口的默认方法 如: MyFun88.super.getHello();
 *
 * @author PengJiaJun
 * @Date 2022/05/23 15:12
 */
public class SubClass extends MyParentClass implements MyFun8<String>, MyFun88 {

    @Override
    public String fun(String s) {
        return null;
    }

    @Override
    public String getHello() {
        return MyFun88.super.getHello();
    }

}
