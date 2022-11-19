package com.example.geekroomprototype.ui.feed

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.geekroomprototype.R
import com.example.geekroomprototype.databinding.FragmentFeedBinding
import com.example.geekroomprototype.databinding.ListitemArticleBinding
import com.example.geekroomprototype.ui.abstractions.BaseFragment
import com.example.geekroomprototype.ui.feed.models.FreshArticleRvItem
import com.example.geekroomprototype.ui.feed.vh.FreshArticleViewHolder
import com.example.geekroomprototype.util.extensions.viewModelFactory
import com.example.geekroomprototype.util.rv.BaseAdapter

class FeedFragment: BaseFragment(R.layout.fragment_feed) {
    private val binding by viewBinding(FragmentFeedBinding::bind)
    private val viewModel by viewModels<FeedViewModel> { viewModelFactory }
    private lateinit var freshAdapter: BaseAdapter<FreshArticleRvItem, FreshArticleViewHolder>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapters()
        subscribe()
        setView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadDigest()
    }

    private fun setAdapters() {
        freshAdapter = BaseAdapter.create {
            FreshArticleViewHolder(
                ListitemArticleBinding.inflate(requireActivity().layoutInflater, it, false),
            )
        }.apply {
            bindToRv(
                recyclerView = binding.rvFresh,
                layoutManager = LinearLayoutManager(
                    requireContext(), LinearLayoutManager.HORIZONTAL, false,
                ),
            )
        }
    }

    private fun subscribe() {
        viewModel.run {
            freshArticles.observe(viewLifecycleOwner) {
                when (it) {
                    is FeedViewModel.ArticlesLoadingState.Fetched -> {
                        freshAdapter.submitList(it.content)
                        binding.pbFresh.isVisible = false
                    }
                    FeedViewModel.ArticlesLoadingState.Loading -> {
                        freshAdapter.submitList(emptyList())
                        binding.pbFresh.isVisible = true
                    }
                }
            }
        }
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