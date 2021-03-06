package org.pjj.java8;

import org.junit.Test;
import org.pjj.java8.fun.MyFun;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * 一、Lambda 表达式的基础语法: Java8中引入了一个新的操作符 "->" 该操作符称为箭头操作符 或 Lambda操作符
 *      箭头操作符将Lambda表达式拆分成了两部分:
 *          左侧: Lambda表达式的参数列表
 *          右侧: Lambda表达式所需要执行的功能, 即 Lambda 体
 *
 * Lambda表达式 就是简化 匿名内部类的一种写法, 其实就是想要 new 接口(); 就必须得实现接口的方法. 匿名内部类是实现的一种方式, Lambda也是一种方式
 *
 * 语法格式一: 无参数, 无返回值
 *      () -> System.out.println("Hello Lambda!");
 *
 * 语法格式二: 有一个参数, 无返回值  (此种情况 Lambda表达式左侧的参数的小括号可以不写)
 *      (x) -> System.out.println(x);
 *      x -> System.out.println(x);//只有一个参数时, 可以省略参数小括号
 *
 * 语法格式三: 有两个及以上的参数, 有返回值, 并且Lambda体中有多条语句
 *      Comparator<Integer> comparator = (a, b) -> {
 *          System.out.println("如果Lambda体中有多条语句, 那么必须使用大括号{}");
 *          return Integer.compare(a, b);
 *      };
 *
 * 语法格式四: 如果Lambda体中只有一条语句, return 和 {} 都可以省略不写
 *      Comparator<Integer> comparator = (a, b) -> Integer.compare(a, b);
 *
 * 语法格式五: Lambda表达式的参数列表的数据类型可以省略不写, 因为JVM编译器可以通过上下文推断出数据类型, 即”数据推断“
 *      但是 只要写了一个参数的类型, 就必须把其他参数的类型也写了, 简单来说: 参数类型要么一个不写, 要么都写
 *      Comparator<Integer> comparator = (Integer a, Integer b) -> Integer.compare(a, b);
 *
 * 左右遇一括号省,   即 Lambda 左侧 或 右侧 只有一个参数(左侧) 或 一条语句(右侧), 那么括号可以省略 左侧省略() 右侧省略{}
 * 左侧推断类型省,   即 Lambda 左侧参数的类型可以省略
 *
 *
 * 二、Lambda 表达式需要 ”函数式接口“ 的支持
 *      函数式接口: 接口中只有一个抽象方法的接口, 称为函数式接口. 可以使用注解 @FunctionalInterface 修饰
 *      可以检查是否是函数式接口
 *
 *
 * @author PengJiaJun
 * @Date 2022/5/13 19:40
 */
public class TestLambda2 {

    /**
     * 语法格式一: 无参数, 无返回值
     * () -> System.out.println("Hello Lambda!");
     */
    @Test
    public void test1() {

        int num = 0;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World" + num);//jdk1.7前, num 必须是 final 修饰的, 这里才能使用, jdk1.8可以直接使用
                                                        //但是 不能 num++, 相当于说 还是 final int num = 0;
            }
        };
        runnable.run();

        System.out.println("-----------------------------------");

        Runnable runnable1 = () -> System.out.println("Hello Lambda" + num);
        runnable1.run();

    }

    /**
     * 语法格式二: 有一个参数, 无返回值
     * (x) -> System.out.println(x);
     * x -> System.out.println(x);//只有一个参数时, 可以省略参数小括号
     */
    @Test
    public void test2() {
        // Consumer接口中有一个方法 void accept(T t); 有一个参数, 没有返回值
        // 此处使用Lambda表达式 的意思就是 相当于免去匿名内部类的方式获取一个 consumer 对象
        // 而是使用 Lambda表达式的方式实现了方法 void accept(T t);
        Consumer<String> consumer = x -> System.out.println(x);
//        Consumer<String> consumer = (x) -> System.out.println(x);

        consumer.accept("还行吧");
    }

    /**
     * 语法格式三: 有两个及以上的参数, 有返回值, 并且Lambda体中有多条语句
     *
     * Comparator<Integer> comparator = (a, b) -> {
     *     System.out.println("如果Lambda体中有多条语句, 那么必须使用大括号{}");
     *     return Integer.compare(a, b);
     * };
     */
    @Test
    public void test3() {
        // Comparator 接口中有一个方法 int compare(T o1, T o2);
        // 此处就是相当于 使用Lambda表达式的方式 实现了 compare方法
        Comparator<Integer> comparator = (a, b) -> {
            System.out.println("如果Lambda体中有多条语句, 那么必须使用大括号{}");
            return Integer.compare(a, b);
        };

        System.out.println(comparator.compare(100, 200));//-1
    }

    /**
     * 语法格式四: 如果Lambda体中只有一条语句, return 和 {} 都可以省略不写
     * Comparator<Integer> comparator = (a, b) -> Integer.compare(a, b);
     */
    @Test
    public void test4() {
        // Comparator 接口中有一个方法 int compare(T o1, T o2);
        // 此处就是相当于 使用Lambda表达式的方式 实现了 compare方法
        Comparator<Integer> comparator = (a, b) -> Integer.compare(a, b); //如果Lambda体中只有一条语句, return 和 {} 都可以省略不写

        System.out.println(comparator.compare(2022, 2020));// 1
    }

    /**
     * 语法格式五: Lambda表达式的参数列表的数据类型可以省略不写, 因为JVM编译器可以通过上下文推断出数据类型, 即”数据推断“
     * 但是 只要写了一个参数的类型, 就必须把其他参数的类型也写了, 简单来说: 参数类型要么一个不写, 要么都写
     * Comparator<Integer> comparator = (Integer a, Integer b) -> Integer.compare(a, b);
     */
    @Test
    public void test5() {
        Comparator<Integer> comparator = (Integer a, Integer b) -> Integer.compare(a, b);

        System.out.println(comparator.compare(2022, 2020));// 1
    }

    public Integer operation(Integer num, MyFun mf) {
        return mf.getValue(num);
    }

    /**
     * 需求: 对一个数进行运算
     */
    @Test
    public void test6() {
        //匿名内部类方式 (帮助理解 Lambda)
        Integer operation = operation(100, new MyFun() {
            @Override
            public Integer getValue(Integer num) {
                return num * num;
            }
        });
        System.out.println(operation);//10000

        // Lambda方式
        Integer operation1 = operation(200, num -> num + num);
        System.out.println(operation1);//400
    }


}
