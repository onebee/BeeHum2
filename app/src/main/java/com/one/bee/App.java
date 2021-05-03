package com.one.bee;

import android.app.Application;

import com.one.library.log.HiConsolePrinter;
import com.one.library.log.HiLogConfig;
import com.one.library.log.HiLogManager;

/**
 * @author diaokaibin@gmail.com on 5/1/21.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        HiLogManager.init(new HiLogConfig() {
            @Override
            public boolean enable() {
                return super.enable();
            }
        },new HiConsolePrinter());
    }
}
