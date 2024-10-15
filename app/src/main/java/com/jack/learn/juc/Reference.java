package com.jack.learn.juc;

import com.jack.learn.juc.basic.Student;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

public class Reference {

    public static void main(String[] args) {
        Student student = new Student();
        ReferenceQueue<Student> queue = new ReferenceQueue<>();
        WeakReference<Student> weakReference = new WeakReference<>(student,queue);
        CountDownLatch countDownLatch = new CountDownLatch(3);
        countDownLatch.countDown();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ReentrantLock lock = new ReentrantLock(false);
        lock.lock();
        lock.unlock();
    }
}
