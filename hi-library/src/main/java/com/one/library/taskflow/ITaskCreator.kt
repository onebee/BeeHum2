package com.one.library.taskflow

/**
 * @author  diaokaibin@gmail.com on 2021/8/8.
 */
interface ITaskCreator {
    fun createTask(taskName: String): Task

}