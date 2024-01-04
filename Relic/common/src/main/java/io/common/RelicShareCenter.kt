package io.common

import android.content.Context
import android.content.Intent
import io.common.RelicConstants.URL.DEFAULT_PLACEHOLDER_URL
import io.common.util.LogUtil

/**
 * [Send simple data to other apps](https://developer.android.google.cn/training/sharing/send)
 * */
object RelicShareCenter {

    private const val TAG = "RelicShareCenter"
    private const val SHARE_TITLE = "Share to..."
    private const val SHARE_TYPE_PLAIN_TEXT = "text/plain"
    private const val SHARE_TYPE_RTF_TEXT = "text/rtf"
    private const val SHARE_TYPE_HTML = "text/html"

    fun shareTextOnly(
        context: Context,
        shareContent: String?
    ) {
        LogUtil.d(TAG, "[Share - Text only] content: $shareContent")
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = SHARE_TYPE_PLAIN_TEXT
            putExtra(Intent.EXTRA_TEXT, shareContent ?: DEFAULT_PLACEHOLDER_URL)
        }
        val chooserIntent = Intent.createChooser(intent, SHARE_TITLE)
        context.startActivity(chooserIntent)
    }

    fun shareTextRTF(
        context: Context,
        shareContent: String?
    ) {
        LogUtil.d(TAG, "[Share - RTF] content: $shareContent")
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = SHARE_TYPE_RTF_TEXT
            putExtra(Intent.EXTRA_TEXT, shareContent ?: DEFAULT_PLACEHOLDER_URL)
        }
        val chooserIntent = Intent.createChooser(intent, SHARE_TITLE)
        context.startActivity(chooserIntent)
    }

    fun shareWebLink(
        context: Context,
        title: String?,
        url: String?
    ) {
        LogUtil.d(TAG, "[Share - Web Link] content: $url")
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = SHARE_TYPE_HTML
            putExtra(Intent.EXTRA_TITLE, title ?: SHARE_TITLE)
            putExtra(Intent.EXTRA_TEXT, url ?: DEFAULT_PLACEHOLDER_URL)
        }
        val chooserIntent = Intent.createChooser(intent, title)
        context.startActivity(chooserIntent)
    }
}