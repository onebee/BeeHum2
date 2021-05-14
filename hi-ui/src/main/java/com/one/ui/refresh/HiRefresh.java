package com.one.ui.refresh;

/**
 * @author diaokaibin@gmail.com on 2021/5/14.
 */
public interface HiRefresh {
    /**
     * 刷新的时候 是否禁止滚动
     *
     * @param disableRefreshScroll
     */
    void setDisableRefreshScroll(boolean disableRefreshScroll);


    /**
     * 刷新完成
     */
    void refreshFinished();

    /**
     * 设置下拉刷新监听
     *
     * @param hiRefreshListener
     */
    void setRefreshListener(HiRefreshListener hiRefreshListener);


    void setRefreshOverView(HiOverView hiOverView);


    interface HiRefreshListener {

        void onRefresh();

        boolean enableRefresh();
    }


}
