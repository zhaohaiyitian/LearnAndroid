package com.jack.learn.juc;

public class WaitNotifySample {

    public static final Object obj = new Object();
    public static boolean isReady = false;

    public static void main(String[] args) {
        Thread waitThread = new Thread(() -> {
            synchronized (obj) {
                while (!isReady) {
                    try {
                        System.out.println("等待中..........");
                        obj.wait();
                        System.out.println("运行中..........");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        Thread notifyThread = new Thread(() -> {
            synchronized (obj) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("唤醒等待的线程..........");
                isReady = true;
                obj.notify();
            }
        });

        waitThread.start();
        notifyThread.start();
    }
}
