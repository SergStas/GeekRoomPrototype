package com.example.geekroomprototype.ui.messenger

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.geekroomprototype.R
import com.example.geekroomprototype.databinding.FragmentMessengerBinding
import com.example.geekroomprototype.databinding.ListitemChatPreviewBarBinding
import com.example.geekroomprototype.ui.abstractions.BaseFragment
import com.example.geekroomprototype.ui.messenger.models.MessengerChatBarItem
import com.example.geekroomprototype.ui.messenger.viewholders.MessengerChatBarViewHolder
import com.example.geekroomprototype.util.extensions.toastInDevelopment
import com.example.geekroomprototype.util.extensions.viewModelFactory
import com.example.geekroomprototype.util.rv.BaseAdapter

class MessengerFragment: BaseFragment(R.layout.fragment_messenger) {
    private val binding by viewBinding(FragmentMessengerBinding::bind)
    private val viewModel by viewModels<MessengerViewModel> { viewModelFactory }
    private lateinit var adapter: BaseAdapter<MessengerChatBarItem, MessengerChatBarViewHolder>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        subscribe()
        setView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadChats()
    }

    private fun setView() {
        binding.bNew.setOnClickListener {
            toastInDevelopment()
        }
    }

    private fun subscribe() =
        viewModel.run {
            chats.observe(viewLifecycleOwner) {
                when(it) {
                    is MessengerViewModel.State.Loaded -> {
                        binding.pbLoading.isVisible = false
                        adapter.submitList(it.chats)
                        binding.tvEmpty.isVisible = it.chats.isEmpty()
                    }
                    MessengerViewModel.State.Loading -> {
                        binding.pbLoading.isVisible = true
                        adapter.submitList(emptyList())
                    }
                }
            }
        }

    private fun setAdapter() {
        adapter = BaseAdapter.create { MessengerChatBarViewHolder(
            ListitemChatPreviewBarBinding.inflate(requireActivity().layoutInflater, it, false),
        ) }.apply {
            bindToRv(binding.rvChats)
        }
    }
}