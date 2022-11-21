package com.example.geekroomprototype.ui.messenger.newchat

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.geekroomprototype.R
import com.example.geekroomprototype.databinding.FragmentNewChatBinding
import com.example.geekroomprototype.databinding.ListitemNewChatUserBinding
import com.example.geekroomprototype.ui.abstractions.BaseFragment
import com.example.geekroomprototype.ui.messenger.newchat.models.NewChatUserItem
import com.example.geekroomprototype.ui.messenger.newchat.vh.NewChatUserViewHolder
import com.example.geekroomprototype.util.extensions.toast
import com.example.geekroomprototype.util.extensions.viewModelFactory
import com.example.geekroomprototype.util.rv.BaseAdapter

class NewChatFragment: BaseFragment(R.layout.fragment_new_chat) {
    private val binding by viewBinding(FragmentNewChatBinding::bind)
    private val viewModel by viewModels<NewChatViewModel> { viewModelFactory }
    private lateinit var adapter: BaseAdapter<NewChatUserItem, NewChatUserViewHolder>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        subscribe()
        setView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.reloadUsers()
    }

    private fun setView() {
        binding.run {
            bCreate.setOnClickListener { viewModel.finish() }
        }
    }

    private fun subscribe() {
        viewModel.run {
            errMsg.observe(viewLifecycleOwner) {
                toast(it ?: return@observe)
            }
            modifiedIndex.observe(viewLifecycleOwner) {
                try { adapter.notifyItemChanged(it ?: return@observe) } catch (_: Exception) {}
            }
            chatName.observe(viewLifecycleOwner) {
                binding.tvTitle.text = it
            }
            state.observe(viewLifecycleOwner) {
                binding.pbLoading.isVisible = it is NewChatViewModel.State.LoadingUsers
                when(it) {
                    NewChatViewModel.State.Created -> onSuccess()
                    is NewChatViewModel.State.EditingParticipants -> submitUsers(it.users)
                    NewChatViewModel.State.LoadingUsers -> {}
                    NewChatViewModel.State.Waiting -> {}
                }
            }
        }
    }

    private fun submitUsers(users: List<NewChatUserItem>) {
        binding.bCreate.isEnabled = users.isNotEmpty()
        adapter.submitList(users.toList())
    }

    private fun onSuccess() =
        findNavController().popBackStack()

    private fun setAdapter() {
        adapter = BaseAdapter.create { NewChatUserViewHolder(
            ListitemNewChatUserBinding.inflate(requireActivity().layoutInflater, it, false),
        ) }.apply { bindToRv(binding.rvUsers) }
    }
}