package com.one.ui.tab.top;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.one.library.util.HiDisplayUtil;
import com.one.ui.tab.common.IHiTabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author diaokaibin@gmail.com on 2021/5/13.
 */
public class HiTabTopLayout extends HorizontalScrollView implements IHiTabLayout<HiTabTop, HiTabTopInfo<?>> {

    private List<OnTabSelectedListener<HiTabTopInfo<?>>> tabSelectedListeners = new ArrayList<>();
    private HiTabTopInfo<?> selectedInfo;
    private List<HiTabTopInfo<?>> infoList;


    public HiTabTopLayout(Context context) {
        this(context, null);
    }

    public HiTabTopLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HiTabTopLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setVerticalFadingEdgeEnabled(false);
    }

    @Override
    public HiTabTop findTab(@NotNull HiTabTopInfo<?> info) {
        ViewGroup ll = getRootLayout(false);
        for (int i = 0; i < ll.getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if (child instanceof HiTabTop) {
                HiTabTop tap = (HiTabTop) child;
                if (tap.getHiTabInfo() == info) {
                    return tap;
                }
            }
        }
        return null;
    }


    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<HiTabTopInfo<?>> listener) {
        tabSelectedListeners.add(listener);
    }

    @Override
    public void defaultSelected(@NotNull HiTabTopInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    @Override
    public void inflateInfo(@NotNull List<HiTabTopInfo<?>> infoList) {
        if (infoList.isEmpty()) {
            return;
        }

        this.infoList = infoList;
        LinearLayout linearLayout = getRootLayout(true);
        selectedInfo = null;

        Iterator<OnTabSelectedListener<HiTabTopInfo<?>>> iterator = tabSelectedListeners.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof HiTabTop) {
                iterator.remove();
            }
        }

        for (int i = 0; i < infoList.size(); i++) {
            HiTabTopInfo<?> info = infoList.get(i);
            HiTabTop tap = new HiTabTop(getContext());
            tabSelectedListeners.add(tap);
            tap.setHiTabInfo(info);
            linearLayout.addView(tap);
            tap.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelected(info);
                }
            });
        }
    }

    private void onSelected(@NonNull HiTabTopInfo<?> nextInfo) {
        for (OnTabSelectedListener<HiTabTopInfo<?>> listener : tabSelectedListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        }
        this.selectedInfo = nextInfo;
        autoScroll(nextInfo);
    }

    int tabWith;

    private void autoScroll(HiTabTopInfo<?> info) {
        HiTabTop tabTop = findTab(info);
        if (tabTop == null) return;

        int index = infoList.indexOf(info);
        int[] loc = new int[2];
        /**
         * ??????????????????????????????????????????
         */
        tabTop.getLocationInWindow(loc);

        if (tabWith == 0) {
            tabWith = tabTop.getWidth();
        }

        /**
         * ?????????????????????
         */
        int scrollWidth;

        /**
         * ??????????????????????????????????????????
         */
        if ((loc[0] + tabWith / 2) > HiDisplayUtil.getDisplayWidthInPx(getContext())/2) {
            scrollWidth = rangeScrollWidth(index, 3);
        } else {
            //??????
            scrollWidth = rangeScrollWidth(index, -3);
        }

        scrollTo(getScrollX()+scrollWidth,0);
    }

    /**
     * ????????????????????????
     *
     * @param index ??????????????????
     * @param range ?????????????????????
     * @return ??????????????????
     */
    private int rangeScrollWidth(int index, int range) {
        int scrollWidth = 0;
        for (int i = 0; i <= Math.abs(range); i++) {
            int next;
            if (range < 0) { //?????????
                next = range + i + index;
            } else {
                next = range - i + index;

            }
            if (next >= 0 && next < infoList.size()) {
                if (range < 0) {// ???????????????
                    scrollWidth -= scrollWidth(next, false);
                } else {
                    scrollWidth += scrollWidth(next, true);

                }
            }
        }
        return scrollWidth;

    }

    /**
     * ????????????????????? ??????????????????
     *
     * @param index   ?????????????????????
     * @param toRight ???????????????????????????
     * @return ??????????????????
     */
    private int scrollWidth(int index, boolean toRight) {
        HiTabTop target = findTab(infoList.get(index));
        if (target == null) return 0;

        Rect rect = new Rect();
        target.getLocalVisibleRect(rect);
        if (toRight) {
            // right ??????????????????????????????,????????????????????????
            if (rect.right > tabWith) {
                // ????????????????????????????????????
                return tabWith;
            } else {
                // ????????????,????????????????????????
                return tabWith - rect.right;
            }
        } else {
            if (rect.left <= -tabWith) {
                // left ?????????????????????????????????,????????????????????????
                return tabWith;
            } else if (rect.left > 0) {
                // ????????????
                return rect.left;
            }
            return 0;
        }

    }

    private LinearLayout getRootLayout(boolean clear) {
        LinearLayout rootView = (LinearLayout) getChildAt(0);
        if (rootView == null) {
            rootView = new LinearLayout(getContext());
            rootView.setOrientation(LinearLayout.HORIZONTAL);

            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            addView(rootView, params);
        } else if (clear) {
            rootView.removeAllViews();
        }
        return rootView;
    }
}
