package com.one.bee;

import com.one.common.ui.component.HiBaseApplication;
import com.one.library.log.HiConsolePrinter;
import com.one.library.log.HiLogConfig;
import com.one.library.log.HiLogManager;

/**
 * @author diaokaibin@gmail.com on 5/1/21.
 */
public class HiApplication extends HiBaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();


        HiLogManager.init(new HiLogConfig() {
            @Override
            public boolean enable() {
                return super.enable();
            }

            @Override
            public boolean includeThread() {
                return false;
            }

            @Override
            public int stackTraceDepth() {
                return 0;
            }
        },new HiConsolePrinter());
    }
}
