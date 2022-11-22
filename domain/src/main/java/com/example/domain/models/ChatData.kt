package com.example.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatData(
    val title: String,
    val avatarUrl: String,
    val participants: List<UserData>,
    val messages: List<MessageData>,
): Parcelable
