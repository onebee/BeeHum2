package com.one.ui.tab.top;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

/**
 * @author diaokaibin@gmail.com on 5/3/21.
 */
public class HiTabTopInfo<Color>{
    public enum TabType {
        BITMAP,TEXT
    }

    public Class<? extends Fragment> fragment;
    public String name;
    public Bitmap defaultBitmap;
    public Bitmap selectedBitmap;

    public Color defaultColor;
    public Color tintColor;
    public TabType tabType;

    public HiTabTopInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        tabType = TabType.BITMAP;
    }

    public HiTabTopInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap, Color defaultColor, Color tintColor) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        tabType = TabType.BITMAP;
    }


    public HiTabTopInfo(String name,  Color defaultColor, Color tintColor) {
        this.name = name;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        tabType = TabType.TEXT;
    }
}
