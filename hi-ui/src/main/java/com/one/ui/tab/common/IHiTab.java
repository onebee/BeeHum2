package com.one.ui.tab.common;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.Px;

/**
 * @author diaokaibin@gmail.com on 5/3/21.
 */
public interface IHiTab<D> extends IHiTabLayout.OnTabSelectedListener<D> {


    void setHiTabInfo(@NotNull D data);


    /**
     * 动态修改某个item 的大小
     * @param height
     */
    void resetHeight(@Px int height);
}
