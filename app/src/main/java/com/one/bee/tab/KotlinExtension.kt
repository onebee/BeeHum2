package com.one.bee.tab

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes

/**
 * @author  diaokaibin@gmail.com on 2021/7/23.
 */
fun String.lastChar(): Char = this[this.length - 1]

fun MutableList<Int>.swap (index1:Int,index2:Int) {
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}

fun main() {

//    val list = mutableListOf(1, 2, 3)
//    list.swap(0,2)
//    println(list)

    val msg = "heeelll55"

    println(msg.lastChar)
}
val String.lastChar:Char get() = this.get(this.length-1)

fun  <T: View> Activity.find(@IdRes id: Int):T {
    return findViewById(id)
}

fun Int.onClick(activity: Activity,click :()->Unit){
    activity.find<View>(this).apply {
        setOnClickListener {
            click()
        }
    }
}

