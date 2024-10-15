package com.alibaba.otter.canal.adapter.launcher.loader;

/**
 * 由于 ready 变量没有内存可见性保证，所以子线程中一直读取的是旧值，子线程while不会停下
 * 被volatile修饰后，子线程会读取新值，while循环停下
 * private static volatile boolean ready = false;
 *
 */
public class VolatileTest {

    private static volatile boolean ready = false;

    public static void main(String[] args) throws InterruptedException {
        // 创建一个读取线程
        Thread readerThread = new Thread(() -> {
            while (!ready) {
                // 读取变量，但由于没有内存可见性保证，可能会一直读取到旧值
                // System.out.println("Reday : " + ready);
            }
            System.out.println("Ready variable is now true!");
        });

        readerThread.start();

        // 模拟一些工作
        Thread.sleep(1000);

        // 修改共享变量
        new Thread(() -> {
            ready = true;
            System.out.println("Set ready to true");
        }).start();

        // 等待一段时间，让读者线程有机会看到最新的值
        Thread.sleep(5000);

    }
}