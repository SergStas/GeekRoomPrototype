package com.example.geekroomprototype.ui.messenger.chat.vh

import android.view.View
import androidx.core.view.isVisible
import com.example.geekroomprototype.R
import com.example.geekroomprototype.databinding.ListitemChatMessageBinding
import com.example.geekroomprototype.ui.messenger.chat.models.ChatMessageItem
import com.example.geekroomprototype.util.extensions.drawableFromId
import com.example.geekroomprototype.util.rv.BaseViewHolder

class ChatMessageViewHolder(
    private val binding: ListitemChatMessageBinding,
): BaseViewHolder<ChatMessageItem>(binding.root) {
    override fun bind(item: ChatMessageItem) {
        binding.run {
            tvMessage.text = item.message
            cardAvatarLeft.isVisible = !item.isSelf
            cardAvatarRight.isVisible = item.isSelf
            tvUsernameLeft.visibility = if (item.senderName != null) View.VISIBLE else View.INVISIBLE
            tvUsernameRight.visibility = if (item.senderName != null) View.VISIBLE else View.INVISIBLE
            tvUsernameLeft.text = if (!item.isSelf) item.senderName ?: "" else ""
            tvUsernameRight.text = if (item.isSelf) item.senderName ?: "" else ""
            ivMarkerRead.isVisible = item.isRead != null
            ivMarkerRead.setImageDrawable(context.drawableFromId(
                if (item.isRead == true) R.drawable.ic_message_mark_read else R.drawable.ic_message_mark_unread,
            ))
        }
    }
}