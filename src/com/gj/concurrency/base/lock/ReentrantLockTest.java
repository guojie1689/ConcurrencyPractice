package com.gj.concurrency.base.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

    Lock lock = new ReentrantLock();
    Lock fairLock = new ReentrantLock(true);

    void m1() {
        for (int i = 0; i < 10; i++) {
            try {
                lock.lock();
                Thread.sleep(500);
                System.out.println(i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }

            if (i == 2) {
                m2();
            }
        }
    }

    void m2() {
        lock.lock();
        System.out.println("m2 execute");
        lock.unlock();
    }

    void interrupt() {
        try {
            System.out.println("t1 start lock ---");
            lock.lockInterruptibly();
            Thread.sleep(10 * 60 * 1000);
        } catch (InterruptedException e) {
            System.out.println("t1 cause InterruptedException");
        } finally {
            System.out.println("t1 finally");
            lock.unlock();
        }

        System.out.println("t1 end ----");
    }

    void fairM1() {
        for (int i = 0; i < 1000; i++) {
            fairLock.lock();
            System.out.println(Thread.currentThread().getName() + " 执行");
            fairLock.unlock();
        }
    }

    void fairM2() {
        for (int i = 0; i < 1000; i++) {
            fairLock.lock();
            System.out.println(Thread.currentThread().getName() + " 执行");
            fairLock.unlock();
        }
    }

    /**
     * synchronized
     *
     * @param args
     */
    public static void main(String[] args) {
        ReentrantLockTest reentrantLockTest = new ReentrantLockTest();

        new Thread(() -> reentrantLockTest.m1()).start();

        /**
         * interrupt
         */
//        Thread t1 = new Thread(() -> reentrantLockTest.interrupt());
//        t1.start();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                System.out.println("Execute t1 interrupt");
//                t1.interrupt();
//            }
//        }).start();

        /**
         * Fair lock
         */
        new Thread(() -> reentrantLockTest.fairM1()).start();
        new Thread(() -> reentrantLockTest.fairM2()).start();

    }
}
