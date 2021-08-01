package com.gj.concurrency.base.collections;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author guojie
 * <p>
 * <p>
 * FIFO队列，二者分别对应LinkedList和ArrayList
 * LinkedBlockingQueue
 * ArrayBlockingQueue
 * <p>
 * PriorityBlockingQueue 按优化级排序 的队列，当你希望按照某种顺序而不是FIFO来处理元素时，这个队列将非常有用
 * <p>
 */
public class BlockingQueuePractice {

    public static void main(String[] args) {
        ArrayBlockingQueue<Plate> blockingQueue = new ArrayBlockingQueue(1000);
        ExecutorService executorService = Executors.newCachedThreadPool();
        AtomicInteger atomicInteger = new AtomicInteger();

        for (int i = 0; i < 15; i++) {
            executorService.submit(new Washer(blockingQueue));
        }

        for (int i = 0; i < 3; i++) {
            executorService.submit(new Servicer(blockingQueue,atomicInteger));
        }


    }
}

/**
 * 洗碗工
 */
class Washer implements Runnable {
    private ArrayBlockingQueue<Plate> queue = null;

    public Washer(ArrayBlockingQueue<Plate> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("1洗完了");

            try {
                queue.put(new Plate());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}

class Servicer implements Runnable {

    private ArrayBlockingQueue<Plate> queue = null;
    private AtomicInteger count = null;

    public Servicer(ArrayBlockingQueue<Plate> queue, AtomicInteger atomicInteger) {
        this.queue = queue;
        this.count = atomicInteger;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Plate plate = queue.take();
                System.out.println("2端走了:" + count.addAndGet(1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

class Plate {

}
