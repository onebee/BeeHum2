package com.one.bee.concurrent_demo;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import com.one.library.log.HiLog;

import androidx.annotation.NonNull;

/**
 * @author diaokaibin@gmail.com on 2021/7/28.
 */
public class ConcurrentTest {


    private static int MSG_WHAT_1= 1;

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

        HandlerThread handlerThread = new HandlerThread("handler-thread");

        handlerThread.start();

        MyHandler handler = new MyHandler(handlerThread.getLooper());

        handler.sendEmptyMessage(MSG_WHAT_1);
        Message msg = new Message();
        msg.what = 99;
        msg.obj = "hello";
        handler.sendMessage(msg);
    }

    static class  MyHandler extends Handler{

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
