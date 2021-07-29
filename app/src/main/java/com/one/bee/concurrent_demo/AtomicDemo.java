package com.one.bee.concurrent_demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author diaokaibin@gmail.com on 2021/7/29.
 */
public class AtomicDemo {
    public static void main(String[] args) {

        AtomicTask atomicTask = new AtomicTask();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 10000; i++) {
                    atomicTask.incrementAtomic();
                    atomicTask.incrementVolatile();
                }
            }
        };

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("原子类结果 : " + atomicTask.atomicInteger.get());
        System.out.println("volatile类结果 : " + atomicTask.volatileCount);
    }

    static class AtomicTask{
        AtomicInteger atomicInteger = new AtomicInteger();

        volatile int volatileCount = 0;


        void incrementAtomic(){
            atomicInteger.getAndIncrement();
        }

        void incrementVolatile(){
            volatileCount++;
        }


    }

}
