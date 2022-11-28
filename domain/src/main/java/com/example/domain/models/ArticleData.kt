package com.example.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleData(
    val title: String,
    val imageUrl: String,
    val content: String,
    val author: UserData,
    val likedUsers: List<UserData>,
    val creationDate: Long,
    val commentsCount: Int = 0,
    val sharedCount: Int = 0,
): Parcelable
