package com.example.geekroomprototype.ui.feed

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.geekroomprototype.R
import com.example.geekroomprototype.databinding.FragmentFeedBinding
import com.example.geekroomprototype.ui.abstractions.BaseFragment

class FeedFragment: BaseFragment(R.layout.fragment_feed) {
    private val binding by viewBinding(FragmentFeedBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
    }

    private fun setView() {
        binding.run {
            bNewArticle.setOnClickListener {
                toNewArticlePage()
            }
        }
    }

    private fun toNewArticlePage() =
        findNavController().navigate(R.id.action_feedFragment_to_newArticleFragment)
}