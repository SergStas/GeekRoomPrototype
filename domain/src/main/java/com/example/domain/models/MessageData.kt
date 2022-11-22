package com.example.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageData(
    val sender: UserData,
    val content: String,
    val sentTime: Long,
    val readUsers: List<UserData>,
): Parcelable
