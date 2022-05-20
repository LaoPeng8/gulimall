package org.pjj.java8;

import org.junit.Test;
import org.pjj.java8.entity.Employee;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
            new Employee(4L, "赵六", 22, new BigDecimal(6000.6666)),
            new Employee(4L, "赵六", 22, new BigDecimal(6000.6666)),
            new Employee(4L, "赵六", 22, new BigDecimal(6000.6666)),
            new Employee(4L, "赵六", 22, new BigDecimal(6000.6666))
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

}
