package com.gj.concurrency.base.shutlock;

import java.util.concurrent.CountDownLatch;

/**
 * @author guojie
 * <p>
 * 闭锁是一种同步工具类，可以延迟线程的进度直到期到达终止状态。
 * 闭锁的作用相当于一扇门：在闭锁到达结束状态之前，这扇门一直是关闭的，并且没有任何线程能通过，当到达
 * 结束状态时，这扇门会打开并允许所有的线程通过
 */
public class CountDownLatchPractice {

    static int aResult = 0;
    static int bResult = 0;

    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(() -> {
            aResult = 100;
            countDownLatch.countDown();
        }).start();

        new Thread(() -> {
            bResult = 200;
            countDownLatch.countDown();
        }).start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(aResult + bResult);

    }
}
