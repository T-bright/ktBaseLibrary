package com.tbright.ktbaselibrary.utils

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * 将请求参数转成RequestBody, 将file转成Part
 */
//text type
fun getTextType(content: String): RequestBody {
    val textType = MediaType.parse("text/plain")
    return RequestBody.create(textType, content)
}

//json type
fun getJsonType(jsonContent: String): RequestBody {
    val jsonType = MediaType.parse("application/json; charset=utf-8")
    return RequestBody.create(jsonType, jsonContent)
}

//form type
fun getFormType(formContent: String): RequestBody {
    val formType = MediaType.parse("multipart/form-data")
    return RequestBody.create(formType,formContent)
}

//file
fun getFilePart(file: File): MultipartBody.Part {
    return getFilePart("file", file)
}

//file
fun getFilePart(name: String, file: File): MultipartBody.Part {
    val fileType = MediaType.parse("multipart/form-data")
    val body = RequestBody.create(fileType, file)
    return MultipartBody.Part.createFormData(name, file.name, body)
}

fun getFileListPart(key: String, fileList: List<File>): Array<MultipartBody.Part?> {
    val part = arrayOfNulls<MultipartBody.Part>(fileList.size)
    for (i in part.indices) {
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileList[i])
        part[i] = MultipartBody.Part.createFormData(key, fileList[i].name, requestFile)
    }
    return part
}

