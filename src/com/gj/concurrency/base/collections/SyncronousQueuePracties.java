package com.gj.concurrency.base.collections;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @author guojie
 * SynchronousQueue，实际上它不是一个真正的队列，因为它不会为队列中元素维护存储空间。与其他队列不同的是，它维护一组线程，这些线程在等待着把元素加入或移出队列。
 * 如果以洗盘子的比喻为例，那么这就相当于没有盘架，而是将洗好的盘子直接放入下一个空闲的烘干机中。这种实现队列的方式看似很奇怪，但由于可以直接交付工作，从而降低了将数据从生产者移动到消费者的延迟。（在传统的队列中，在一个工作单元可以交付之前，必须通过串行方式首先完成入列[Enqueue]或者出列[Dequeue]等操作。）
 * <p>
 * 直接交付方式还会将更多关于任务状态的信息反馈给生产者。当交付被接受时，它就知道消费者已经得到了任务，而不是简单地把任务放入一个队列——这种区别就好比将文件直接交给同事，还是将文件放到她的邮箱中并希望她能尽快拿到文件。
 * <p>
 * 因为SynchronousQueue没有存储功能，因此put和take会一直阻塞，直到有另一个线程已经准备好参与到交付过程中。仅当有足够多的消费者，并且总是有一个消费者准备好获取交付的工作时，才适合使用同步队列。
 */
public class SyncronousQueuePracties {

    static class Dispatcher implements Runnable {
        private BlockingQueue blockingQueue = null;

        public Dispatcher(BlockingQueue blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {

                System.out.println("第" + i + "号开始投递");

                try {
                    blockingQueue.put("第{" + i + "}件快递");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Courier implements Runnable {
        private BlockingQueue<String> blockingQueue = null;
        private int code;

        public Courier(int code, BlockingQueue<String> blockingQueue) {
            this.code = code;
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String packageName = blockingQueue.take();
                    System.out.println(code + " 号投递了" + packageName);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        BlockingQueue blockingQueue = new SynchronousQueue();

        Dispatcher dispatcher = new Dispatcher(blockingQueue);

        new Thread(dispatcher).start();

        Courier courier = new Courier(1, blockingQueue);
        Courier courier2 = new Courier(2, blockingQueue);
        Courier courier3 = new Courier(3, blockingQueue);

        new Thread(courier).start();
        new Thread(courier2).start();
        new Thread(courier3).start();

    }
}
