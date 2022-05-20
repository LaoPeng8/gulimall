package org.pjj.java8.entity;

import java.math.BigDecimal;
import java.util.Objects;

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
    private Status status;

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

    public Employee(Long id, String name, Integer age, BigDecimal salary, Status status) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public Double getSalary2() {
        return salary.doubleValue();
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(name, employee.name) && Objects.equals(age, employee.age) && Objects.equals(salary, employee.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, salary);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", status=" + status +
                '}';
    }

    public enum Status {
        FREE,
        BUSY,
        VOCATION;
    }

}
