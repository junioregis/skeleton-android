package com.domain.skeleton.core.ext

import java.util.*

fun ByteArray.toHexFormatted(separator: String = ":"): String {
    val chars = toHex()

    return (0 until chars.size step 2)
            .joinToString(separator) { "${chars[it]}${chars[it + 1]}" }
}

fun ByteArray.toHex(): CharArray {
    val hexArray = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
    val hexChars = CharArray(size * 2)
    var v: Int

    for (j in indices) {
        v = this[j].toInt() and 0xFF

        hexChars[j * 2] = hexArray[v.ushr(4)]
        hexChars[j * 2 + 1] = hexArray[v and 0x0F]
    }

    return hexChars
}

fun ByteArray.chunked(chunkSize: Int): Array<ByteArray> {
    val result = ArrayList<ByteArray>()
    var start = 0

    while (start < this.size) {
        val end = Math.min(this.size, start + chunkSize)
        result.add(Arrays.copyOfRange(this, start, end))
        start += chunkSize
    }

    return result.toTypedArray()
}