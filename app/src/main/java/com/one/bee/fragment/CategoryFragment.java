package com.one.bee.fragment;

import com.one.bee.R;
import com.one.bee.concurrent_demo.ConcurrentTest;
import com.one.common.ui.component.HiBaseFragment;

/**
 * @author diaokaibin@gmail.com on 2021/5/11.
 */
public class CategoryFragment extends HiBaseFragment {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    public void onResume() {
        super.onResume();
        ConcurrentTest.test(getContext());
    }
}
