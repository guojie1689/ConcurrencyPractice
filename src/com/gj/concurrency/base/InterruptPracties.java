package com.gj.concurrency.base;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author guojie
 * <p>
 * 中止BlockingQueue的最好做法是做发一个DEAD element
 */
public class InterruptPracties {

    private static Object DEAD_OBJECT = new Object();

    public static void main(String[] args) {
        BlockingQueue blockingQueue = new LinkedBlockingQueue();

        Thread thread = new Thread(() -> {

            while (true) {
                try {
                    System.out.println("等待任务");
                    Object element = blockingQueue.take();

//                    if (element == DEAD_OBJECT) {
//                        System.out.println("终止");
//                        break;
//                    } else {
                    System.out.println("执行任务：");
//                    }
                } catch (InterruptedException e) {
                    System.out.println("发生中断");
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        thread.start();

        new Thread(() -> {

            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    blockingQueue.put(new Object());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("中断任务");

            thread.interrupt();
//使用线程中断也可以
//            try {
//                blockingQueue.put(DEAD_OBJECT);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

        }).start();

    }
}
