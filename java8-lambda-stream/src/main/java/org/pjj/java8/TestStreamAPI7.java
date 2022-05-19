package org.pjj.java8;

import org.junit.Test;
import org.pjj.java8.entity.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Java8 中有两大最为重要的改变。第一个是 Lambda 表达式; 另外一个则是 Stream API(java.util.stream.*)。
 *
 * Stream 是 Java8 中处理集合的关键抽象概念，它可以指定你希望对集合进行的操作，可以执行非常复杂的查找、过滤和映射数据等操作。
 * 使用Stream API对集合数据进行操作，就类似于使用SQL执行的数据库查询。也可以使用Stream API 来并行执行操作。
 * 简而言之，Stream API提供了一种高效且易于使用的处理数据的方式。
 *
 * 流(Stream)到底是什么呢?
 * 是数据渠道，用于操作数据源（集合、数组等）所生成的元素序列。
 *
 * 注意:
 * ①Stream自己不会存储元素。
 * ②Stream不会改变源对象。相反，他们会返回一个持有结果的新Stream。
 * ③Stream操作是延迟执行的。这意味着他们会等到需要结果的时候才执行。
 *
 *
 * 一、Stream 的三个操作步骤
 *
 * 1. 创建 Stream
 *
 * 2. 中间操作
 *
 * 3. 终止操作(终端操作)
 *
 * @author PengJiaJun
 * @Date 2022/05/18 19:32
 */
public class TestStreamAPI7 {

    /**
     * 创建 Stream  (有四种方式创建 Stream)
     */
    @Test
    public void test1() {
        //1. 可以通过 Collection 系列集合提供的 stream() 或 parallelStream()
        List<String> list = Arrays.asList("小明", "小黑", "小智", "喷火龙", "皮卡丘", "大宝", "G102");
        Stream<String> stream = list.stream();
        stream.forEach(System.out::println);

        //2. 通过 Arrays 中的静态方法 stream() 获取数组流
        Employee[] employees = new Employee[10];
        Stream<Employee> stream1 = Arrays.stream(employees);

        //3. 通过 Stream 类中的静态方法 of()
        Stream<String> stream2 = Stream.of("aa", "bb", "cc", "dd");

        //4. 创建无限流 (两种方式: 迭代, 生成)
        //迭代 从 0 开始, 怎么迭代? 得看 传入的 UnaryOperator 对象, 也就是第二个参数 (x) -> x + 2 使用 Lambda表达式的方式 实现, 表示 每次迭代 x + 2
        //UnaryOperator 对象 中的抽象方法, 实质就是一个Function<T, T> 输入一个 x, 返回一个 x 即 (x) -> x + 2
        Stream.iterate(0, (x) -> x + 2)
                .limit(10) //即 虽然是死循环 但是只截取10条, 然后就退出了
                //forEach 中的抽象方法 需要一个 Consumer<T> 对象, 即输入一个参数, 无返回值, 即 (x) -> System.out.println(x)
                .forEach((x) -> System.out.println(x)); // 死循环, 打印 x, x每次+2
//                .forEach(System.out::println);// 当 Lambda体 为一个已有的方式时, 可以写为 方法引用的方式

        //生成 (无限流 所以生成出来的流 就相当于死循环)
        Stream.generate(Math::random) //这个方法会产生一个新的流, 而流的内容就相当于是一个数组 而数组的内容就是 一直调用 Math.random()方法 生成的
                .limit(10)
                .forEach(System.out::println);//相当于就是 forEach循环, 然后内容只有一句话 System.out.println(流的内容);

    }

}
