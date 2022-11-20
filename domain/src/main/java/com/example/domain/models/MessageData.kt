package com.example.domain.models

data class MessageData(
    val sender: UserData,
    val content: String,
    val sentTime: Long,
    val readUsers: List<UserData>,
)
