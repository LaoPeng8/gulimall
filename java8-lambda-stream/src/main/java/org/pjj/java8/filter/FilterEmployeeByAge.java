package org.pjj.java8.filter;

import org.pjj.java8.entity.Employee;

/**
 * @author PengJiaJun
 * @Date 2022/5/13 17:15
 */
public class FilterEmployeeByAge implements MyPredicate<Employee> {

    private int age = 35;//默认

    public FilterEmployeeByAge() {
    }
    public FilterEmployeeByAge(int age) {
        this.age = age;
    }

    @Override
    public boolean test(Employee employee) {
        return employee.getAge() > age;
    }

}
