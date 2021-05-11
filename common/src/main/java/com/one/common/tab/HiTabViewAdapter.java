package com.one.common.tab;

import android.view.View;

import com.one.ui.tab.bottom.HiTabBottomInfo;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * @author diaokaibin@gmail.com on 2021/5/10.
 */
public class HiTabViewAdapter {

    private List<HiTabBottomInfo<?>> infoList;

    private Fragment curFragment;
    private FragmentManager fragmentManager;

    public HiTabViewAdapter(List<HiTabBottomInfo<?>> infoList, FragmentManager fragmentManager) {
        this.infoList = infoList;
        this.fragmentManager = fragmentManager;
    }

    /**
     * 实例化以及显示指定位置的fragment
     *
     * @param container
     * @param position
     */
    public void instantiateItem(View container, int position) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (curFragment != null) {
            transaction.hide(curFragment);
        }
        String name = container.getId() + ":" + position;
        Fragment fragment = fragmentManager.findFragmentByTag(name);
        if (fragment != null) {
            transaction.show(fragment);
        } else {
            fragment = getItem(position);
            if (!fragment.isAdded()) {
                transaction.add(container.getId(), fragment, name);
            }
        }

        curFragment = fragment;
        transaction.commitAllowingStateLoss();
    }

    public Fragment getItem(int position) {
        try {
            return infoList.get(position).fragment.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Fragment getCurFragment() {
        return curFragment;
    }

    public int getCount() {
        return infoList == null ? 0 : infoList.size();
    }

}
