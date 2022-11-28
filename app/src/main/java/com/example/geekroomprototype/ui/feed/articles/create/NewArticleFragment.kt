package com.example.geekroomprototype.ui.feed.articles.create

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.geekroomprototype.R
import com.example.geekroomprototype.databinding.FragmentNewArticleBinding
import com.example.geekroomprototype.ui.abstractions.BaseFragment
import com.example.geekroomprototype.util.extensions.toast
import com.example.geekroomprototype.util.extensions.viewModelFactory

class NewArticleFragment: BaseFragment(R.layout.fragment_new_article) {
    private val binding by viewBinding(FragmentNewArticleBinding::bind)
    private val viewModel by viewModels<NewArticleViewModel> { viewModelFactory }

    private val title
        get() = binding.etTitle.text.toString()

    private val content
        get() = binding.etContent.text.toString()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        setView()
    }

    private fun setView() {
        binding.run {
            bPublish.setOnClickListener {
                viewModel.create(title, content)
            }
        }
    }

    private fun subscribe() {
        viewModel.run {
            creationState.observe(viewLifecycleOwner) {
                when (it) {
                    is NewArticleViewModel.CreationState.Error -> displayError(it.message)
                    NewArticleViewModel.CreationState.Success -> exitOnCompletion()
                    NewArticleViewModel.CreationState.Waiting -> {}
                }
            }
        }
    }

    private fun exitOnCompletion() {
        toast("Created successfully")
        findNavController().popBackStack()
    }

    private fun displayError(message: String) = toast(message)
}