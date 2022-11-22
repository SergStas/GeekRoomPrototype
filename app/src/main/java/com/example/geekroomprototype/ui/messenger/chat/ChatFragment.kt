package com.example.geekroomprototype.ui.messenger.chat

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.geekroomprototype.R
import com.example.geekroomprototype.databinding.FragmentChatBinding
import com.example.geekroomprototype.databinding.ListitemChatMessageBinding
import com.example.geekroomprototype.ui.abstractions.BaseFragment
import com.example.geekroomprototype.ui.messenger.chat.models.ChatMessageItem
import com.example.geekroomprototype.ui.messenger.chat.vh.ChatMessageViewHolder
import com.example.geekroomprototype.util.extensions.viewModelFactory
import com.example.geekroomprototype.util.rv.BaseAdapter
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChatFragment: BaseFragment(R.layout.fragment_chat) {
    private val binding by viewBinding(FragmentChatBinding::bind)
    private val viewModel by viewModels<ChatViewModel> { viewModelFactory }
    private val args by navArgs<ChatFragmentArgs>()
    private lateinit var adapter: BaseAdapter<ChatMessageItem, ChatMessageViewHolder>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        subscribe()
        setView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadMessages(args.chat)
    }

    private fun setView() {
        binding.run {
            bSend.setOnClickListener {
                val content = etMessage.text.toString()
                if (content.isNotEmpty()) {
                    viewModel.createMessage(content)
                    etMessage.setText("")
                }
            }
        }
    }

    private fun subscribe() {
        viewModel.run {
            title.observe(viewLifecycleOwner) {
                binding.tvTitle.text = it
            }
            state.observe(viewLifecycleOwner) {
                when(it) {
                    is ChatViewModel.State.Loaded -> {
                        binding.pbLoading.isVisible = false
                        adapter.submitList(it.messages)
                        binding.pbLoading.clearFocus()
                        MainScope().launch {
                            delay(200)
                            binding.rvMessages.scrollToPosition(it.messages.size - 1)
                        }
                    }
                    ChatViewModel.State.Loading ->
                        binding.pbLoading.isVerticalFadingEdgeEnabled = true
                    ChatViewModel.State.Waiting -> {}
                }
            }
        }
    }

    private fun setAdapter() {
        adapter = BaseAdapter.create { ChatMessageViewHolder(
            ListitemChatMessageBinding.inflate(requireActivity().layoutInflater, it, false),
        ) }.apply {
            bindToRv(binding.rvMessages)
        }
    }
}