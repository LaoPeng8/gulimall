package org.pjj.java8;

import org.junit.Test;
import org.pjj.java8.entity.Employee;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

/**
 * 一、Stream 的三个操作步骤
 *
 * 1. 创建 Stream
 *
 * 2. 中间操作
 *      多个中间操作可以连接起来形成一个流水线, 除非流水线上触发终止操作, 否则中间操作不会执行任何的处理! 而在终止操作时一次性全部处理, 称为“惰性求值”.
 *
 *      * 筛选与切片
 *      * filter(Predicate) - 接收 Lambda ,从流中排除某些元素. limit - 截断流,使其元素不超过给定数量.
 *      * distinct - 筛选, 通过流所生成元素的 hashCode()和equals()去除重复元素
 *      * limit(long maxSize) 截断流, 使其不超过给定数量
 *      * skip(n) - 跳过元素, 返回一个扔掉了前n个元素的流.若流中元素不足 n 个, 则返回一个空流.
 *
 *      * 映射
 *      * map - 接收Lambda, 将元素转换成其他形式或提取信息。
 *      * 接收一个函数作为参数，该函数会被应用到每个元素上, 并将其映射成一个新的元素。
 *      * flatMap - 接收一个函数作为参数, 将流中的每个值都换成另一个流, 然后把所有流连接成一个流
 *
 *      * 排序
 *      * sorted() - 自然排序
 *      * sorted(Comparator com) - 定制排序
 *
 * 3. 终止操作(终端操作)
 *
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
            new Employee(4L, "赵六", 22, new BigDecimal(6000.6666)),
            new Employee(4L, "赵六", 22, new BigDecimal(6000.6666)),
            new Employee(4L, "赵六", 22, new BigDecimal(6000.6666)),
            new Employee(4L, "赵六", 22, new BigDecimal(6000.6666))
    );

    /**
     * 中间操作
     *
     * 多个中间操作可以连接起来形成一个流水线, 除非流水线上触发终止操作, 否则中间操作不会执行任何的处理! 而在终止操作时一次性全部处理, 称为“惰性求值”.
     *
     * 筛选与切片
     * filter(Predicate) - 接收 Lambda ,从流中排除某些元素.
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

    /**
     * skip(n) - 跳过元素, 返回一个扔掉了前n个元素的流.若流中元素不足 n 个, 则返回一个空流. 与 limit(n) 互补
     */
    @Test
    public void test4() {
        employeeList.stream()
                .filter(employee -> employee.getAge() >= 22)
                .skip(2) // 跳过前两个元素
                .forEach(System.out::println);
        // 通过打印可以发现, 流中满足 age >= 22 的元素 应该有 5 个, 却只打印了 3 个, 是因为 前两个元素被skip跳过了
        // Employee{id=5, name='小明', age=36, salary=12000.23660000000018044374883174896240234375}
        // Employee{id=3, name='王五', age=22, salary=5000}
        // Employee{id=4, name='赵六', age=22, salary=6000.6665999999995619873516261577606201171875}
    }

    /**
     * distinct - 筛选, 通过流所生成元素的 hashCode()和equals()去除重复元素
     */
    @Test
    public void test5() {
        employeeList.stream()
                .distinct() // 去重, 需要给实际元素 实现 hashCode() 与 equals() 方法, 才可以去除, 否则 distinct() 不生效
                .forEach(System.out::println);
        // 通过打印可以发现 四个赵六 现在只打印了一个 说明 distinct() 生效了, 去重了.
    }

    /**
     * 映射
     * map - 接收Lambda, 将元素转换成其他形式或提取信息。
     * 接收一个函数作为参数，该函数会被应用到每个元素上, 并将其映射成一个新的元素。
     * flatMap - 接收一个函数作为参数, 将流中的每个值都换成另一个流, 然后把所有流连接成一个流
     *
     * 我的理解 map 就是用来类似于 类型转换的 map需要一个Function接口 即 输入一个参数 返回一个参数
     * 还有 mapToDouble 需要一个ToDoubleFunction接口 即 输入一个参数 返回一个Double类型的数据
     * 同理 mapToLong 等等等都一样.
     *
     */
    @Test
    public void test6() {
        List<String> list = Arrays.asList("AA", "BB", "CC", "DD", "EE", "FF");

        list.stream()
                .map(str -> str.toLowerCase()) //该map需要一个 Function的接口 即函数型接口 输入一个参数, 返回一个参数
                .forEach(System.out::println);

        System.out.println("\n------------------------------------------------------\n");

        employeeList.stream()
//                .map(employee -> employee.getName()) // 需要一个 Function接口 输入一个参数, 返回一个参数 可简写为 方法引用的方式
                .map(Employee::getName) // 输入一个 employee对象 返回一个 String类型 即 e -> e.getName()
                .forEach(System.out::println);

        System.out.println("\n------------------------------------------------------\n");

        Stream<Stream<Character>> streamStream = list.stream()
//                .map(str -> filterCharacter(str)) //输入一个 字符串, 返回一个 流, 可简写为方法引用的方式
                .map(TestStreamAPI8::filterCharacter);
                //map中需要的接口为 Function 本身就是输入一个参数 返回一个参数, 不过方法filterCharacter是输入一个字符串 返回一个流
                //而map本身也是将 最返回的参数作为一个 新的流返回 (集合中存放集合的赶脚)

        streamStream.forEach(stream -> {
            stream.forEach(System.out::println);
        });

        System.out.println("\n------------------------------------------------------\n");

        Stream<Character> characterStream = list.stream()
                .flatMap(TestStreamAPI8::filterCharacter); //flatMap会将返回的流 合并为同一个流, 避免上面 map出现的情况

        characterStream.forEach(System.out::println);


    }

    //输入一个字符串, 将其拆分为字符数组转为 Stream流后返回
    public static Stream<Character> filterCharacter(String str) {
        // Character 为 char 的包装类
        ArrayList<Character> list = new ArrayList<>();

        for (Character ch : str.toCharArray()) {
            list.add(ch);
        }

        return list.stream();
    }

    /**
     * sorted() - 自然排序 以String举例, String类实现了Comparable接口中的compareTo方法 自然排序默认就是根据 该方法排序的
     * sorted(Comparator com) - 定制排序  即按照自己定义的规则排序
     */
    @Test
    public void test7() {
        List<String> list = Arrays.asList("aa", "ff", "gg", "cc", "bb", "jj", "ee");

        list.stream()
                .sorted() // 自然排序 从小到大, (升序)
                .forEach(System.out::println);

        System.out.println("\n====================================\n");

        // employee集合 按照年龄排序 年龄大的在前面 (降序)
        employeeList.stream()
                .sorted((a, b) -> -(a.getAge().compareTo(b.getAge()))) // employees对象排序 我们自己实现规则 这里是按年龄排序
                .forEach(System.out::println);

    }



}
