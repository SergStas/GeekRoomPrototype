package com.example.geekroomprototype.ui.messenger.models

import com.example.geekroomprototype.util.rv.IRvItem

data class MessengerChatBarItem(
    val title: String,
    val avatarUrl: String,
    val lastMessageContent: String,
    val unreadCount: Int,
    val lastMessageDateToken: String,
    val onOpen: (MessengerChatBarItem) -> Unit,
): IRvItem