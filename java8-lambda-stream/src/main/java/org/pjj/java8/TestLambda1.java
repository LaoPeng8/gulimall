package org.pjj.java8;

import org.junit.Test;
import org.pjj.java8.entity.Employee;
import org.pjj.java8.filter.FilterEmployeeByAge;
import org.pjj.java8.filter.FilterEmployeeBySalary;
import org.pjj.java8.filter.MyPredicate;

import java.math.BigDecimal;
import java.util.*;

/**
 * 为什么使用Lambda表达式
 *
 * Lambda是一个匿名函数，我们可以把Lambda 表达式理解为是一段可以传递的代码(将代码像数据一样进行传递)。
 * 可以写出更简洁、更灵活的代码。作为一种更紧凑的代码风格，使Java的语言表达能力得到了提升。
 *
 * @author PengJiaJun
 * @Date 2022/5/12 21:59
 */
public class TestLambda1 {

    List<Employee> employeeList = Arrays.asList(
            new Employee(1L, "张三", 21, new BigDecimal(3000)),
            new Employee(2L, "李四", 22, new BigDecimal(4000)),
            new Employee(3L, "王五", 23, new BigDecimal(5000)),
            new Employee(4L, "赵六", 24, new BigDecimal(6000.6666)),
            new Employee(5L, "小明", 36, new BigDecimal(12000.2366)),
            new Employee(6L, "小黑", 37, new BigDecimal(16001.88888))
    );

    /**
     * 匿名内部类
     */
    @Test
    public void test1() {
        Comparator<Integer> com = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };
        int compare = com.compare(100, 200);// -1
        int compare1 = com.compare(300, 200);// 1
        int compare2 = com.compare(100, 100);// 0

        TreeSet<Integer> treeSet = new TreeSet<>(com);
    }

    /**
     * Lambda表达式
     */
    @Test
    public void test2() {
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
        int compare = com.compare(100, 200);// -1

        TreeSet<Integer> treeSet = new TreeSet<>(com);
    }

    /**
     * 需求: 获取当前公司中员工年龄大于 35 的员工信息
     * 正常写法
     */
    public List<Employee> filterEmployees(List<Employee> list) {
        List<Employee> result = new ArrayList<>();
        for(Employee item : list) {
            if(item.getAge() > 35) {
                result.add(item);
            }
        }

        return result;
    }

    /**
     * 需求: 获取当前公司中员工工资大于 5000 的员工信息
     * 正常写法
     */
    public List<Employee> filterEmployees2(List<Employee> list) {
        List<Employee> result = new ArrayList<>();
        for(Employee item : list) {
//            BigDecimal 比较大小的方法
//            5000.compareTo(6000); // -1    5000.compareTo(5000); // 0   6000.compareTo(5000); // 1
            if(item.getSalary().compareTo(new BigDecimal(5000)) == 1) {
                result.add(item);
            }
        }

        return result;
    }

    /**
     * 发现 上面两个方法 filterEmployees 与 filterEmployees2 有大量重复代码
     * 优化方式一 : 策略设计模式
     *      使用策略设计模式设计模式 编写 MyPredicate.java 接口 该接口提供一个 比较两数大小的接口
     *      编写不同实现类如: FilterEmployeeByAge.java, FilterEmployeeBySalary.java 一个比较年龄, 一个比较工资
     *      在实际调用 传入本方法的 MyPredicate<Employee> mp 对象不同 比较的东西也不同
     *      实际可看 test3()方法
     *
     *
     */
    public List<Employee> filterEmployees(List<Employee> list, MyPredicate<Employee> mp) {

        List<Employee> result = new ArrayList<>();
        for(Employee item : list) {
            if(mp.test(item)) { // 调用 test 进行比较大小, 至于比较的是什么? 就得看 传入的 mp 是什么的实现类 (多态)
                result.add(item);
            }
        }

        return result;
    }

    /**
     * 测试 正常写法 与 Lambda写法
     */
    @Test
    public void test3() {
        //正常写法  获取当前公司中员工年龄大于 35 的员工信息
        List<Employee> employees = filterEmployees(employeeList);
        System.out.println(employees);

        //优化后的写法  获取当前公司中员工年龄大于 35 的员工信息
        System.out.println(filterEmployees(employeeList, new FilterEmployeeByAge()));
        //获取当前公司中员工年龄大于 15 的员工信息
        System.out.println(filterEmployees(employeeList, new FilterEmployeeByAge(15)));

        System.out.println("\n=======================================================\n");
        //正常写法  获取当前公司中员工工资大于 5000 的员工信息
        List<Employee> employees1 = filterEmployees2(employeeList);
        System.out.println(employees1);

        //优化后的写法
        System.out.println(filterEmployees(employeeList, new FilterEmployeeBySalary()));

    }

    /**
     * 优化方式二 : 匿名内部类
     *
     * 我们发现 使用策略模式 来优化 的好处 虽然 不用修改原有代码, 新增过滤要求 只需要新增一个 MyPredicate 实现类 (非常符合设计模式的 六大原则)
     * 但是 每次新增实现类 还是比较麻烦, 而且会导致 多出很多类, 比较臃肿. 于是 我们可以使用 匿名内部类的方式
     *
     * 需求: 获取当前公司中员工id 大于 2 的员工信息 (采用匿名内部类方式, 就不用再次新建一个 MyPredicate接口的实现类了)
     *
     */
    @Test
    public void test4() {
        List<Employee> employees = filterEmployees(this.employeeList, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getId() > 2L;
            }
        });

        System.out.println(employees);
    }

    /**
     * 优化方式三 : Lambda表达式 来 简化匿名内部类
     *
     * 需求: 获取当前公司中员工id 大于 2 的员工信息 (采用匿名内部类方式, 就不用再次新建一个 MyPredicate接口的实现类了) (Lambda就不用编写匿名内部类了)
     *
     */
    @Test
    public void test5() {
        List<Employee> employees = filterEmployees(this.employeeList, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getId() > 2L;
            }
        });

        employees = filterEmployees(this.employeeList, (e) -> {return e.getId() > 2;});
        employees.forEach(System.out::println);
    }

    /**
     * 优化方式四: Stream API
     */
    @Test
    public void test6() {
        this.employeeList.stream()
                .filter(employee -> employee.getAge() > 20)
                .limit(4)
                .forEach(System.out::println);

        System.out.println("-----------------------------------------------------");

        this.employeeList.stream()
                .map(Employee::getName)
                .forEach(System.out::println);

    }


}