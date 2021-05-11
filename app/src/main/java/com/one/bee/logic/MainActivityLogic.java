package com.one.bee.logic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.one.bee.R;
import com.one.common.tab.HiFragmentTabView;
import com.one.library.util.HiDisplayUtil;
import com.one.ui.tab.bottom.HiTabBottom;
import com.one.ui.tab.bottom.HiTabBottomInfo;
import com.one.ui.tab.bottom.HiTabBottomLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;

/**
 * @author diaokaibin@gmail.com on 2021/5/11.
 * 将MainActivity 的一些逻辑内聚在这,让MainActivity 更加清爽
 */
public class MainActivityLogic {

    private HiFragmentTabView fragmentTabView;
    private HiTabBottomLayout hiTabBottomLayout;
    private List<HiTabBottomInfo<?>> infoList;

    /**
     * Activity 提供的能力 , MainActivityLogic持有 ActivityProvider,
     * 不需要持有具体的MainActivity 进行解耦
     */
    private ActivityProvider activityProvider;
    private int currentItemIndex;

    public MainActivityLogic(ActivityProvider activityProvider) {
        this.activityProvider = activityProvider;
        initTabBottom();
    }


    private void initTabBottom() {
        hiTabBottomLayout = activityProvider.findViewById(R.id.tab_bottom_layout);
        hiTabBottomLayout.setAlpha(0.85f);
        infoList = new ArrayList<>();
        int defaultColor = activityProvider.getResources().getColor(R.color.tabBottomDefaultColor);
        int tintColor = activityProvider.getResources().getColor(R.color.tabBottomTintColor);


        HiTabBottomInfo homeInfo = new HiTabBottomInfo(
                "首页",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_home),
                null,
                defaultColor,
                tintColor
        );

        HiTabBottomInfo favoriteInfo = new HiTabBottomInfo(
                "收藏",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_favorite),
                null,
                defaultColor,
                tintColor
        );

        Bitmap bitmapSelected =
                BitmapFactory.decodeResource(activityProvider.getResources(), R.drawable.ic_card_selected, null);
        Bitmap bitmapNormal = BitmapFactory.decodeResource(activityProvider.getResources(), R.drawable.ic_card_normal, null);

        HiTabBottomInfo categoryInfo = new HiTabBottomInfo(
                "分类",
                bitmapNormal,
                bitmapSelected,
                defaultColor,
                tintColor

        );

        HiTabBottomInfo recommendCategory = new HiTabBottomInfo(
                "推荐",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_recommend),
                null,
                defaultColor,
                tintColor
        );
        Bitmap bitmap1 = BitmapFactory.decodeResource(activityProvider.getResources(), R.drawable.fire, null);

        HiTabBottomInfo profileInfo = new HiTabBottomInfo<String>(
                "我的",
                bitmap1,
                bitmap1

        );

        infoList.add(homeInfo);
        infoList.add(favoriteInfo);
        infoList.add(categoryInfo);
        infoList.add(recommendCategory);
        infoList.add(profileInfo);

        hiTabBottomLayout.inflateInfo(infoList);
        hiTabBottomLayout.defaultSelected(homeInfo);


        HiTabBottom findTab = hiTabBottomLayout.findTab(profileInfo);
        findTab.resetHeight(HiDisplayUtil.dp2px(66f, activityProvider.getResources()));

    }

    public interface ActivityProvider {
        <T extends View> T findViewById(@IdRes int id);

        Resources getResources();

        FragmentManager getSupportFragmentManager();

        String getString(@StringRes int resId);
    }


}
