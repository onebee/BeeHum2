package com.one.bee;

import android.os.Bundle;
import android.view.KeyEvent;

import com.one.bee.logic.MainActivityLogic;
import com.one.common.ui.component.HiBaseActivity;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

/**
 * 实现ActivityProvider 接口,接口的方法并未复写. 因为方法同名
 */
public class MainActivity extends HiBaseActivity implements MainActivityLogic.ActivityProvider {

    private MainActivityLogic activityLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityLogic = new MainActivityLogic(this, savedInstanceState);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            // 音量下键
            if (BuildConfig.DEBUG) {
                try {
                    Class<?> aClass = Class.forName("com.one.debug.DebugToolDialogFragment");
                    DialogFragment target = (DialogFragment) aClass.getConstructor().newInstance();
                    target.show(getSupportFragmentManager(), "debug_tool");

                } catch (ClassNotFoundException | NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        activityLogic.onSaveInstanceState(outState);
    }
}