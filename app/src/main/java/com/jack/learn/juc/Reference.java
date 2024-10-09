package com.jack.learn.juc;

import com.jack.learn.juc.basic.Student;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class Reference {

    public static void main(String[] args) {
        Student student = new Student();
        ReferenceQueue<Student> queue = new ReferenceQueue<>();
        WeakReference<Student> weakReference = new WeakReference<>(student,queue);
    }
}
