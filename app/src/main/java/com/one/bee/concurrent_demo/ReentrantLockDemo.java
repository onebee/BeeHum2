package com.one.bee.concurrent_demo;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author diaokaibin@gmail.com on 2021/7/29.
 */
public class ReentrantLockDemo {

    static class ReentrantLockTask {

        ReentrantLock lock = new ReentrantLock();


        void buyTicket() {
            try {

                String name = Thread.currentThread().getName();
                lock.lock();
                System.out.println(name + " : 准备好了");
                Thread.sleep(100);
                System.out.println(name+ "买好了");

                lock.lock();
                System.out.println(name + " : 又准备好了");
                Thread.sleep(100);
                System.out.println(name+ "又买好了");

                lock.lock();
                System.out.println(name + " : 又又准备好了");
                Thread.sleep(100);
                System.out.println(name+ "又又买好了");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                lock.unlock();
                lock.unlock();
            }
        }


    }

    public static void main(String[] args) {

        ReentrantLockTask task = new ReentrantLockTask();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                task.buyTicket();
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }

    }

}
