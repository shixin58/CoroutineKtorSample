package com.bride.kotlin.coroutines.server.upload

import com.bride.kotlin.coroutines.server.Context
import com.bride.kotlin.coroutines.server.utils.md5
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.io.File

/**
 * 上传文件返回Response, 包含文件url和md5。md5用于校验是否上传成功。
 */
@Serializable
data class UploadResponse(val url: String, val md5: String)

val uploadDir = File("static").also { it.mkdirs() }

fun Route.upload() {
    post("upload") {
        val multipart = call.receiveMultipart()
        val uploadedFiles = mutableListOf<UploadResponse>()

        multipart.forEachPart { part ->
            when(part) {
                is PartData.FileItem -> {
                    val file = File(uploadDir, "upload-${System.currentTimeMillis()}-${part.originalFileName}")
                    // InputStream读取
                    part.streamProvider().use { input ->
                        file.outputStream().buffered().use { output ->
                            input.copyTo(output)
                        }
                    }
                    uploadedFiles += UploadResponse(Context.urlOf(file), file.md5())
                }
                is PartData.FormItem -> {}
                is PartData.BinaryItem -> {}
                is PartData.BinaryChannelItem -> {}
            }
            part.dispose()
        }

        /*val message = uploadedFiles.joinToString(separator = ",") {
            Json.encodeToString(it)
        }.let { "[$it]" }*/
        call.respond(uploadedFiles)
    }
}