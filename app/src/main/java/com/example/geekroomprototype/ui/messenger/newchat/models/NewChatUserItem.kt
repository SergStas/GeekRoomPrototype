package com.example.geekroomprototype.ui.messenger.newchat.models

import com.example.geekroomprototype.util.rv.IRvItem

data class NewChatUserItem(
    val username: String,
    val avatarUrl: String,
    val isChecked: Boolean,
    private val _onTap: (NewChatUserItem) -> Unit,
): IRvItem {
    fun onTap() = _onTap(this)
}
