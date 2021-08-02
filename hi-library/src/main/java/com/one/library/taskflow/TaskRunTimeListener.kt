package com.one.library.taskflow

import com.one.library.BuildConfig
import com.one.library.log.HiLog

/**
 * @author  diaokaibin@gmail.com on 2021/8/2.
 */
class TaskRunTimeListener : TaskListener {
    override fun onStart(task: Task) {
        if (BuildConfig.DEBUG) {
            HiLog.dt(TAG,task.id + START_METHOD)
        }
    }

    override fun onRunning(task: Task) {
        if (BuildConfig.DEBUG) {
            HiLog.dt(TAG,task.id + RUNNING_METHOD)
        }
    }

    override fun onFinished(task: Task) {
    }

    companion object {
        const val TAG: String = "TaskFlow"
        const val START_METHOD = "-- onStart --"
        const val RUNNING_METHOD = "-- onRunning --"
        const val FINISHED_METHOD = "-- onFinished --"
    }


}