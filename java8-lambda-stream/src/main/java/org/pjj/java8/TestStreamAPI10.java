package org.pjj.java8;

import org.junit.Before;
import org.junit.Test;
import org.pjj.java8.entity.Employee;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 练习 Stream API
 *
 * 1. 给定一个数字列表, 如何返回一个由每个数的平方构成的列表呢?
 *    如: 给定【1,2,3,4,5】,应该返回【1,4,9,16,25】。
 * 2. 怎样用 map 和 reduce 方法数一数流中有多少个Employee呢?
 *
 *
 * 1. 找出2011年发生的所有交易,并按交易额排序（从低到高)
 * 2．交易员都在哪些不同的城市工作过?
 * 3. 查找所有来自剑桥的交易员,并按姓名排序
 * 4. 返回所有交易员的姓名字符串,按字母顺序排序
 * 5. 有没有交易员是在米兰工作的?
 * 6. 打印生活在剑桥的交易员的所有交易额
 * 7. 所有交易中,最高的交易额是多少
 * 8. 找到交易额最小的交易
 *
 * @author PengJiaJun
 * @Date 2022/05/21 23:33
 */
public class TestStreamAPI10 {

    List<Employee> employeeList = Arrays.asList(
            new Employee(1L, "张三", 22, new BigDecimal(3000), Employee.Status.FREE),
            new Employee(2L, "李四", 21, new BigDecimal(4000), Employee.Status.BUSY),
            new Employee(6L, "小黑", 37, new BigDecimal(16001.88888), Employee.Status.VOCATION),
            new Employee(5L, "小明", 36, new BigDecimal(12000.2366), Employee.Status.FREE),
            new Employee(3L, "王五", 22, new BigDecimal(5000), Employee.Status.BUSY),
            new Employee(4L, "赵六", 22, new BigDecimal(6000.6666), Employee.Status.FREE),
            new Employee(4L, "赵六", 22, new BigDecimal(6000.6666), Employee.Status.FREE),
            new Employee(4L, "赵六", 50, new BigDecimal(6000.6666), Employee.Status.BUSY),
            new Employee(4L, "赵六", 22, new BigDecimal(6000.6666), Employee.Status.BUSY)
    );

    List<Transaction> transactionList = null;

    @Before
    public void before(){
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        transactionList = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
    }

    /**
     * 1. 给定一个数字列表, 如何返回一个由每个数的平方构成的列表呢?
     * 如: 给定【1,2,3,4,5】,应该返回【1,4,9,16,25】。
     */
    @Test
    public void test1() {
        Integer[] arr = new Integer[]{1, 2, 3, 4, 5};
        Stream<Integer> arrStream = Arrays.stream(arr);

        ArrayList<Integer> collect = arrStream.map((x) -> x * x)// 对stream流进行处理 即 返回每个数的平方
                .collect(Collectors.toCollection(ArrayList::new));// 将处理后的流转为 ArrayList返回
        collect.forEach(System.out::println);
    }

    /**
     * 2. 怎样用 map 和 reduce 方法数一数流中有多少个Employee呢?
     */
    @Test
    public void test2() {
        // 使用 map 和 reduce 方式 获取总数
        Integer count = employeeList.stream()
                .map((e) -> 1) // 将EmployeeList流中每个元素 转为 1
//                .reduce(0, (x, y) -> x + y);// 累加
                .reduce(0, Integer::sum);
        System.out.println(count);

        // 方式二
        long count1 = employeeList.stream()
                .count();
        System.out.println(count1);

        // 方式三
        Long count2 = employeeList.stream()
                .collect(Collectors.counting());
        System.out.println(count2);

        // 方式四
        long count3 = employeeList.stream()
                .collect(Collectors.summarizingLong(Employee::getId))
                .getCount();
        System.out.println(count3);
    }


    /**
     * 1. 找出2011年发生的所有交易,并按交易额排序（从低到高)
     */
    @Test
    public void test3() {
        transactionList.stream()
                .filter(transaction -> transaction.getYear() == 2011) //过滤出 2011 年发生的交易
                .sorted((t1, t2) -> Integer.compare(t1.getValue(), t2.getValue())) // 按交易额排序
                .forEach(System.out::println);

    }

    /**
     * 2．交易员都在哪些不同的城市工作过?
     */
    @Test
    public void test4() {
        transactionList.stream()
                .map(Transaction::getTrader) //将 transaction 转为 trader 因为城市在trader中
                .map(trader -> trader.getCity()) //将 trader 转为 城市
                .distinct() //去重
                .forEach(System.out::println);
    }

    /**
     * 3. 查找所有来自剑桥的交易员,并按姓名排序
     */
    @Test
    public void test5() {
        transactionList.stream()
                .map(Transaction::getTrader) //将 Transaction 转为 Transaction内部的 Trader
                .distinct() // 因为 Transaction(交易记录)中 可能会有多个交易来自同一个交易员, 所以将 交易记录中的交易员取出后需要去重
                .filter(trader -> trader.getCity().equals("Cambridge")) // 筛选出来自剑桥的交易员
                .sorted((t1, t2) -> t1.getName().compareTo(t2.getName())) // 按姓名排序
                .forEach(System.out::println);
    }

    /**
     * 4. 返回所有交易员的姓名字符串,按字母顺序排序
     */
    @Test
    public void test6() {
        transactionList.stream()
                .map(Transaction::getTrader) //从交易记录中获取交易员
                .distinct() // 交易记录中 可能会有多个交易来自同一个交易员, 所以将 交易记录中的交易员取出后需要去重
                .map(trader -> trader.getName()) //从交易员中取出交易员的姓名
                .sorted((name1, name2) -> name1.compareTo(name2)) //根据姓名排序
                .forEach(System.out::println);
    }

    /**
     * 5. 有没有交易员是在米兰工作的?
     */
    @Test
    public void test7() {
        boolean b = transactionList.stream()
                // 从每个交易记录中取出交易员, 从交易员中取出城市, 并判断是否是米兰
                // anyMatch 只要记录中 有一条满足 即为true, 刚好满足题目
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        System.out.println(b);
    }

    /**
     * 6. 打印生活在剑桥的交易员的所有交易额
     */
    @Test
    public void test8() {
        Integer cambridge = transactionList.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge")) //筛选出城市为 剑桥的交易员
                .collect(Collectors.summingInt(Transaction::getValue)); // 求和
        System.out.println(cambridge);

    }

    /**
     * 7. 所有交易中,最高的交易额是多少
     */
    @Test
    public void test9() {
        Optional<Integer> collect = transactionList.stream()
                .map(Transaction::getValue) // 从交易记录中取出交易额
                .collect(Collectors.maxBy(Integer::compare)); // 找出最大值
        System.out.println(collect.get());
    }


    /**
     * 8. 找到交易额最小的交易
     */
    @Test
    public void test10() {
        Optional<Transaction> min = transactionList.stream()
                .min((t1, t2) -> Integer.compare(t1.getValue(), t2.getValue()));
        System.out.println(min.get());
    }



}

//交易员类
class Trader {

    private String name;
    private String city;

    public Trader() {
    }

    public Trader(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Trader [name=" + name + ", city=" + city + "]";
    }

}

//交易类
class Transaction {

    private Trader trader;
    private int year;
    private int value;

    public Transaction() {
    }

    public Transaction(Trader trader, int year, int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Trader getTrader() {
        return trader;
    }

    public void setTrader(Trader trader) {
        this.trader = trader;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Transaction [trader=" + trader + ", year=" + year + ", value="
                + value + "]";
    }

}
