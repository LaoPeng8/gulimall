package org.pjj.java8;

import org.junit.Test;
import org.pjj.java8.entity.Employee;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * 一、Stream 的三个操作步骤
 *
 * 1. 创建 Stream
 *
 * 2. 中间操作
 *      多个中间操作可以连接起来形成一个流水线, 除非流水线上触发终止操作, 否则中间操作不会执行任何的处理! 而在终止操作时一次性全部处理, 称为“惰性求值”.
 *      * 筛选与切片
 *      * filter(Predicate) - 接收 Lambda ,从流中排除某些元素. limit - 截断流,使其元素不超过给定数量.
 *      * distinct - 筛选, 通过流所生成元素的 hashCode()和equals()去除重复元素
 *      * limit(long maxSize) 截断流, 使其不超过给定数量
 *      * skip(n) - 跳过元素, 返回一个扔掉了前n个元素的流.若流中元素不足 n 个, 则返回一个空流.
 *
 * 3. 终止操作(终端操作)
 * @author PengJiaJun
 * @Date 2022/05/19 21:20
 */
public class TestStreamAPI8 {

    List<Employee> employeeList = Arrays.asList(
            new Employee(1L, "张三", 22, new BigDecimal(3000)),
            new Employee(2L, "李四", 21, new BigDecimal(4000)),
            new Employee(6L, "小黑", 37, new BigDecimal(16001.88888)),
            new Employee(5L, "小明", 36, new BigDecimal(12000.2366)),
            new Employee(3L, "王五", 22, new BigDecimal(5000)),
            new Employee(4L, "赵六", 22, new BigDecimal(6000.6666))
    );

    /**
     * 中间操作
     *
     * 多个中间操作可以连接起来形成一个流水线, 除非流水线上触发终止操作, 否则中间操作不会执行任何的处理! 而在终止操作时一次性全部处理, 称为“惰性求值”.
     *
     * 筛选与切片
     * filter(Predicate) - 接收 Lambda ,从流中排除某些元素.
     * distinct - 筛选, 通过流所生成元素的 hashCode()和equals()去除重复元素
     * limit(long maxSize) 截断流, 使其不超过给定数量
     * skip(n) - 跳过元素, 返回一个扔掉了前n个元素的流.若流中元素不足 n 个, 则返回一个空流.
     */
    @Test //内部迭代: 迭代操作由Stream API 完成
    public void test1() {
        // 中间操作: 不会执行任何操作
        Stream<Employee> employeeStream = employeeList.stream() //该流中 是集合所有的元素
                .filter((employee) -> {
                    // 通过 filter 过滤出, 年龄大于 30 的 employee, 然后产生新的流返回 (原有流不会发生改变)
                    System.out.println("Stream API 的 中间操作");
                    return employee.getAge() > 30;
                });


        //没有终止操作, filter() 中的 打印语句 Stream API 的 中间操作 是不会打印的 (也就是 该条语句注释后 中间操作是不会执行的)
        //终止操作: 一次性执行全部内容, 即 “惰性求值”
        employeeStream.forEach(System.out::println);
        //根据打印:  我的理解 即 之前的中间操作 指定定义了这个操作 相当于只是实现了这个接口, 等到真正执行终止操作的时候 会调用该接口 实现过滤
        // 这个打印出来的感觉就像 直接遍历 最原始的集合产生的流(employeeList.stream()) 然后根据 filter 一个一个筛选出 符合该流应该有的数据
        //Stream API 的 中间操作
        //Stream API 的 中间操作
        //Stream API 的 中间操作
        //Employee{id=6, name='小黑', age=37, salary=16001.888880000000426662154495716094970703125}
        //Stream API 的 中间操作
        //Employee{id=5, name='小明', age=36, salary=12000.23660000000018044374883174896240234375}
        //Stream API 的 中间操作
        //Stream API 的 中间操作

    }

    /**
     *
     */
    @Test //外部迭代
    public void test2() {
        Iterator<Employee> iterator = employeeList.iterator();
        while(iterator.hasNext()) { // boolean hasNext(); 是否有下一个遍历对象
            System.out.println(iterator.next());// E next(); 返回遍历对象, 且游标指向下一个遍历对象
        }
    }

    /**
     * limit - 截断流,使其元素不超过给定数量.
     */
    @Test
    public void test3() {
        employeeList.stream()
                .filter(employee -> {
                    System.out.println("短路!");
                    return employee.getAge() < 30;
                })
                .limit(2)//截断流 限制employeeList.stream()只能有2条数据, 多余的截断, 然后产生新的流(原有的流不被改变)
                .forEach(System.out::println);

        // 通过观察打印数据发现: 当limit(2)满足 只能有2条数据后, 就不继续遍历了直接截断流了, 这个过程就叫短路,  这和 filter() 不一样 filter()会遍历完流
        // 短路!
        // Employee{id=1, name='张三', age=22, salary=3000}
        // 短路!
        // Employee{id=2, name='李四', age=21, salary=4000}
    }

}
