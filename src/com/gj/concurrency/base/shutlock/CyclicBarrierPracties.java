package com.gj.concurrency.base.shutlock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author guojie
 * <p>
 */
public class CyclicBarrierPracties {

    static class Runner implements Runnable {

        CyclicBarrier cyclicBarrier = null;
        private String name;
        private int timeLimit = 0;

        public Runner(int timeLimit, String name, CyclicBarrier cyclicBarrier) {
            this.timeLimit = timeLimit;
            this.name = name;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(timeLimit);
                System.out.println(name + " 到达栅栏A");
                cyclicBarrier.await();

                System.out.println(name + "开始冲向栅栏B");
                Thread.sleep(timeLimit);
                System.out.println(name + "到达栅栏B");
                cyclicBarrier.await();

                System.out.println(name + "开始冲向栅栏C");
                Thread.sleep(timeLimit);
                System.out.println(name + "到达栅栏C");

            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(20, new Runnable() {
            @Override
            public void run() {
                System.out.println("20人满了，发车");
            }
        });

        for (int i = 0; i < 100; i++) {
            final int currentIdx = i;
            new Thread(() -> {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

    }
}
