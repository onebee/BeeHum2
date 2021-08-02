package com.one.library.taskflow

/**
 * @author  diaokaibin@gmail.com on 2021/8/2.
 */
interface TaskListener {

    fun onStart(task: Task)
    fun onRunning(task: Task)
    fun onFinished(task: Task)
}