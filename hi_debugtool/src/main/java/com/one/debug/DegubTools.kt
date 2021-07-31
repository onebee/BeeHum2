package com.one.debug

import android.content.Intent
import android.os.Build
import android.os.Process
import android.provider.ContactsContract
import com.one.common.utils.SPUtil
import com.one.hi_debugtool.BuildConfig
import com.one.library.util.AppGlobals

/**
 * @author  diaokaibin@gmail.com on 2021/7/30.
 */
class DebugTools {


    fun buildVersion(): String {
        //TODO :修改
        return "构建版本:"
    }


    fun buildTime(): String {
        return "构建时间:" + BuildConfig.BUILD_TIME
    }

    @HiDebug(name = "一键开启Https降级", desc = "降级成Http,可以使用抓包工具明文抓包")
    fun degrade2Http() {
        SPUtil.putBoolean("degrade_http", true)
        val context = AppGlobals.get()?.applicationContext ?: return
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)

        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)


        Process.killProcess(Process.myPid())
    }

}