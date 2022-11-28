package com.example.geekroomprototype.ui.feed.articles

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.models.ArticleData
import com.example.domain.models.UserData
import com.example.geekroomprototype.R
import com.example.geekroomprototype.databinding.FragmentReadArticleBinding
import com.example.geekroomprototype.ui.abstractions.BaseFragment
import com.example.geekroomprototype.util.extensions.drawableFromId
import com.example.geekroomprototype.util.extensions.formatDate
import com.example.geekroomprototype.util.extensions.viewModelFactory

class ReadArticleFragment: BaseFragment(R.layout.fragment_read_article) {
    private val binding by viewBinding(FragmentReadArticleBinding::bind)
    private val viewModel by viewModels<ReadArticleViewModel> { viewModelFactory }
    private val args by navArgs<ReadArticleFragmentArgs>()
    private val isLiked
        get() = viewModel.user?.username?.let {
            it in (viewModel.article.value?.likedUsers?.map(UserData::username) ?: emptyList())
        } ?: false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        viewModel.loadArticle(args.article)
    }

    private fun subscribe() {
        viewModel.run {
            article.observe(viewLifecycleOwner) {
                if (it != null) displayArticle(it)
            }
        }
    }

    private fun displayArticle(article: ArticleData) {
        binding.run {
            tvAuthorName.text = article.author.username
            tvAuthorTag.text = getString(R.string.author_tag_ph).format(article.author.username)
            tvContent.text = article.content
            tvTime.text = formatDate(requireContext(), article.creationDate)
            tvTitle.text = article.title
            tvLikes.text = article.likedUsers.size.toString()
            ivLike.setImageDrawable(requireContext().drawableFromId(
                if (isLiked) R.drawable.ic_like_on else R.drawable.ic_like_off,
            ))
            ivLike.setOnClickListener { viewModel.switchLike() }
        }
    }
}