package com.one.ui.tab.bottom;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

/**
 * @author diaokaibin@gmail.com on 5/3/21.
 */
public class HiTabBottomInfo <Color>{
    public enum TabType {
        BITMAP,ICON
    }

    public Class<? extends Fragment> fragment;
    public String name;
    public Bitmap defaultBitmap;
    public Bitmap selectedBitmap;
    public String iconFont;
    public String defaultIconName;
    public String selectedIconName;
    public Color defaultColor;
    public Color tintColor;
    public TabType tabType;

    public HiTabBottomInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        tabType = TabType.BITMAP;
    }

    public HiTabBottomInfo(String name, Bitmap defaultBitmap, Bitmap selectedBitmap,Color defaultColor, Color tintColor) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectedBitmap = selectedBitmap;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        tabType = TabType.BITMAP;
    }


    public HiTabBottomInfo(String name, String iconFont, String defaultIconName, String selectedIconName, Color defaultColor, Color tintColor) {
        this.name = name;
        this.iconFont = iconFont;
        this.defaultIconName = defaultIconName;
        this.selectedIconName = selectedIconName;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        tabType = TabType.ICON;
    }
}
