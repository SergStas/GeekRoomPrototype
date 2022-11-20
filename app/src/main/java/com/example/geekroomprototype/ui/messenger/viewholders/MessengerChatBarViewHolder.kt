package com.example.geekroomprototype.ui.messenger.viewholders

import androidx.core.view.isVisible
import com.example.geekroomprototype.databinding.ListitemChatPreviewBarBinding
import com.example.geekroomprototype.ui.messenger.models.MessengerChatBarItem
import com.example.geekroomprototype.util.rv.BaseViewHolder

class MessengerChatBarViewHolder(
    private val binding: ListitemChatPreviewBarBinding,
): BaseViewHolder<MessengerChatBarItem>(binding.root) {
    override fun bind(item: MessengerChatBarItem) {
        binding.run {
            root.setOnClickListener { item.onOpen(item) }
            tvContent.text = item.lastMessageContent
            tvDate.text = item.lastMessageDateToken
            tvUsername.text = item.title
            tvNotificator.text = item.unreadCount.toString()
            tvNotificator.isVisible = item.unreadCount > 0
        }
    }
}