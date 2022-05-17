package org.pjj.java8;

import org.junit.Test;
import org.pjj.java8.entity.Employee;

import java.io.PrintStream;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 *
 * 方法引用: 若 Lambda体 中的内容有方法已经实现了, 我们可以使用"方法引用"
 *      (可以理解为方法引用是 Lambda表达式的另一种表现形式)
 *      (我的理解  就是进一步简化  Lambda表达式)
 *
 * 主要有三种语法格式:
 *
 * 对象::实例方法名
 *
 * 类::静态方法名
 *
 * 类::实例方法名
 *
 * @author PengJiaJun
 * @Date 2022/05/17 23:30
 */
public class TestMethodRef5 {

    /**
     * 对象::实例方法名
     */
    @Test
    public void test1() {
        // Consumer 接口是一个函数型接口 其中有一个方法 void accept(T t); 一个入参, 无返回值
        // 那么我们可以使用 Lambda表达式 来实现 该接口 一个入参 (x) 一个Lambda体无返回值 System.out.println();
        // 如果 Lambda体 中是引用一个已经存在的 或者说 别人以及写好的方法
        // 那我们就可以使用 另一种表现形式 对象::方法 (System.out 是对象, println是实例方法  System.out::println)
        // 但是要注意 使用该方法引用(Lambda) 有一个前提 需要实现的接口的抽象方法中的参数, 返回值 必须与 Lambda体(方法引用)中的方法 参数, 返回值 一致
        Consumer<String> consumer = (x) -> System.out.println("消费了: " + x);

        // 比如: 该Consumer接口中的抽象方法 void accept(T t); 与 方法引用(Lambda) void println(String x);
        // 都是 一个参数, 无返回值
        PrintStream ps = System.out;
        Consumer<String> consumer1 = ps::println;


        // 通过 方法引用的方式 new 出接口 Consumer, 接口中的方法 accept 通过方法引用实现 System.out::println 相当于Lambda体
        Consumer<String> consumer2 = System.out::println;
        consumer2.accept("测试 Lambda表达式 方法引用");
    }

    /**
     * 对象::实例方法名
     */
    @Test
    public void test2() {
        Employee employee = new Employee();
        employee.setName("英国内阁制");

        // Supplier函数型接口 T get(); 无入参, 一个返回值
        // 如果 Lambda体 中是引用一个已经存在的 或者说 别人以及写好的方法
        Supplier<String> supplier = () -> employee.getName();


        // 那么我们可以使用 方法引用的方式实现 对象::实例方法名
        // 前提是 函数型接口的抽象方法 的参数和返回值 与 方法引用 的参数和返回值一致
        Supplier<String> supplier1 = employee::getName;
        String s = supplier1.get();
        System.out.println(s);
    }


}
