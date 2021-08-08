package com.one.library.taskflow

import androidx.core.os.TraceCompat
import java.lang.RuntimeException
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

/**
 * @author  diaokaibin@gmail.com on 2021/8/2.
 */
abstract class Task @JvmOverloads constructor(
    val id: String
    /***任务名称**/
    ,
    val isAsyncTask: Boolean = false
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

    val taskComparator = Comparator<Task> { task1, task2 -> Util.compareTask(task1, task2) }


    /**
     * 用于输出task 运行时日志
     */
    private var taskRuntimeListener: TaskRunTimeListener? = TaskRunTimeListener()


    /**
     * 用于运行时log 统计输出,输出当前 task 依赖了那些前置任务
     * 这些前置任务的名称 我们将它存储在这里
     */
    private val dependTasksName: MutableList<String> = ArrayList()


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
        taskRuntimeListener?.onStart(this)
    }

    override fun run() {
        // 改变任务的状态 onStart onRunning onFinshed -- 通知后置任务去开始执行
        TraceCompat.beginSection(id)

        toRunning()
        run(id)  // 真正的执行 初始化任务的代码的方法

        toFinish()

        //通知它的后置任务去执行
        notifyBehindTasks()

        recycle()

        TraceCompat.endSection()
    }

    private fun recycle() {

        dependTasks.clear()
        behindTasks.clear()
        taskListeners.clear()
        taskRuntimeListener = null

    }

    /**
     * 通知后置任务去尝试执行
     */
    private fun notifyBehindTasks() {
        if (behindTasks.isNotEmpty()) {
            if (behindTasks.size > 1) {
                Collections.sort(behindTasks, taskComparator)
            }

            /**
             * 遍历 behindTask 后置任务,通知他们 告诉他们你的一个前置依赖任务已经执行完成了
             */

            for (behindTask in behindTasks) {
                // A behindTask -> (B,C) A执行完成之后,B,C才可以执行
                behindTask.dependTaskFinished(this)
            }
        }
    }

    private fun dependTaskFinished(dependTask: Task) {

        /**
         *
         */
        if (dependTasks.isEmpty()) {
            return
        }
        // 把A 从B,C的前置 依赖任务 集合中移除
        dependTasks.remove(dependTask)


        // B,C 的所有前置任务 是否都执行完了
        if (dependTasks.isEmpty()) {
            start()
        }
    }

    /**
     * 给当前task 添加一个前置的 依赖任务
     */
    open fun dependOn(dependTask: Task) {
        var task = dependTask
        if (task != this) {
            if (dependTask is Project) {
                task = dependTask.endTask
            }
            dependTasks.add(task)
            dependTasksName.add(task.id)

            // 当前task 依赖了dependTask ,那么我们还需要把dependTask- 里面的behindTask,
            // 添加进当前task

            if (!task.behindTasks.contains(this)) {
                task.behindTasks.add(this)
            }
        }
    }

    /**
     * 给当前task 移除一个前置依赖任务
     */
    open fun removeDependence(dependTask: Task) {
        var task = dependTask
        if (dependTask != this) {
            if (dependTask is Project) {
                task = dependTask.endTask
            }

            dependTasks.remove(task)
            dependTasksName.remove(task.id)
            /**
             * 把当前task 从 dependTask 的后置任务集合 behindTasks 中移除
             * 达到解除 两个任务依赖关系的目的
             */
            if (task.dependTasks.contains(this)) {
                task.behindTasks.remove(this)
            }
        }
    }

    /**
     * 给当前任务添加后置 依赖项
     * 他喝dependon 是相反的
     */
    open fun behind(behindTask: Task) {
        var task = behindTask
        if (behindTask != this) {
            if (behindTask is Project) {
                task = behindTask.startTask
            }

            // 把这个 behindTask 添加到当前task 的后面
            behindTasks.add(task)


            // 把当前task 添加到behindTask 的前面
            task.dependOn(this)
        }

    }

    /**
     * 给当前task 移除一个 后置的任务
     */
    open fun removeBehind(behindTask: Task) {
        var task = behindTask
        if (behindTask != this) {
            if (behindTask is Project) {
                task = behindTask.startTask
            }
            behindTasks.remove(task)
            task.removeDependence(this)
        }
    }

    private fun toFinish() {
        state = TasKState.FINISHED
        for (listener in taskListeners) {
            listener.onStart(this)
        }
        taskRuntimeListener?.onFinished(this)

    }

    private fun toRunning() {
        state = TasKState.RUNNING
        for (listener in taskListeners) {
            listener.onStart(this)
        }
        taskRuntimeListener?.onRunning(this)
    }

    abstract fun run(id: String)

    override fun compareTo(other: Task): Int {
        return Util.compareTask(this, other)
    }
}