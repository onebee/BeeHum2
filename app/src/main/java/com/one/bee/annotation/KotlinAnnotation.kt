package com.one.bee.annotation

/**
 * @author  diaokaibin@gmail.com on 2021/7/22.
 */

fun main() {

    fire(ApiGetArticles())
}

enum class Method {
    GET,
    POST
}

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class HttpMethod(val method: Method)

interface Api {
    val name: String
    val version: String
        get() = "1.0"
}

@HttpMethod(Method.GET)
class ApiGetArticles : Api {
    override val name: String
        get() = "/api.articles"
}

fun fire(api: Api) {
    val annotation = api.javaClass.annotations
    val method = annotation.find { it is HttpMethod } as? HttpMethod

    println("该接口的注解类型 请求 ${method}")

}

