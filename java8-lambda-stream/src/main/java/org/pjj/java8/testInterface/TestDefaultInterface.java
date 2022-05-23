package org.pjj.java8.testInterface;

/**
 * 测试 接口中的 默认方法 与 静态方法
 *
 * 接口默认方法的 ”类优先” 原则
 * 若一个接口中定义了一个默认方法, 而另外一个父类或接口中又定义了一个同名的方法时
 *      1. 选择父类中的方法。如果一个父类提供了具体的实现, 那么接口中具有相同名称和参数的默认方法会被忽略。
 *      2. 接口冲突。如果一个父接口提供一个默认方法, 而另一个接口也提供了一个具有相同名称和参数列表的方法（不管方法是否是默认方法）, 那么必须覆盖该方法来解决冲突
 *
 * SubClass 如果不继承父类, 实现两个接口 两个接口中都有同一个默认方法 default String getHello()
 * 那么 SubClass 必须实现 getHello()方法, 可以自己实现, 也可以调用接口的默认方法 如: MyFun88.super.getHello();
 *
 * @author PengJiaJun
 * @Date 2022/05/23 15:15
 */
public class TestDefaultInterface {

    public static void main(String[] args) {
        // SubClass 继承自 MyParentClass 类, 实现了 MyFun8 接口
        // 父类中有方法 public String getHello()
        // 接口中有方法 default String getHello()
        // 那么 SubClass类中的 getHello() 方法是来自哪的???
        SubClass subClass = new SubClass();
        System.out.println(subClass.getHello());//hello MyParentClass 通过打印可以发现 最终实现是 MyParentClass 中的 getHello()

        MyFun8.show();// MyFun8<T>接口中的 静态方法 show()
    }

}
