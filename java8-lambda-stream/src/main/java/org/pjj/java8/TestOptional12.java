package org.pjj.java8;

import org.junit.Test;
import org.pjj.java8.entity.Employee;

import java.util.Optional;

/**
 *
 * Optional<T>类(java.util.Optional)是一个容器类, 代表一个值存在或不存在,
 * 原来用null表示一个值不存在, 现在Optional 可以更好的表达这个概念。并且可以避免空指针异常。
 *
 * 常用方法:
 * Optional.of(T t): 创建一个Optional实例
 * Optional.empty(): 创建一个空的Optional实例
 * Optional.ofNullable(T t):若t不为 null,创建0ptional 实例,否则创建空实例
 * isPresent() :判断是否包含值
 * orElse(T t) :如果调用对象包含值，返回该值，否则返回t
 * orElseGet(Supplier s):如果调用对象包含值，返回该值，否则返回s获取的值
 * map(Function f):如果有值对其处理，并返回处理后的Optional，否则返回
 * Optional.empty()flatMap(Function mapper):与map类似，要求返回值必须是Optional
 *
 * @author PengJiaJun
 * @Date 2022/05/23 14:26
 */
public class TestOptional12 {

    /**
     *  Optional.of(T t): 创建一个Optional实例
     */
    @Test
    public void test1() {
        Optional<Employee> employeeOptional = Optional.of(new Employee("张三")); // of 方法为创建一个Optional实例
        Employee employee = employeeOptional.get();
        System.out.println(employee);
    }

    /**
     * Optional.empty(): 创建一个空的Optional实例
     */
    @Test
    public void test2() {
        Optional<Object> empty = Optional.empty();
        System.out.println(empty.get());
    }

    /**
     * Optional.ofNullable(T t):若t不为 null,创建0ptional 实例,否则创建空实例
     * isPresent() :判断是否包含值
     * orElse(T t) :如果调用对象包含值，返回该值，否则返回t
     */
    @Test
    public void test3() {
        Optional<Employee> optional = Optional.ofNullable(null);

        if(optional.isPresent()) {// 判断optional中是否有值, 有值才会执行
            System.out.println(optional.get());
        }

        // 如果 optional中原本有值, 则orElse()返回的是原本的值, 如果 optional中原本没有值, 则orElse()就是返回orElse()中设置的备用值
        Employee emp = optional.orElse(new Employee("代替值"));

        System.out.println(emp);

    }

}
