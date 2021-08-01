package com.gj.concurrency.base.threadpool;

import java.util.Random;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author guojie
 * <p>
 * 如果向Executor提交了一组计算任务，并且希望在计算完成后获得结果，那么可以保留与每个任务关联的Future，然后反复使用get方法，同时将参数timeout 指定为0，从而通过轮询来判断任务是否完成，这种方法虽然可对得起，但却有些繁锁。
 * CompletionService 将Executor 和BlockingQueue的功能融合在一起，可以将Callable任务提交给它来执行，然后使用类似于队列操作的take和poll等方法来获得已完成的结果
 */
public class CompletionServicePractices {

    static class Image {
        private String name;

        public Image(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 5, 5L, TimeUnit.SECONDS, new LinkedBlockingDeque());
        CompletionService<Image> completionService = new ExecutorCompletionService(executor);

        for (int i = 0; i < 5; i++) {
            int j = i;
            completionService.submit(() -> {
                Thread.sleep(1000 + random.nextInt(2000));
                return new Image("IMAGE_" + j);
            });
        }

        for (int i = 0; i < 5; i++) {
            try {
                Future<Image> imageTask = completionService.take();
                Image image = imageTask.get();
                System.out.println(image + " 加载完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
    }
}
