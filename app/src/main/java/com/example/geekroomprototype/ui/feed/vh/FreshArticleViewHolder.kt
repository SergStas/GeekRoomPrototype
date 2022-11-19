package com.example.geekroomprototype.ui.feed.vh

import com.example.geekroomprototype.R
import com.example.geekroomprototype.databinding.ListitemArticleBinding
import com.example.geekroomprototype.ui.feed.models.FreshArticleRvItem
import com.example.geekroomprototype.util.rv.BaseViewHolder

class FreshArticleViewHolder(
    private val binding: ListitemArticleBinding,
): BaseViewHolder<FreshArticleRvItem>(binding.root) {
    override fun bind(item: FreshArticleRvItem) {
        binding.run {
            tvTime.text = item.creationDateToken
            tvAuthorName.text = item.authorName
            tvTitle.text = item.title
            tvContent.text = item.content
            tvLikes.text = context.getString(R.string.listitem_article_likes_ph).format(item.likesCount.toString())
            tvAuthorTag.text = item.authorTag
            root.setOnClickListener { item.onOpen(item) }
        }
    }
}