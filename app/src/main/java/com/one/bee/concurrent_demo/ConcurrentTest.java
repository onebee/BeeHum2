package com.one.bee.concurrent_demo;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.one.library.log.HiLog;

import java.util.concurrent.locks.ReentrantLock;

import androidx.annotation.NonNull;

/**
 * @author diaokaibin@gmail.com on 2021/7/28.
 */
public class ConcurrentTest {


    private static int MSG_WHAT_1 = 1;
    private volatile static boolean hasNotify = false;


    public static void test(Context context) {


        class MyAsyncTask extends AsyncTask<String, Integer, String> {

            @Override
            protected String doInBackground(String... params) {
                for (int i = 0; i < 10; i++) {
                    publishProgress(i * 10);
                }
                HiLog.d(" doInBackground " + params[0]);
                return params[0];
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                HiLog.d(" onProgressUpdate " + values[0]);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                HiLog.d(" onPostExecute " + result);

            }
        }

//        MyAsyncTask myAsyncTask = new MyAsyncTask();
//
//        myAsyncTask.execute("execute my async task ");
//
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                HiLog.d(" run : AsyncTask execute");
//            }
//        });
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                HiLog.d(" run1 : AsyncTask execute");
//            }
//        });

//        HandlerThread handlerThread = new HandlerThread("handler-thread");
//
//        handlerThread.start();
//
//        MyHandler handler = new MyHandler(handlerThread.getLooper());
//
//        handler.sendEmptyMessage(MSG_WHAT_1);
//        Message msg = new Message();
//        msg.what = 99;
//        msg.obj = "hello";
//        handler.sendMessage(msg);

//        Thread thread = new Thread();
//        thread.start();
//        int ui_priority = Process.getThreadPriority(0);
//
//        int th_priority = thread.getPriority();
//        HiLog.d(" ui_priority = " + ui_priority);
//        HiLog.d(" th_priority = " + th_priority);


//        Object object = new Object();
//
//        class Runnable1 implements Runnable {
//
//            @Override
//            public void run() {
//                HiLog.d(" run : thread1 start ");
//                synchronized (object) {
//                    try {
//                        if (!hasNotify) {
//                            object.wait(1000);
//                        }
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                HiLog.e(" run : thread1 end ");
//            }
//        }
//
//        class Runnable2 implements Runnable {
//
//            @Override
//            public void run() {
//                HiLog.d(" run : thread2 start ");
//                synchronized (object) {
//                    object.notify();
//                    hasNotify= true;
//                }
//                HiLog.e(" run : thread2 end ");
//            }
//        }
//
//        Thread thread1 = new Thread(new Runnable1());
//        Thread thread2 = new Thread(new Runnable2());
//        thread2.start();
//        thread1.start();

//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                HiLog.d(" run in runnable");
//
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                HiLog.d(" run in runnable end ");
//            }
//        });
//
//        thread.start();
//
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        HiLog.d(" run in main");


//        class LooperThread extends Thread {
//
//            public LooperThread(String name) {
//                super(name);
//            }
//
//            public Looper getMyLooper() {
//                synchronized (this) {
//                    if (myLooper == null && isAlive()) {
//                        try {
//                            wait();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                return myLooper;
//            }
//
//            private Looper myLooper;
//
//            @Override
//            public void run() {
//                Looper.prepare();
//
//                synchronized (this) {
//                    myLooper = Looper.myLooper();
//                    notify();
//                }
//
//                Looper.loop();
//
//
//            }
//        }
//
//        LooperThread looperThread = new LooperThread("looper thread");
//        looperThread.start();
//
//        /**
//         * looperThread 循环得到的消息能够被Handler 处理, Handler 需要绑定一个Looper
//         */
//        Handler handler = new Handler(looperThread.getMyLooper()){
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                super.handleMessage(msg);
//
//                HiLog.d("receive " + msg.what);
//                HiLog.d("receive " + Thread.currentThread().getName());
//            }
//        };
//
//        handler.sendEmptyMessage(MSG_WHAT_1);


        ReentrantLock lock = new ReentrantLock(true);


        lock.lock();



        lock.unlock();

    }

    static class MyHandler extends Handler {

        public MyHandler(Looper looper) {
            super(looper);

        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            HiLog.d("handleMessage : " + msg.what + " , " + msg.obj);
            HiLog.d("handleMessage : " + Thread.currentThread().getName());
        }
    }


}
