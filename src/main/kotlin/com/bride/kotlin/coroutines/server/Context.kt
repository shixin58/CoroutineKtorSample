package com.bride.kotlin.coroutines.server

import java.io.File

object Context {
    val host: String = "http://localhost:8081"

    val workDir: File = File(".").absoluteFile

    fun urlOf(file: File): String {
        return "${host}/${file.absoluteFile.relativeTo(workDir).path}"
    }
}