package org.pjj.java8.entity;

import java.math.BigDecimal;

/**
 * 员工实体类
 * @author PengJiaJun
 * @Date 2022/5/13 16:39
 */
public class Employee {

    private Long id;
    private String name;
    private Integer age;
    private BigDecimal salary;

    public Employee() {
    }

    public Employee(String name) {
        this.name = name;
    }

    public Employee(Integer age, String name) {
        this.age = age;
        this.name = name;
    }

    public Employee(Long id, String name, Integer age, BigDecimal salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }
}
