package org.pjj.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 前言: 我们发现 之前得测试 使用Lambda表达式还得 编写一个 支持Lambda表达式得 函数式接口, 感觉就是很麻烦, 但是Java其实是已经给我们提供了得.
 *
 * Java8 内置得四大核心函数式接口:
 *
 * Consumer<T> : 消费型接口
 *      void accept(T t);
 *
 * Supplier<T> : 供给型接口
 *      T get();
 *
 * Function<T, R> : 函数型接口
 *      R apply(T t);
 *
 * Predicate<T> : 断言型接口
 *      boolean test(T t);
 *
 *
 * @author PengJiaJun
 * @Date 2022/5/14 12:26
 */
public class TestLambda4 {

    /**
     * Consumer<T> : 消费型接口
     */
    @Test
    public void test1() {
        happy(10000, money -> System.out.println("所以消费型接口就是用来大宝剑的? 本次大宝剑消费了: " + money + "元"));
    }

    // 调用 con.accept(money) 进行消费, 至于怎么消费, 得看传入得con得具体实现, 也就是Lambda体 完成得实现
    public void happy(double money, Consumer<Double> con) {
        con.accept(money);
    }

    /**
     * Supplier<T> : 供给型接口
     */
    @Test
    public void test2() {
        List<Integer> numList = getNumList(10, () -> (int) (Math.random() * 100));
        for (Integer i : numList) {
            System.out.println(i);
        }
    }

    // 产生指定个数的整数 sup.get() 至于怎么产生, 得看传入得sup得具体实现, 也就是Lambda体 是如何实现的, 并放入集合中
    public List<Integer> getNumList(int num, Supplier<Integer> sup) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Integer n = sup.get();
            list.add(n);
        }

        return list;
    }

    /**
     * Function<T, R> : 函数型接口
     */
    @Test
    public void test3() {
        // (str) -> str.trim() Lambda解析: 传入一个 str 将 str去除前后空格后返回
        //此处 strHandler(string, Function) Function为函数式接口 其中只有一个抽象方法 apply Lambda相当于就是实现该方法 然后 new 出一个 Function接口的对象
        String res = strHandler("\t\t\t Python是世界上最好的语言.java   ", (str) -> str.trim());
        System.out.println(res);

        // 将后缀改为 js
        // 该 strHandler 中是调用Function的apply方法, 所以strHandler具体是干什么的 主要看传入的 Function 对象, Lambda以简化匿名内部类的方式 实现了 Function 接口
        String res2 = strHandler("Python是世界上最好的语言.java", (str) -> str.substring(0, str.indexOf(".") + 1) + "js");
        System.out.println(res2);
    }

    // 用于处理字符串 输入一个 字符串 与 Function对象, 然后返回一个字符串, 至于返回什么字符串 得看传入的Function对象的具体实现, 也就是Lambda体的具体实现
    public String strHandler(String str, Function<String, String> fun) {
        String apply = fun.apply(str);
        return apply;
    }

    /**
     * Predicate<T> : 断言型接口
     */
    @Test
    public void test4() {
        List<String> StringList = new ArrayList<>();
        StringList.add("100");
        StringList.add("200");
        StringList.add("300");
        StringList.add("400");
        StringList.add("500");
        StringList.add("600");

        // 传入一个 字符串集合, 与 一个 Predicate对象 通过Predicate对象的test方法进行过滤字符串集合 最终返回一个新的集合
        // 而该Predicate对象 是通过Lambda表达式传入的 Predicate对象, 该对象的 test 方法就是 Lambda体的具体实现
        // 具体实现 该Lambda表达式 表达的是 将输入的字符串转为int 如果大于300为真 反之为假
        List<String> strings = filterStr(StringList, (str) -> Integer.parseInt(str) > 300);
        for(String item : strings) {
            System.out.println(item);
        }


    }

    // 将满足条件的字符串, 放入集合中. 至于满足什么字符串, 则得看传入的 Predicate 对象的具体实现了
    public List<String> filterStr(List<String> list, Predicate<String> predicate) {
        List<String> result = new ArrayList<>();
        for(String item : list) {
            if(predicate.test(item)) { //传入的 predicate的具体实现 来判断 传入的字符串是否要加入 返回的集合中.
                result.add(item);
            }
        }

        return result;
    }





}
