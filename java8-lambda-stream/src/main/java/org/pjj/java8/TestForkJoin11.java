package org.pjj.java8;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * 理解并行流原理
 *
 * @author PengJiaJun
 * @Date 2022/05/22 16:25
 */
public class TestForkJoin11 {

    /**
     * ForkJoin框架 (相当于 并行流(并行流底层使用的ForkJoin框架), 相当于多线程)
     *
     * 耗费: 2275毫秒  (注意 如果数据量小的话 单线程 是比 多线程快的, 因为线程是需要开销的)
     */
    @Test
    public void test1() {
        Instant start = Instant.now();

        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoin(0, 10000000000L);//累加 0 ~ 10000000000
        Long sum = pool.invoke(task);
        System.out.println(sum);

        Instant end = Instant.now();

        System.out.println("耗费: " + Duration.between(start, end).toMillis() + "毫秒");
    }

    /**
     * 普通 for 循环 (单线程) (相当于串行流)
     *
     * 耗费: 5762毫秒  (注意 如果数据量小的话 单线程 是比 多线程快的, 因为线程是需要开销的)
     */
    @Test
    public void test2() {
        long start = System.currentTimeMillis();

        long sum = 0L;
        for (long i = 0; i <= 10000000000L; i++) {
            sum += i;
        }
        System.out.println(sum);

        long end = System.currentTimeMillis();
        System.out.println("耗费: " + (end - start) + "毫秒");

    }

    /**
     * Java 8 中将并行进行了优化, 我们可以很容易的对数据进行并行操作。
     * Stream API可以声明性地通过 parallel() 与 sequential() 在并行流与顺序流之间进行切换。
     *
     * 耗费: 224毫秒
     */
    @Test
    public void test3() {
        Instant start = Instant.now();

        long reduce = LongStream.rangeClosed(0, 1000000000L)
                .parallel() //转为 并行流执行后续操作
                .reduce(0, (a, b) -> a + b);
        System.out.println(reduce);

        Instant end = Instant.now();

        System.out.println("耗费: " + Duration.between(start, end).toMillis() + "毫秒");




    }

}


class ForkJoin extends RecursiveTask<Long> {
    private static final long serialVersionUID = 123456789L;

    private long start;
    private long end;

    private static final long THRESHOLD = 10000;

    public ForkJoin() {}
    public ForkJoin(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;
        if(length <= THRESHOLD) {
            long sum = 0;
            for (long i = start; i <= end; i++) {
                sum += i;
            }

            return sum;
        } else {
            long middle = (start + end) / 2;
            ForkJoin left = new ForkJoin(start, middle);
            left.fork(); //拆分子任务, 同时压入线程队列

            ForkJoin right = new ForkJoin(middle + 1, end);
            right.fork();

            return left.join() + right.join();
        }

    }
}