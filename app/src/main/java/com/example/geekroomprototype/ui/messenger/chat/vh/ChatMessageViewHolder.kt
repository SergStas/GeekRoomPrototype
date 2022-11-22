package com.example.geekroomprototype.ui.messenger.chat.vh

import androidx.core.view.isVisible
import com.example.geekroomprototype.databinding.ListitemChatMessageBinding
import com.example.geekroomprototype.ui.messenger.chat.models.ChatMessageItem
import com.example.geekroomprototype.util.rv.BaseViewHolder

class ChatMessageViewHolder(
    private val binding: ListitemChatMessageBinding,
): BaseViewHolder<ChatMessageItem>(binding.root) {
    override fun bind(item: ChatMessageItem) {
        binding.run {
            tvMessage.text = item.message
            cardAvatarLeft.isVisible = !item.isSelf
            cardAvatarRight.isVisible = item.isSelf
        }
    }
}