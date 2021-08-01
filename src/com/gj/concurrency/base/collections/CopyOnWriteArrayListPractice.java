package com.gj.concurrency.base.collections;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author guojie
 * <p>
 * <p>
 * 当迭代操作远远多于修改操作时，才应用使用该容器
 */
public class CopyOnWriteArrayListPractice {

    private CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();

    private void addItem(String item) {
        copyOnWriteArrayList.add(item);
    }

    private void iterator() {
        StringBuffer stringBuffer = new StringBuffer("result " + "size:" + copyOnWriteArrayList.size()+"   :");

        Iterator iterator = copyOnWriteArrayList.iterator();
        while (iterator.hasNext()) {
            stringBuffer.append(iterator.next());
            stringBuffer.append(",");
        }

        System.out.println("  " + stringBuffer);
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CopyOnWriteArrayListPractice copyOnWriteArrayListPractice = new CopyOnWriteArrayListPractice();

        for (int i = 0; i < 1000; i++) {
            int j = i;
            executorService.submit(() -> {
                if (j % 5 == 0) {
                    copyOnWriteArrayListPractice.addItem("" + j);
                } else {
                    copyOnWriteArrayListPractice.iterator();
                }
            });
        }

    }
}
