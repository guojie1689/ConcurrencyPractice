package com.gj.concurrency.base;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 通常情况下，我们创建的成员变量都是线程不安全的。
 * 因为他可能被多个线程同时修改，此变量对于多个线程之间彼此并不独立，是共享变量。
 * 而使用ThreadLocal创建的变量只能被当前线程访问，其他线程无法访问和修改。也就是说：将线程公有化变成线程私有化。
 */
public class ThreadLocalPratices {
    private static List values = new CopyOnWriteArrayList();

    public static void main(String[] args) {
        System.out.println("开始分配工作");
        for (int i = 0; i < 100000; i++) {
            int j = i;
            new Thread(() -> {
                String value = DataFormatUtils.dateToStr(j * 100000);
                if (values.contains(value)) {
                    System.out.println("values has");
                } else {
                    values.add(values);
//                    System.out.println("add values :" + value);
                }

            }).start();
        }
        System.out.println("工作分配完毕");
    }
}

class DataFormatUtils {
    private static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));

    public static String dateToStr(int millisSeconds) {
        SimpleDateFormat simpleDateFormat = dateFormatThreadLocal.get();
        Date date = new Date(millisSeconds);
        return simpleDateFormat.format(date);
    }
}