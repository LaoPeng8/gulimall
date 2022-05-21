package org.pjj.java8;

import org.junit.Test;
import org.pjj.java8.entity.Employee;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 一、Stream 的三个操作步骤
 *
 * 1. 创建 Stream
 *
 * 2. 中间操作
 *
 * 3. 终止操作(终端操作)
 *
 *  * 查找与匹配
 *  * allMatch - 检查是否匹配所有元素
 *  * anyMatch - 检查是否至少匹配一个元素
 *  * noneMatch - 检查是否没有匹配所有元素
 *  * findFirst - 返回第一个元素
 *  * findAny - 返回当前流中的任意元素
 *  * count - 返回流中元素的总个数
 *  * max - 返回流中最大值
 *  * min - 返回流中最小值
 *
 *  * 归约
 *  * reduce(T identity, BinaryOperator) / reduce(BinaryOperator) - 可以将流中元素反复结合起来, 得到一个值.
 *
 *  * 收集
 *  * collect - 将流转为其他形式. 接受一个Collector接口的实现, 用于给Stream中元素做汇总的方法
 *
 * @author PengJiaJun
 * @Date 2022/05/21 00:57
 */
public class TestStreamAPI9 {

    List<Employee> employeeList = Arrays.asList(
            new Employee(1L, "张三", 22, new BigDecimal(3000), Employee.Status.FREE),
            new Employee(2L, "李四", 21, new BigDecimal(4000), Employee.Status.BUSY),
            new Employee(6L, "小黑", 37, new BigDecimal(16001.88888), Employee.Status.VOCATION),
            new Employee(5L, "小明", 36, new BigDecimal(12000.2366), Employee.Status.FREE),
            new Employee(3L, "王五", 22, new BigDecimal(5000), Employee.Status.BUSY),
            new Employee(4L, "赵六", 22, new BigDecimal(6000.6666), Employee.Status.FREE),
            new Employee(4L, "赵六", 22, new BigDecimal(6000.6666), Employee.Status.FREE),
            new Employee(4L, "赵六", 50, new BigDecimal(6000.6666), Employee.Status.BUSY),
            new Employee(4L, "赵六", 22, new BigDecimal(6000.6666), Employee.Status.BUSY)
    );

    /**
     * 查找与匹配
     *
     * allMatch - 检查是否匹配所有元素
     */
    @Test
    public void test1() {
        boolean flag = employeeList.stream()
                // allMatch() 判断流中的元素是否 都满足 一种规则, 如此处实现为 判断流中元素status属性是否都不为null
                .allMatch(employee -> employee.getStatus() != null);
        System.out.println("employeeList中Status状态是否都不为NULL: " + flag);
    }

    /**
     * anyMatch - 检查是否至少匹配一个元素
     */
    @Test
    public void test2() {
        boolean flag = employeeList.stream()
                // anyMatch() 判断流中的元素是否 有一个元素满足 一种规则, 如此处实现为 判断流中是否有一位员工工资大于15000
                .anyMatch(employee -> employee.getSalary().doubleValue() > 15000);
        System.out.println("employeeList中至少有一位员工 工资大于 15000: " + flag);
    }

    /**
     * noneMatch - 检查是否没有匹配所有元素
     */
    @Test
    public void test3() {
        boolean flag = employeeList.stream()
                // noneMatch() 判断流中的元素 是否都不满足 一种规则, 如此处实现为 判断流中元素的姓名长度是否都不大于10位
                .noneMatch(employee -> employee.getName().length() > 10);
        System.out.println("employeeList中没有一位员工的姓名长度大于10位: " + flag);
    }

    /**
     * findFirst - 返回第一个元素
     */
    @Test
    public void test4() {
        Optional<Employee> first = employeeList.stream()
                // 安装工资排序 降序排列 工资高的在前面
                .sorted((e1, e2) -> -Double.compare(e1.getSalary().doubleValue(), e2.getSalary().doubleValue()))
                .findFirst();// findFirst() 返回流中第一个元素
        System.out.println("employeeList中工资第一的员工信息为: " + first.get());
    }

    /**
     * findAny - 返回当前流中的任意元素
     */
    @Test
    public void test5() {
        Optional<Employee> any = employeeList.stream()
                // 先过滤出 流中 Status == FREE 的元素, 也就是过滤出流中 员工状态为空闲的 员工
                .filter(employee -> employee.getStatus() == Employee.Status.FREE)
                // 随机从流中返回一个元素 (从 全是空闲员工的流中, 随机返回一个员工)
                .findAny();
        System.out.println("employeeList中随机获取一个状态为FREE的员工" + any.get());
    }

    /**
     * count - 返回流中元素的总个数
     */
    @Test
    public void test6() {
        long count = employeeList.stream()
                .count();// 返回流中元素的总个数
        System.out.println("employeeList中一共有员工: " + count + "位");

        long count2 = employeeList.stream()
                //过滤出流中 年龄等于22岁的员工
                .filter(employee -> employee.getAge() == 22)
                .count();// 返回流中元素的总个数
        System.out.println("employeeList中员工年龄为22岁的员工一共有: " + count2 + "位");
    }

    /**
     * max - 返回流中最大值
     */
    @Test
    public void test7() {
        Optional<Employee> max = employeeList.stream()
                .max((e1, e2) -> Integer.compare(e1.getAge(), e2.getAge()));
        System.out.println("employeeList中员工年龄最大的员工为: " + max.get());
        // employeeList中员工年龄最大的员工为: Employee{id=6, name='小黑', age=37, salary=16001.888880000000426662154495716094970703125, status=VOCATION}
    }

    /**
     * min - 返回流中最小值
     */
    @Test
    public void test8() {
        Optional<Employee> min = employeeList.stream()
                .min((e1, e2) -> Integer.compare(e1.getAge(), e2.getAge()));
        System.out.println("employeeList中员工年龄最小的员工为: " + min.get());
        // employeeList中员工年龄最小的员工为: Employee{id=2, name='李四', age=21, salary=4000, status=BUSY}

        Optional<Double> min1 = employeeList.stream()
                .map(Employee::getSalary2)
                .min(Double::compare);
        System.out.println("employeeList中员工工资最少为: " + min1.get());
    }


    /**
     * 归约
     *
     * reduce(T identity, BinaryOperator) / reduce(BinaryOperator) - 可以将流中元素反复结合起来, 得到一个值.
     */
    @Test
    public void test9() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Integer sum = list.stream()
                // 起始值为0, 即 x 第一次为 0 , y第一次为 list中第一个元素 1, x + y = 1, x=1 + y=2 = 3, x=3 + y=3 = 6, x=6 + y=4 = 10 ...
                .reduce(0, (x, y) -> x + y);
        System.out.println(sum);

        System.out.println("\n=====================================================\n");

        Optional<Double> salarySum = employeeList.stream()
                //将 流中的employee转为salary
                .map(employee -> employee.getSalary2())
                // 输入什么类型返回什么类型, 本来是想 (e1, e2) -> e1.getSalary() + e2.getSalary()
                // 但是不行 reduce需要泛型为 T, T, T 即输入两个什么类型, 返回什么类型
                // 于是使用 map() 将 employee 转为了 salary 所以使用 (x, y) -> x + y, 可简写为 Double.sum(x, y), 继续简写 Double::sum
                .reduce(Double::sum);
                // reduce(T identity, BinaryOperator) 上面计算1~10的和 使用起始值0, 所以不可能为空, 最多为0, 所以返回值为 Integer
                // 这里重载的 reduce(BinaryOperator)方法 没有给起起始值(第一个参数), 所以 可能为空, 所以返回值为 Optional<Double>
                // Optional 是不可能为空的, 其真正的值在 Optional.get()

        System.out.println("员工工资总和为: " + salarySum.get());
    }

    /**
     * 收集
     *
     * collect - 将流转为其他形式. 接受一个Collector接口的实现, 用于给Stream中元素做汇总的方法
     */
    @Test
    public void test10() {
        List<String> list = employeeList.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());// 转为list
        list.forEach(System.out::println);

        System.out.println("\n=================================================\n");

        Set<String> set = employeeList.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet());// 转为set (利用set特性, 去重)
        set.forEach(System.out::println);

        System.out.println("\n=================================================\n");

        HashSet<String> hashSet = employeeList.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new));
        hashSet.forEach(System.out::println);
    }

    /**
     * collect - 将流转为其他形式. 接受一个Collector接口的实现, 用于给Stream中元素做汇总的方法
     *      使用 collect 来收集元素个数
     */
    @Test
    public void test11() {
        // 总数
        Long count = employeeList.stream()
                .collect(Collectors.counting());
        System.out.println("总数: " + count);

        System.out.println("\n=================================================\n");

        // 平均值
        Double avg = employeeList.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary2));
        System.out.println("平均值: " + avg);

        System.out.println("\n=================================================\n");

        // 总工资
        Double salarySum = employeeList.stream()
                .collect(Collectors.summingDouble(Employee::getSalary2));
        System.out.println("总工资: " + salarySum);

        System.out.println("\n=================================================\n");

        // 最大值
        Optional<Double> max = employeeList.stream()
                .map(Employee::getSalary2)
                .collect(Collectors.maxBy(Double::compare));
        System.out.println("工资最大值: " + max.get());

        System.out.println("\n=================================================\n");

        // 最小值
        Optional<Employee> min = employeeList.stream()
                .collect(Collectors.minBy((e1, e2) -> Integer.compare(e1.getAge(), e2.getAge())));
        System.out.println("年龄最小的员工为: " + min.get());
    }

    /**
     * 分组
     */
    @Test
    public void test12() {
        Map<Employee.Status, List<Employee>> map = employeeList.stream()
                // 按照 状态 分组
                .collect(Collectors.groupingBy(Employee::getStatus));
        map.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    /**
     * 多级分组
     */
    @Test
    public void test13() {
        Map<Employee.Status, Map<String, List<Employee>>> map = employeeList.stream()
                // 根据 状态分组, 再根据 年龄分组
                .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e) -> {
                    if(e.getAge() <= 30) {
                        return "青年";
                    } else if(e.getAge() <= 40) {
                        return "中年";
                    } else if(e.getAge() <= 50) {
                        return "老年";
                    } else {
                        return "未知年龄";
                    }
                })));

        // 遍历状态
        map.forEach((k, v) -> {
             System.out.println(k+"组: ");
             // 遍历种状态下的 年龄分组
             v.forEach((k2, v2) -> {
                 System.out.println(k2 + ": " + v2);
             });
             System.out.print("\n");
        });
        //FREE组:
        // 青年: [Employee{id=1, name='张三', age=22, salary=3000, status=FREE}, Employee{id=4, name='赵六', age=22, salary=6000.6665999999995619873516261577606201171875, status=FREE}, Employee{id=4, name='赵六', age=22, salary=6000.6665999999995619873516261577606201171875, status=FREE}]
        // 中年: [Employee{id=5, name='小明', age=36, salary=12000.23660000000018044374883174896240234375, status=FREE}]
        //
        // BUSY组:
        // 青年: [Employee{id=2, name='李四', age=21, salary=4000, status=BUSY}, Employee{id=3, name='王五', age=22, salary=5000, status=BUSY}, Employee{id=4, name='赵六', age=22, salary=6000.6665999999995619873516261577606201171875, status=BUSY}]
        // 老年: [Employee{id=4, name='赵六', age=50, salary=6000.6665999999995619873516261577606201171875, status=BUSY}]
        //
        // VOCATION组:
        // 中年: [Employee{id=6, name='小黑', age=37, salary=16001.888880000000426662154495716094970703125, status=VOCATION}]
    }

    /**
     * 分区 (分片)
     */
    @Test
    public void test14() {
        Map<Boolean, List<Employee>> map = employeeList.stream()
                // 根据 工资大于 8000 分区  <8000 为 false区, >8000为true区
                .collect(Collectors.partitioningBy((e) -> e.getSalary2() > 8000));

        map.forEach((k, v) -> {
            System.out.println(k + ": ");
            v.forEach(System.out::println);
            System.out.print("\n");
        });
    }

    /**
     *
     */
    @Test
    public void test15() {
        DoubleSummaryStatistics doubleSummaryStatistics = employeeList.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary2));//传入工资返回 DoubleSummaryStatistics 对象

        // DoubleSummaryStatistics对象 可以求出
        System.out.println(doubleSummaryStatistics.getCount());//总数
        System.out.println(doubleSummaryStatistics.getSum());//和
        System.out.println(doubleSummaryStatistics.getMax());//最大值
        System.out.println(doubleSummaryStatistics.getMin());//最小值
        System.out.println(doubleSummaryStatistics.getAverage());//平均值
    }

    /**
     *
     */
    @Test
    public void test16() {
        String str = employeeList.stream()
                .map(Employee::getName)
                .collect(Collectors.joining());// joining() 连接字符串
        System.out.println(str);// 张三李四小黑小明王五赵六赵六赵六赵六

        String str2 = employeeList.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(", "));// joining(", ") 连接字符串 每个字符串的连接处加入 “, ”
        System.out.println(str2);// 张三, 李四, 小黑, 小明, 王五, 赵六, 赵六, 赵六, 赵六

        String str3 = employeeList.stream()
                .map(Employee::getName)
                // joining(", ", ">>>", "<<<") 连接字符串 每个字符串的连接处加入 “, ” 连接完成后 首部加入>>>, 尾部加入<<<
                .collect(Collectors.joining(", ", ">>>", "<<<"));
        System.out.println(str3);// >>>张三, 李四, 小黑, 小明, 王五, 赵六, 赵六, 赵六, 赵六<<<
    }



}
