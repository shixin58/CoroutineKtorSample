package com.bride.kotlin.coroutines.server

import com.bride.kotlin.coroutines.server.upload.upload
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.content.*
import io.ktor.routing.*
import io.ktor.serialization.*

fun Application.main() {
    install(DefaultHeaders)// 所有请求默认headers
    install(CallLogging)// 打日志
    install(ContentNegotiation) { serialization() }// 序列化
    install(Routing)// 路由

    // dsl函数
    routing {// 定义具体路由
        // 提供文件上传下载服务
        upload()// Routing继承Route
        static("static") {// 创建static类型的路由
            files("static")
        }
    }
}