package com.gj.concurrency.base.shutlock;

import java.util.Random;
import java.util.concurrent.Phaser;

public class PhaserTest {

    private static Random mRandom = new Random();
    private static MarriagePhaser marriagePhaser = new MarriagePhaser();

    static class MarriagePhaser extends Phaser {

        public MarriagePhaser() {
            super();
        }

        public MarriagePhaser(int parties) {
            super(parties);
        }

        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase) {
                case 0:
                    System.out.println(registeredParties + " 人已到场");
                    System.out.println();
                    return false;
                case 1:
                    System.out.println(registeredParties + " 人开始吃饭");
                    System.out.println();
                    return false;
                case 2:
                    System.out.println(registeredParties + " 人已离场");
                    System.out.println();
                    return false;
                case 3:
                    System.out.println(registeredParties + " 结束");
                    System.out.println();
                    return true;
            }
            return super.onAdvance(phase, registeredParties);
        }
    }

    static class Person implements Runnable {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void arrive() {
            try {
                Thread.sleep(mRandom.nextInt(1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(name + " arrive ---");

            marriagePhaser.arriveAndAwaitAdvance();
        }

        public void eat() {
            try {
                Thread.sleep(mRandom.nextInt(1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(name + " eat ---");
            marriagePhaser.arriveAndAwaitAdvance();
        }

        public void leave() {
            try {
                Thread.sleep(mRandom.nextInt(1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(name + " leave ---");
            marriagePhaser.arriveAndAwaitAdvance();
        }

        public void hug() {
            try {
                Thread.sleep(mRandom.nextInt(1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(name + " hug ---");
            marriagePhaser.arriveAndAwaitAdvance();
        }


        @Override
        public void run() {
            arrive();
            eat();
            leave();
            if ("新郎".equals(name) || "新娘".equals(name)) {
                hug();
            } else {
                marriagePhaser.arriveAndDeregister();
            }
        }
    }

    public static void main(String[] args) {
        marriagePhaser.bulkRegister(7);

        for (int i = 0; i < 5; i++) {
            new Thread(new Person("p" + i)).start();
        }

        new Thread(new Person("新郎")).start();
        new Thread(new Person("新娘")).start();

    }
}
