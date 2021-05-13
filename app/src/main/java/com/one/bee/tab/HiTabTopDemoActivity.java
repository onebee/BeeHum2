package com.one.bee.tab;

import android.os.Bundle;

import com.one.bee.R;
import com.one.library.log.HiLog;
import com.one.ui.tab.common.IHiTabLayout;
import com.one.ui.tab.top.HiTabTopInfo;
import com.one.ui.tab.top.HiTabTopLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class HiTabTopDemoActivity extends AppCompatActivity {

    String[] tabsStr = new String[]{"热门",
            "服装",
            "数码",
            "鞋子",
            "零食",
            "家电",
            "汽车",
            "百货",
            "家居",
            "装修",
            "运动"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hi_tab_top_demo);
        initTabTop();
    }

    private void initTabTop() {
        HiTabTopLayout hiTabTopLayout = findViewById(R.id.tab_top_layout);
        List<HiTabTopInfo<?>> infoList = new ArrayList<>();
        int defaultColor = getResources().getColor(R.color.tabBottomDefaultColor);
        int tintColor = getResources().getColor(R.color.tabBottomTintColor);
        for (String s : tabsStr) {

            HiTabTopInfo<?> info = new HiTabTopInfo<>(s, defaultColor, tintColor);
            infoList.add(info);

        }
        hiTabTopLayout.inflateInfo(infoList);

        hiTabTopLayout.addTabSelectedChangeListener(new IHiTabLayout.OnTabSelectedListener<HiTabTopInfo<?>>() {
            @Override
            public void onTabSelectedChange(int index, @NotNull HiTabTopInfo<?> prevInfo, @NotNull HiTabTopInfo<?> nextInfo) {
                HiLog.i(" click = " + nextInfo.name);
            }
        });

        hiTabTopLayout.defaultSelected(infoList.get(0));
    }
}