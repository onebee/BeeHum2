package com.one.library.taskflow

import androidx.annotation.IntDef

/**
 * @author  diaokaibin@gmail.com on 2021/8/2.
 */
@Retention(AnnotationRetention.SOURCE)
@IntDef(
    TasKState.IDLE,
    TasKState.START,
    TasKState.RUNNING,
    TasKState.FINISHED,
)
annotation class TasKState {

    companion object {
        const val IDLE = 0 //静止
        const val START = 1 // 启动,可能需要等待调度
        const val RUNNING = 2  // 运行
        const val FINISHED = 3  // 运行结束

    }

}
