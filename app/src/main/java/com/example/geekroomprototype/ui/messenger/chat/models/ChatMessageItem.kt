package com.example.geekroomprototype.ui.messenger.chat.models

import com.example.geekroomprototype.util.rv.IRvItem

data class ChatMessageItem(
    val message: String,
    val isSelf: Boolean,
    val senderAvatarUrl: String,
): IRvItem
