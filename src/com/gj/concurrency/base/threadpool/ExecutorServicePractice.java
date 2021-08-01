package com.gj.concurrency.base.threadpool;

import java.util.concurrent.Executors;

/**
 * @author guojie
 * <p>
 */
public class ExecutorServicePractice {

    public static void main(String[] args) {
        /**
         * 将创建一个固定长度的线程池，每当提交一个任务时就创建一个线程，直到达到线程池的最大数量，这时线程池的规模将不再变化(
         * 如果某个线程由于发生了未预期的Exception而结束，那么线程池会补充一个新的线程)
         */
        Executors.newFixedThreadPool(10)                                                                                                                                                                                                                                                                            ;
        /**
         * 将创建一个可缓存的线程池，如果线程池的当前规模超过了处理需求时，那么将回收空闲的线程，而当需求增加时，则可以添加新的线程，线程池的规模不存在任何限制¬
         */
        Executors.newCachedThreadPool();
        /**
         * 单线程的Excecutor，它创建单个工作者线程来执行任务，如果这个线程异常结束，会创建另一个线程来替代。能确保按照任务在队列中的顺序来串行执行
         */
        Executors.newSingleThreadExecutor();
        /**
         * 创建了一个固定长度的线程池，而且以延迟或定时的方式来执行任务
         */
        Executors.newScheduledThreadPool(2);
    }
}
