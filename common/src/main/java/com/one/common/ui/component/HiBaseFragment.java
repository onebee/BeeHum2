package com.one.common.ui.component;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author diaokaibin@gmail.com on 2021/5/10.
 */
public abstract class HiBaseFragment extends Fragment {

    /**
     * Fragment 的rootView
     */
    protected View layoutView;

    @LayoutRes
    public abstract int getLayoutId();

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        layoutView = inflater.inflate(getLayoutId(), container, false);
        return layoutView;
    }

}
