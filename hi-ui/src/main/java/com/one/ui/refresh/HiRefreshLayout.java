package com.one.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author diaokaibin@gmail.com on 2021/5/14.
 */
public class HiRefreshLayout extends FrameLayout implements HiRefresh {

    private HiOverView.HiRefreshState state;
    private GestureDetector gestureDetector;
    private HiRefreshListener refreshListener;
    protected HiOverView hiOverView;
    private int lastY;
    /**
     * 刷新时是否禁止滚动
     */
    private boolean disableRefreshScroll;

    private AutoScroller autoScroller;

    public HiRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public HiRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HiRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        gestureDetector = new GestureDetector(getContext(), hiGestureDetector);
        autoScroller = new AutoScroller();

    }

    @Override
    public void setDisableRefreshScroll(boolean disableRefreshScroll) {

    }

    @Override
    public void refreshFinished() {

    }

    @Override
    public void setRefreshListener(HiRefreshListener hiRefreshListener) {
        this.refreshListener = hiRefreshListener;

    }

    @Override
    public void setRefreshOverView(HiOverView hiOverView) {
        this.hiOverView = hiOverView;

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        View head = getChildAt(0);
        if (ev.getAction() == MotionEvent.ACTION_UP ||
                ev.getAction() == MotionEvent.ACTION_CANCEL ||
                ev.getAction() == MotionEvent.ACTION_POINTER_INDEX_MASK
        ) {
            // 松开手
            if (head.getBottom() > 0) {
                if (state != HiOverView.HiRefreshState.STATE_REFRESH) {// 非正在刷新的状态
                    recover(head.getBottom());
                    return false; // 表示不再处理触摸事件

                }
            }
            lastY=0; // 松手后设置为0
        }

        return super.dispatchTouchEvent(ev);
    }

    /**
     * 用户松开手,头部滚回去
     *
     * @param dis
     */
    private void recover(int dis) {
        if (refreshListener != null && dis > hiOverView.pullRefreshHeight) {
            // 滚动到指定位置 dis-hiOverView.pullRefreshHeight
            autoScroller.recover(dis - hiOverView.pullRefreshHeight);
            state = HiOverView.HiRefreshState.STATE_OVER_RELEASE;
        } else {
            autoScroller.recover(dis);
        }
    }

    /**
     * 借助 Scroller 实现视图的自动滚动
     */
    private class AutoScroller implements Runnable {

        private Scroller scroller;
        private int lastY;

        private boolean isFinished;


        public AutoScroller() {
            scroller = new Scroller(getContext(), new LinearInterpolator());
            isFinished = true;
        }


        @Override
        public void run() {
            if (scroller.computeScrollOffset()) {//还未完成滚动
                lastY = scroller.getCurrY();
                post(this);

            } else {
                removeCallbacks(this);
                isFinished = true;
            }

        }

        void recover(int dis) {
            if (dis < 0) {
                return;
            }
            removeCallbacks(this);
            lastY = 0;
            isFinished = false;
            scroller.startScroll(0, 0, 0, dis, 300);
            post(this);
        }

        boolean isFinished() {
            return isFinished;
        }
    }

    HiGestureDetector hiGestureDetector = new HiGestureDetector() {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    };
}
