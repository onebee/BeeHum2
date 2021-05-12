package com.one.bee;

import android.os.Bundle;

import com.one.bee.logic.MainActivityLogic;
import com.one.common.ui.component.HiBaseActivity;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;

/**
 * 实现ActivityProvider 接口,接口的方法并未复写. 因为方法同名
 */
public class MainActivity extends HiBaseActivity implements MainActivityLogic.ActivityProvider {

    private MainActivityLogic activityLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityLogic = new MainActivityLogic(this,savedInstanceState);

    }

    @Override
    protected void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        activityLogic.onSaveInstanceState(outState);
    }
}