package org.pjj.java8.filter;

import org.pjj.java8.entity.Employee;

import java.math.BigDecimal;

/**
 * @author PengJiaJun
 * @Date 2022/5/13 17:23
 */
public class FilterEmployeeBySalary implements MyPredicate<Employee> {

    private BigDecimal salary = new BigDecimal(5000);//默认

    public FilterEmployeeBySalary() {
    }
    public FilterEmployeeBySalary(BigDecimal bigDecimal) {
        this.salary = bigDecimal;
    }

    @Override
    public boolean test(Employee employee) {
        return employee.getSalary().compareTo(salary) == 1;
    }
}
