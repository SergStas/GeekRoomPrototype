package com.example.geekroomprototype.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.models.ArticleData
import com.example.geekroomprototype.R
import com.example.geekroomprototype.databinding.FragmentSearchBinding
import com.example.geekroomprototype.databinding.ListitemArticleBinding
import com.example.geekroomprototype.ui.abstractions.BaseFragment
import com.example.geekroomprototype.ui.feed.articles.ReadArticleFragmentArgs
import com.example.geekroomprototype.ui.feed.models.ArticlePreviewFreshListItem
import com.example.geekroomprototype.ui.feed.vh.FreshArticleViewHolder
import com.example.geekroomprototype.ui.search.vm.SearchViewModel
import com.example.geekroomprototype.util.extensions.viewModelFactory
import com.example.geekroomprototype.util.rv.BaseAdapter
import kotlinx.coroutines.launch

class SearchFragment: BaseFragment(R.layout.fragment_search) {
    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel by viewModels<SearchViewModel> { viewModelFactory }
    private lateinit var adapter: BaseAdapter<ArticlePreviewFreshListItem, FreshArticleViewHolder>
    private val query
        get() = binding.etSearch.text.toString()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        subscribe()
        setView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.search(query)
    }

    private fun setView() =
        binding.run {
            ivSearch.setOnClickListener { viewModel.search(query) }
        }

    private fun subscribe() =
        lifecycleScope.launch {
            viewModel.state.collect {
                when(it) {
                    SearchViewModel.State.Loading -> showLoading(true)
                    is SearchViewModel.State.OpenArticle -> openReadArticlePage(it.article)
                    is SearchViewModel.State.ResultsLoaded -> showResults(it.items)
                    SearchViewModel.State.Waiting -> showLoading(false)
                }
            }
        }

    private fun showResults(items: List<ArticlePreviewFreshListItem>) {
        adapter.submitList(items)
        binding.tvResults.text = "${items.size} results found"
    }

    private fun showLoading(value: Boolean) {
        binding.pbLoading.isVisible = value
    }

    private fun setAdapter() {
        adapter = BaseAdapter.create { FreshArticleViewHolder(
            ListitemArticleBinding.inflate(layoutInflater, it, false),
        ) }.apply { bindToRv(binding.rvResults) }
    }

    private fun openReadArticlePage(article: ArticleData) =
        findNavController().navigate(
            resId = R.id.action_searchFragment_to_readArticleFragment2,
            args = ReadArticleFragmentArgs(article).toBundle(),
        )
}