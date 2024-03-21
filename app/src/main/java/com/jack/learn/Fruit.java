package com.jack.learn;

public class Fruit {
    static int x = 0;//在方法区
    static BigWaterMelon bigWater;// 不会加载进内存 只是在方法区中有个引用
    static BigWaterMelon bigWater2  = new BigWaterMelon(10); // 加载进内存 在堆去开辟对象 方法区中的bigWater2指向堆中的内存地址
    BigWaterMelon bigWaterMelon = new BigWaterMelon(20);// bigWaterMelon变量在Fruit中
    public static void main(String[] args) {
        // 1.加载Fruit.class到方法区 2.在堆中创建Fruit对象
        Fruit fruit = new Fruit();
        int z = 30; //z在栈区
        BigWaterMelon bigWaterMelon3 = new BigWaterMelon(20); //bigWaterMelon3在栈区

        new Thread(new Runnable() {
            @Override
            public void run() {
                int d = 10; // 新创建一个栈
            }
        }).start();
    }

    static class BigWaterMelon {
        public int weight;
        public BigWaterMelon(int weight) {
            this.weight = weight;
        }

    }
}
