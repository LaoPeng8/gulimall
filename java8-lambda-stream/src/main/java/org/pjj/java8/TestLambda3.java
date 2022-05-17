package org.pjj.java8;

import org.junit.Test;
import org.pjj.java8.entity.Employee;
import org.pjj.java8.fun.MyFun2;
import org.pjj.java8.fun.MyFun3;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
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
 * @Date 2022/5/13 22:46
 */
public class TestLambda3 {

    List<Employee> employeeList = Arrays.asList(
            new Employee(1L, "张三", 22, new BigDecimal(3000)),
            new Employee(2L, "李四", 21, new BigDecimal(4000)),
            new Employee(6L, "小黑", 37, new BigDecimal(16001.88888)),
            new Employee(5L, "小明", 36, new BigDecimal(12000.2366)),
            new Employee(3L, "王五", 22, new BigDecimal(5000)),
            new Employee(4L, "赵六", 22, new BigDecimal(6000.6666))
    );

    /**
     * 1.调用Collections.sort() 方法，通过定制排序比较两个Employee (先按年龄比，年龄相同按姓名比)，使用Lambda 作为参数传递。
     */
    @Test
    public void test1() {
        // sort 方法需要一个 list, 与一个Comparator的对象(需要里面的 int compare(x, y)方法), 可以采用 实现该接口, 然后new,
        // 也可以 使用匿名内部类的方式, 这里采用的 Lambda表达式的方式, 相当于就是 new 了一个接口 Comparator 然后实现了 里面的 int compare(x, y)方法
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

    // 对字符串进行处理, 至于进行什么处理 得看传入得 MyFun2得具体实现
    public String strHandler(String str, MyFun2 mf) {
        return mf.getValue(str);
    }

    /**
     * 2.①声明函数式接口，接口中声明抽象方法，public String getValue(String str);
     * ②声明类TestLambda，类中编写方法使用接口作为参数，将一个字符串转换成大写，并作为方法的返回值。
     * ③再将一个字符串的第2个和第4个索引位置进行截取子串。
     */
    @Test
    public void test2() {
        // 将一个字符串转换成大写，并作为方法的返回值。
        String hello = strHandler("hello", (str) -> str.substring(0, 1).toUpperCase() + str.substring(1));
        System.out.println(hello);

        // 将一个字符串的第2个和第4个索引位置进行截取子串
        String helloworld = strHandler("helloworld", (str) -> str.substring(2, 3) + str.substring(4, 5));
        System.out.println(helloworld);
    }

    // 对两个long进行处理, 至于进行什么处理 得看传入得 MyFun3得具体实现
    public long longHandler(long l1, long l2, MyFun3<Long, Long> mf) {
        return mf.getValue(l1, l2);
    }

    /**
     * 3.①声明一个带两个泛型的函数式接口，泛型类型为<T,R> T为参数，R为返回值
     * ②接口中声明对应抽象方法。
     * ③在TestLambda类中声明方法，使用接口作为参数，计算两个long 型参数的和。
     * ④再计算两个long 型参数的乘积。
     */
    @Test
    public void test3() {
        long param1 = 100, param2 = 200;
        long l = longHandler(param1, param2, (a, b) -> a + b);
        System.out.println(param1 + " + " + param2 + " = " + l);

        long param3 = 8, param4 = 9;
        long l2 = longHandler(param3, param4, (x, y) -> x * y);
        System.out.println(param3 + " * " + param4 + " = " + l2);
    }

}
