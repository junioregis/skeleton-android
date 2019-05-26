package com.domain.skeleton.core.ext

import java.io.ByteArrayOutputStream

fun Array<ByteArray>.join(): ByteArray {
    val outputStream = ByteArrayOutputStream()

    forEach {
        outputStream.write(it)
    }

    outputStream.close()

    return outputStream.toByteArray()
}