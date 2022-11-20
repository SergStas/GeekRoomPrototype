package com.example.geekroomprototype.util.extensions

import android.content.Context
import com.example.geekroomprototype.R

fun formatDate(context: Context, ts: Long): String {
    val seconds = java.lang.Long.max((System.currentTimeMillis() - ts) / 1000, 1)
    return when {
        seconds < 60 -> context.getString(R.string.listitem_article_date_ph_sec).format(seconds)
        seconds < 60 * 60 -> context.getString(R.string.listitem_article_date_ph_minutes).format(seconds / 60)
        seconds < 60 * 60 * 24 -> context.getString(R.string.listitem_article_date_ph_hours).format(seconds / 60 / 60)
        seconds < 60 * 60 * 24 * 7 -> context.getString(R.string.listitem_article_date_ph_days).format(seconds / 60 / 60 / 24)
        seconds < 60 * 60 * 24 * 7 * 30 -> context.getString(R.string.listitem_article_date_ph_weeks).format(seconds / 60 / 60 / 24 / 7)
        seconds < 60 * 60 * 24 * 7 * 30 * 12 -> context.getString(R.string.listitem_article_date_ph_months).format(seconds / 60 / 60 / 24 / 7 / 30)
        else -> context.getString(R.string.listitem_article_date_ph_years).format(seconds / 60 / 60 / 24 / 7 / 30 / 12)
    }
}