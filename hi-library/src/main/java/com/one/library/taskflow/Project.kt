package com.one.library.taskflow

/**
 * @author  diaokaibin@gmail.com on 2021/8/8.
 * 任务组 管理一组任务的依赖关系,先后执行顺序
 */
class Project private constructor(id: String) : Task(id) {

    /**
     * 任务组中 所有任务结束的节点
     */
    lateinit var endTask: Task


    /**
     * 任务组的开始节点
     */
    lateinit var startTask: Task


    override fun start() {
        startTask.start()

    }

    override fun run(id: String) {
        // 不需要处理
    }


    override fun behind(behindTask: Task) {
        // 当咱们给一个任务组 添加后置任务的时候,那么这个任务应该添加到 组当中谁的后面??

        /****
         *把新来的后置任务 添加到 任务组的结束节点上面去,这样的话
         * 任务组里面所有的任务都结束了
         * 这个后置任务才会执行
         */
        endTask.behind(behindTask)

    }

    /**
     * 添加前置依赖
     */
    override fun dependOn(dependTask: Task) {
        startTask.dependOn(dependTask)

    }

    override fun removeBehind(behindTask: Task) {
        endTask.removeBehind(behindTask)
    }

    override fun removeDependence(dependTask: Task) {
        startTask.removeDependence(dependTask)
    }


    class Builder(projectName: String, iTaskCreator: ITaskCreator) {

        private val taskFactory = TaskFactory(iTaskCreator)
        private val startTask: Task = CriticalTask(projectName + "_start")
        private val endTask: Task = CriticalTask(projectName + "_end")
        private val project: Project = Project(projectName)
        private var priority = 0  // 默认该任务组中 所有任务优先级最高的

        /**
         * 本次添加进来的这个task 是否把 start 节点当做依赖
         * 如果这个task 它存在于其他task 的依赖关系,那么就不能直接添加到 start 节点的后面
         * 而需要通过 dependon 来指定任务的依赖关系
         */
        private var currentTaskShouldDependOnStartTask = true

        private var currentAddTask: Task? = null


        fun add(id: String): Builder {
            val task = taskFactory.geTask(id)
            if (task.priority > priority) {
                priority = task.priority
            }

            return add(task)
        }

        fun add(task: Task): Builder {
            if (currentTaskShouldDependOnStartTask && currentAddTask != null) {
                startTask.behind(task)
            }
            currentAddTask = task
            currentTaskShouldDependOnStartTask = true
            currentAddTask!!.behind(endTask)
            return this
        }

        fun dependOn(task: Task): Builder {
            // 确定 刚才添加进来的 currentAddTask 和task 的依赖关系 - currentAddTask 依赖 task
            task.behind(currentAddTask!!)
            endTask.removeDependence(task)
            currentTaskShouldDependOnStartTask = false
            return this
        }

        fun build(): Project {
            if (currentAddTask == null) {
                startTask.behind(endTask)
            } else {
                if (currentTaskShouldDependOnStartTask) {
                    startTask.behind(currentAddTask!!)
                }
            }
            startTask.priority = priority
            endTask.priority = priority
            project.startTask = startTask
            project.endTask = endTask
            return project
        }
    }

    private class TaskFactory(private val iTaskCreator: ITaskCreator) {
        //利用itaskcreator 创建task 实例 , 并管理
        private val cacheTasks: MutableMap<String, Task> = HashMap()

        fun geTask(id: String): Task {
            var task = cacheTasks.get(id)
            if (task != null) {
                return task
            }

            task = iTaskCreator.createTask(id)
            requireNotNull(task) {

                "create task fail , make sure iTaskCreator can create a task with only taskid"
            }
            cacheTasks[id] = task
            return task

        }

    }


    private class CriticalTask internal constructor(id: String) : Task(id) {
        override fun run(id: String) {
            // noting to do
        }

    }

}