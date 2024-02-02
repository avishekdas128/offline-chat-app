package com.orangeink.offlinechatapp.core.util

import android.content.Context
import android.graphics.Rect
import android.net.Uri
import android.text.format.DateUtils
import android.view.View
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun Context.getMediaType(uri: Uri): String {
    return contentResolver.getType(uri) ?: ""
}

fun String.formatCreatedAt(): String {
    return try {
        val existingFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        existingFormat.timeZone = TimeZone.getTimeZone("UTC")
        val parsedDate = existingFormat.parse(this)
        val newFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        newFormat.timeZone = TimeZone.getDefault()
        newFormat.format(parsedDate!!).uppercase()
    } catch (e: Exception) {
        ""
    }
}

fun String.formatChatDate(): String {
    return try {
        val existingFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        existingFormat.timeZone = TimeZone.getTimeZone("UTC")
        val parsedDate = existingFormat.parse(this)

        if (DateUtils.isToday(parsedDate!!.time)) {
            "Today"
        } else if (DateUtils.isToday(parsedDate.time + DateUtils.DAY_IN_MILLIS)) {
            "Yesterday"
        } else {
            val newFormat = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
            newFormat.timeZone = TimeZone.getDefault()
            newFormat.format(parsedDate)
        }
    } catch (e: Exception) {
        ""
    }
}

fun String.parseForSort(): Long {
    return try {
        val existingFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        existingFormat.timeZone = TimeZone.getTimeZone("UTC")
        val parsedDate = existingFormat.parse(this)
        parsedDate!!.time
    } catch (e: Exception) {
        0
    }
}

fun View.getKeyboardHeight(): Boolean {
    val rect = Rect()
    getWindowVisibleDisplayFrame(rect)
    val screenHeight = rootView.height
    val absoluteKeyboardHeight = screenHeight - rect.bottom
    return if (rect.bottom > 0) absoluteKeyboardHeight > screenHeight * .15 else false
}