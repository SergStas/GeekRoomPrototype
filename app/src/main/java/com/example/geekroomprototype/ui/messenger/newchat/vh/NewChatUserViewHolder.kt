package com.example.geekroomprototype.ui.messenger.newchat.vh

import com.example.geekroomprototype.R
import com.example.geekroomprototype.databinding.ListitemNewChatUserBinding
import com.example.geekroomprototype.ui.messenger.newchat.models.NewChatUserItem
import com.example.geekroomprototype.util.extensions.colorFromId
import com.example.geekroomprototype.util.rv.BaseViewHolder

class NewChatUserViewHolder(
    private val binding: ListitemNewChatUserBinding,
): BaseViewHolder<NewChatUserItem>(binding.root) {
    override fun bind(item: NewChatUserItem) {
        binding.run {
            root.setBackgroundColor(
                context.colorFromId(if (item.isChecked) R.color.c4 else R.color.c2),
            )
            root.setOnClickListener { item.onTap() }
            tvName.text = item.username
        }
    }
}