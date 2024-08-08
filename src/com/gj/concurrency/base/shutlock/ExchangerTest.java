package com.gj.concurrency.base.shutlock;

import java.util.concurrent.Exchanger;

public class ExchangerTest {
    private static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        new Thread(() -> {
            String s = "T1";
            try {
                System.out.println("T1 exchange");
                s = exchanger.exchange(s);
                System.out.println("T1 finish exchange");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + ":" + s);
        }).start();

        new Thread(() -> {
            String s = "T2";
            try {
                System.out.println("T2 exchange");
                s = exchanger.exchange(s);
                System.out.println("T2 finish exchange");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + ":" + s);
        }).start();
    }
}
