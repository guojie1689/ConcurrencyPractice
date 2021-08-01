package com.gj.concurrency.base.shutlock;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author guojie
 * <p>
 * 也可以表示一种闭锁，FutureTask表示的计算是通过Callable来实现的
 */
public class FutureTaskPractice {

    public static void main(String[] args) {

        FutureTask<Integer> task1 = new FutureTask(new Callable() {
            @Override
            public Integer call() throws Exception {
                return 100;
            }
        });

        FutureTask<Integer> task2 = new FutureTask(new Callable() {
            @Override
            public Integer call() throws Exception {
                return 200;
            }
        });


        new Thread(task1).start();
        new Thread(task2).start();

        try {
            System.out.println(task1.get() + task2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
