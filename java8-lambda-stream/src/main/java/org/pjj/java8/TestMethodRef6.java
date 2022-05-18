package org.pjj.java8;

import org.junit.Test;
import org.pjj.java8.entity.Employee;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 *
 * 这些题目 我们在TestLambda3.java中已经写过一边了, 不过 后来我们又学习了 四大内置函数式接口 Consumer, Supplier, Function, Predicate
 * 与
 * 方法引用, 构造器引用, 数组引用, 可以结合这些 来重新写一写这些题目
 *
 * 1.调用Collections.sort() 方法，通过定制排序比较两个Employee (先按年龄比，年龄相同按姓名比)，使用Lambda 作为参数传递。
 *
 * 2.①声明函数式接口，接口中声明抽象方法，public String getValue(String str);
 * ②声明类TestLambda，类中编写方法使用接口作为参数，将一个字符串转换成大写，并作为方法的返回值。
 * ③再将一个字符串的第2个和第4个索引位置进行截取子串。
 *
 * 3.①声明一个带两个泛型的函数式接口，泛型类型为<T,R> T为参数，R为返回值
 * ②接口中声明对应抽象方法。
 * ③在TestLambda类中声明方法，使用接口作为参数，计算两个long 型参数的和。
 * ④再计算两个long 型参数的乘积。
 *
 * @author PengJiaJun
 * @Date 2022/05/18 18:07
 */
public class TestMethodRef6 {

    List<Employee> employeeList = Arrays.asList(
            new Employee(1L, "张三", 22, new BigDecimal(3000)),
            new Employee(2L, "李四", 21, new BigDecimal(4000)),
            new Employee(6L, "小黑", 37, new BigDecimal(16001.88888)),
            new Employee(5L, "小明", 36, new BigDecimal(12000.2366)),
            new Employee(3L, "王五", 22, new BigDecimal(5000)),
            new Employee(4L, "赵六", 22, new BigDecimal(6000.6666))
    );

    /**
     * 调用Collections.sort() 方法，通过定制排序比较两个Employee (先按年龄比，年龄相同按姓名比)，使用Lambda 作为参数传递。
     *
     * 这个还真不知道怎么改进
     */
    @Test
    public void test1() {
        Collections.sort(employeeList, (x, y) -> {
            if(x.getAge() == y.getAge()) {
                return x.getName().compareTo(y.getName());
            }else{
                return Integer.compare(x.getAge(), y.getAge());
            }
        });

        for(Employee item : employeeList) {
            System.out.println(item);
        }
    }

    /**
     * ①声明函数式接口，接口中声明抽象方法，public String getValue(String str);
     * ②声明类TestLambda，类中编写方法使用接口作为参数，将一个字符串转换成大写，并作为方法的返回值。
     * ③再将一个字符串的第2个和第4个索引位置进行截取子串。
     */
    @Test
    public void test2() {
        Function<String, String> function = (str) -> str.substring(0,1).toUpperCase() + str.substring(1);
        System.out.println(function.apply("hello"));

        Function<String, String> function1 = (str) -> str.substring(2, 3) + str.substring(4, 5);
        System.out.println(function1.apply("hello"));
    }

    /**
     * ①声明一个带两个泛型的函数式接口，泛型类型为<T,R> T为参数，R为返回值
     * ②接口中声明对应抽象方法。
     * ③在TestLambda类中声明方法，使用接口作为参数，计算两个long 型参数的和。
     * ④再计算两个long 型参数的乘积。
     */
    @Test
    public void test3() {
        //方法引用 类::静态方法
        BiFunction<Long, Long, Long> biFunction = TestMethodRef6::sum;
        System.out.println(biFunction.apply(100L, 200L));

        //方法引用 对象::实例方法
        TestMethodRef6 testMethodRef6 = new TestMethodRef6();
        BiFunction<Long, Long, Long> biFunction1 = testMethodRef6::mul;
        System.out.println(biFunction1.apply(200L, 300L));
    }

    public static long sum(long l1, long l2) {
        return l1 + l2;
    }
    public long mul(long l1, long l2) {
        return l1 * l2;
    }

}
