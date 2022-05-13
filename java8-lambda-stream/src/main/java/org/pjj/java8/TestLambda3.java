package org.pjj.java8;

import org.junit.Test;
import org.pjj.java8.entity.Employee;

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

}
