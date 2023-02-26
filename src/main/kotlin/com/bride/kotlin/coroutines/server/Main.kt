package com.bride.kotlin.coroutines.server

import com.bride.kotlin.coroutines.server.upload.upload
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8081) {
        install(DefaultHeaders)// 所有请求默认headers
        install(CallLogging)// 打日志
        install(ContentNegotiation) { json() }// 序列化

        // dsl函数
        routing {// 定义具体路由
            // 提供文件上传下载服务
            upload()// Routing继承Route
            static("static") {// 创建static类型的路由
                files("static")
            }
        }
    }.start(wait = true)
}