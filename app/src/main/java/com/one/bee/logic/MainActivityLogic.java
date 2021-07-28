package com.one.bee.logic;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.one.bee.R;
import com.one.bee.fragment.CategoryFragment;
import com.one.bee.fragment.FavoriteFragment;
import com.one.bee.fragment.HomePageFragment;
import com.one.bee.fragment.ProfileFragment;
import com.one.bee.fragment.RecommendFragment;
import com.one.common.tab.HiFragmentTabView;
import com.one.common.tab.HiTabViewAdapter;
import com.one.library.log.HiLog;
import com.one.library.util.HiDisplayUtil;
import com.one.ui.tab.bottom.HiTabBottom;
import com.one.ui.tab.bottom.HiTabBottomInfo;
import com.one.ui.tab.bottom.HiTabBottomLayout;
import com.one.ui.tab.common.IHiTabLayout;

import org.jetbrains.annotations.NotNull;

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
    private final static String SAVED_CURRENT_ID = "SAVED_CURRENT_ID";

    public MainActivityLogic(ActivityProvider activityProvider, Bundle savedInstanceState) {
        this.activityProvider = activityProvider;

        // fix 不保留活动导致的Fragment 重叠问题
        if (savedInstanceState != null) {
            currentItemIndex = savedInstanceState.getInt(SAVED_CURRENT_ID);
        }
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

        profileInfo.fragment = ProfileFragment.class;
        homeInfo.fragment = HomePageFragment.class;
        favoriteInfo.fragment = FavoriteFragment.class;
        categoryInfo.fragment = CategoryFragment.class;
        recommendCategory.fragment = RecommendFragment.class;

        infoList.add(homeInfo);
        infoList.add(favoriteInfo);
        infoList.add(profileInfo);
        infoList.add(recommendCategory);
        infoList.add(categoryInfo);

        hiTabBottomLayout.inflateInfo(infoList);

        initFragmentTabView();


        hiTabBottomLayout.defaultSelected(infoList.get(currentItemIndex));

        HiTabBottom findTab = hiTabBottomLayout.findTab(profileInfo);
        findTab.resetHeight(HiDisplayUtil.dp2px(66f, activityProvider.getResources()));
        hiTabBottomLayout.addTabSelectedChangeListener(

                new IHiTabLayout.OnTabSelectedListener<HiTabBottomInfo<?>>() {
                    @Override
                    public void onTabSelectedChange(int index, @NotNull HiTabBottomInfo<?> prevInfo, @NotNull HiTabBottomInfo<?> nextInfo) {
                        HiLog.i("hiTabBottomLayout  addTabSelectedChangeListener prev = " + prevInfo.name + " , next = " + nextInfo.name);
                        fragmentTabView.setCurrentItem(index);
                        MainActivityLogic.this.currentItemIndex = index;
                    }
                });
    }

    private void initFragmentTabView() {
        HiTabViewAdapter tabViewAdapter = new HiTabViewAdapter(infoList, activityProvider.getSupportFragmentManager());
        fragmentTabView = activityProvider.findViewById(R.id.fragment_tab_view);
        fragmentTabView.setAdapter(tabViewAdapter);

    }


    public HiFragmentTabView getFragmentTabView() {
        return fragmentTabView;
    }

    public HiTabBottomLayout getHiTabBottomLayout() {
        return hiTabBottomLayout;
    }

    public List<HiTabBottomInfo<?>> getInfoList() {
        return infoList;
    }

    public void onSaveInstanceState(Bundle state) {
        state.putInt(SAVED_CURRENT_ID, currentItemIndex);

    }


    public interface ActivityProvider {
        <T extends View> T findViewById(@IdRes int id);

        Resources getResources();

        FragmentManager getSupportFragmentManager();

        String getString(@StringRes int resId);
    }


}
