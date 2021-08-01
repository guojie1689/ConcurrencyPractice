package com.gj.concurrency.base.shutlock;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @author guojie
 * <p>
 * 信号量
 * 用来控制同时访问某个特定资源的操作数量，或者同时执行某个指定操作的数量。计数信号量还可以用来实现某种资源池，或者对容器施加边界
 */
public class SemaphorePracties {

    private static Random random = new Random();

    /**
     * 柜台
     */
    static class Counter {
        /**
         * 编号
         */
        private int code;

        public Counter(int code) {
            this.code = code;
        }

        public void recieptCustom(Queue<Counter> counterList, Customer custom, Semaphore semaphore) {


            new Thread(() -> {
                try {
                    Thread.sleep(500 + random.nextInt(2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("柜台" + code + "接待" + custom + " 完毕");

                counterList.offer(this);
                semaphore.release();
            }).start();
        }
    }

    static class Dispatcher {
        Queue<Counter> counterList = new LinkedList<>();
        Semaphore semaphore = null;

        public Dispatcher() {
            counterList.offer(new Counter(1));
            counterList.offer(new Counter(2));
            counterList.offer(new Counter(3));

            semaphore = new Semaphore(counterList.size());
        }

        public void dispatchCustom(Customer customer) {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Counter counter = counterList.poll();

            counter.recieptCustom(counterList, customer, semaphore);
        }
    }

    static class Customer {
        public String name;

        public Customer(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static void main(String[] args) {
        Dispatcher dispatcher = new Dispatcher();

        for (int i = 0; i < 50; i++) {
            dispatcher.dispatchCustom(new Customer("顾客" + i));
        }
    }
}
