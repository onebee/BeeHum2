package com.one.bee.tab

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.one.bee.R
import com.one.library.log.HiLog
import com.one.ui.tab.bottom.HiTabBottomInfo
import com.one.ui.tab.bottom.HiTabBottomLayout


class HiTabBottomDemoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_tab_bottom_demo)
        initTabBottom()
    }

    private fun initTabBottom() {
        val hiTabBottomLayout: HiTabBottomLayout = findViewById(R.id.hi_tab_layout)
        hiTabBottomLayout.setTabAlpha(0.85f)

        val bottomInfoList: MutableList<HiTabBottomInfo<*>> = ArrayList()


        val homeInfo = HiTabBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.if_home),
            null,
            "#ff656667",
            "#ffd44949",
        )

        val favoriteInfo = HiTabBottomInfo(
            "收藏",
            "fonts/iconfont.ttf",
            getString(R.string.if_favorite),
            null,
            "#ff656667",
            "#ffd44949",
        )

        val bitmapSelected =
            BitmapFactory.decodeResource(resources, R.drawable.ic_card_selected, null)
        val bitmapNormal = BitmapFactory.decodeResource(resources, R.drawable.ic_card_normal, null)

        val categoryInfo = HiTabBottomInfo<String>(
            "分类",
            bitmapNormal,
            bitmapSelected,
            "#ff656667",
            "#ffd44949",

            )

        val recommendCategory = HiTabBottomInfo(
            "推荐",
            "fonts/iconfont.ttf",
            getString(R.string.if_recommend),
            null,
            "#ff656667",
            "#ffd44949",
        )

        val profileInfo = HiTabBottomInfo(
            "我的",
            "fonts/iconfont.ttf",
            getString(R.string.if_profile),
            null,
            "#ff656667",
            "#ffd44949",
        )

        bottomInfoList.add(homeInfo)
        bottomInfoList.add(favoriteInfo)
        bottomInfoList.add(categoryInfo)
        bottomInfoList.add(recommendCategory)
        bottomInfoList.add(profileInfo)

        hiTabBottomLayout.inflateInfo(bottomInfoList)
        hiTabBottomLayout.defaultSelected(homeInfo)

        hiTabBottomLayout.addTabSelectedChangeListener { _, _, nextInfo ->
//            Toast.makeText(this@HiTabBottomDemoActivity, nextInfo.name, Toast.LENGTH_SHORT).show()

            HiLog.d(" click = " + nextInfo.name)
        }


        val findTab = hiTabBottomLayout.findTab(profileInfo)


    }


}