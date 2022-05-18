package org.pjj.java8;

import org.junit.Test;
import org.pjj.java8.entity.Employee;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;

/**
 *
 * 一、方法引用: 若 Lambda体 中的内容有方法已经实现了, 我们可以使用"方法引用"
 *      (可以理解为方法引用是 Lambda表达式的另一种表现形式)
 *      (我的理解  就是进一步简化  Lambda表达式)
 *
 * 主要有三种语法格式:
 *      (但是要注意 使用该方法引用(Lambda) 有一个前提 需要实现的接口的抽象方法中的参数, 返回值 必须与 Lambda体(方法引用)中的方法 参数, 返回值 一致)
 * 对象::实例方法名
 *
 * 类::静态方法名
 *
 * 类::实例方法名
 *
 *
 * 二、构造器引用:
 *
 * 格式: ClassName::new
 *
 * 注意: 需要调用的构造器的参数列表 要与 函数式接口中抽象方法的参数列表保持一致
 *
 *         // Supplier<Employee> supplier = () -> new Employee();
 *         // 构造器引用 (Lambda表达式实现的 Supplier接口中的 抽象方法是无参的 那么 Employee::new 就是调用的无参构造)
 *         // 如果 Supplier接口中的 抽象方法是有一个参数的, 则 Employee::new 就是调用的一个参数的构造 (如果没有一个参数的构造 估计会报错)
 *         Supplier<Employee> supplier1 = Employee::new;
 *
 *
 * 三、数组引用:
 *
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

    /**
     * 类::静态方法名
     */
    @Test
    public void test3() {
        // Lambda表达式
        Comparator<Integer> comparator = (a, b) -> Integer.compare(a, b);
        System.out.println(comparator.compare(100, 200));// -1

        // 方法引用
        Comparator<Integer> comparator1 = Integer::compare;
        System.out.println(comparator1.compare(200, 100));// 1
    }

    /**
     * 类::实例方法名  (形参::实例方法名)
     *
     * 当 Lambda表达式              (a, b) -> a.equals(b); 的第一个参数 是这个方法的调用者, 而第二个参数是要调用方法的参数时,
     * 我们就可以使用 类名::实例方法名 String::equals;
     */
    @Test
    public void test4() {
        // Lambda表达式
        BiPredicate<String, String> biPredicate = (a, b) -> a.equals(b);

        // 方法引用
        BiPredicate<String, String> biPredicate1 = String::equals;
    }

    /**
     * 构造器引用  类名::new;
     */
    @Test
    public void test5() {
        // Lambda表达式
        Supplier<Employee> supplier = () -> new Employee();
//        Supplier<Employee> supplier = () -> new Employee(1L, "xiaoming", 18, new BigDecimal(3.33));

        // 构造器引用 (Lambda表达式实现的 Supplier接口中的 抽象方法是无参的 那么 Employee::new 就是调用的无参构造)
        // 如果 Supplier接口中的 抽象方法是有一个参数的, 则 Employee::new 就是调用的一个参数的构造 (如果没有一个参数的构造 估计会报错)
        Supplier<Employee> supplier1 = Employee::new;
        System.out.println(supplier1.get());


        // 测试 构造器引用 (有一个参数的构造)
        // Lambda 表达式
        Function<String, Employee> function = (name) -> new Employee(name);
        // 构造器引用 由于 Function 中的 抽象方法 apply 需要一个入参, 一个返回值 所以 Employee::new; 调用的也是一个参数的构造
        Function<String, Employee> function1 = Employee::new;
        System.out.println(function1.apply("小黑"));


        // 测试 构造器引用 (有两个参数的构造)
        BiFunction<Integer, String, Employee> biFunction = Employee::new;
        System.out.println(biFunction.apply(18, "小黑子"));
    }

    /**
     * 数组引用
     */
    @Test
    public void test6() {
        // Lambda Function接口 抽象方法 apply 输入一个 Integer 返回一个 String[]
        Function<Integer, String[]> function = (x) -> new String[x];
        String[] apply = function.apply(10);
        System.out.println(apply.length);

        // 数组引用
        Function<Integer, String[]> function1 = String[]::new;
        String[] apply1 = function1.apply(20);
        System.out.println(apply1.length);

        // 数组引用 输入一个 Integer len 返回一个 初始化容量为len 的 List<Integer>     该构造器 public ArrayList(int initialCapacity)
        Function<Integer, List<Integer>> function2 = ArrayList::new;
        System.out.println(function2.apply(100));
    }


}
