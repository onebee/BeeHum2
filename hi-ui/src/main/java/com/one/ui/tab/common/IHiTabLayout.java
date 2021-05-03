package com.one.ui.tab.common;

import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author diaokaibin@gmail.com on 5/1/21.
 */
public interface IHiTabLayout<Tab extends ViewGroup, D> {


    Tab findTab(@NotNull D data);

    void addTabSelectedChangeListener(OnTabSelectedListener<D> listener);

    void defaultSelected(@NotNull D defaultInfo);

    void inflateInfo(@NotNull List<D> infoList);

    interface OnTabSelectedListener<D> {
        void onTabSelectedChange(int index, @NotNull D prevInfo, @NotNull D nextInfo);
    }

}
