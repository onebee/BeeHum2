package com.one.library.taskflow

import androidx.core.os.TraceCompat
import java.lang.RuntimeException

/**
 * @author  diaokaibin@gmail.com on 2021/8/2.
 */
abstract class Task @JvmOverloads constructor(
    val id: String
    /***任务名称**/
    ,
    val isAsyncTask: Boolean
    /**是否是异步任务*/
    ,
    val delayMills: Long = 0,
    /**延迟执行的时间*/
    var priority: Int = 0
    /**任务的优先级**/

) : Runnable, Comparable<Task> {

    var executeTime: Long = 0
        // 任务执行时间
        protected set
    var state: Int = TasKState.IDLE
        // 任务的状态
        protected set


    /**
     * 当前task 依赖了哪些前置任务,只有当dependTasks 集合中的所有任务执行完
     * 当前才可执行
     */
    val dependTasks: MutableList<Task> = ArrayList()

    /**
     * 当前task 被哪些后置任务依赖,只有当前的task 执行完,
     * behindTasks集合中的后置任务才可以执行
     */
    val behindTasks: MutableList<Task> = ArrayList()


    /***
     * 任务运行状态监听集合
     */
    private val taskListeners: MutableList<TaskListener> = ArrayList()


    /**
     * 用于输出task 运行时日志
     */
    private val taskRuntimeListener = TaskRunTimeListener()

    fun addTaskListener(taskListener: TaskListener) {
        if (!taskListeners.contains(taskListener)) {
            taskListeners.add(taskListener)
        }
    }

    open fun start() {
        if (state != TasKState.IDLE) {
            throw RuntimeException("cannot run task $id again")
        }

        toStart()
        executeTime = System.currentTimeMillis()
        executeTask(this)

    }

    private fun toStart() {
        state = TasKState.START
        for (listener in taskListeners) {
            listener.onStart(this)
        }
        taskRuntimeListener.onStart(this)
    }

    override fun run() {
        // 改变任务的状态 onStart onRunning onFinshed -- 通知后置任务去开始执行
        TraceCompat.beginSection(id)

        toRunning()
        run(id)  // 真正的执行 初始化任务的代码的方法

        toFinish()
        TraceCompat.endSection()
    }

    private fun toFinish() {
        state = TasKState.FINISHED
        for (listener in taskListeners) {
            listener.onStart(this)
        }
        taskRuntimeListener.onFinished(this)

    }

    private fun toRunning() {
        state = TasKState.RUNNING
        for (listener in taskListeners) {
            listener.onStart(this)
        }
        taskRuntimeListener.onRunning(this)
    }

    abstract fun run(id: String)
}