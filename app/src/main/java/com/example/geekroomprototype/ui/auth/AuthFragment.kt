package com.example.geekroomprototype.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.geekroomprototype.R
import com.example.geekroomprototype.databinding.FragmentAuthBinding
import com.example.geekroomprototype.ui.auth.models.AuthMode
import com.example.geekroomprototype.ui.auth.vm.AuthViewModel
import com.example.geekroomprototype.util.extensions.toast
import com.example.geekroomprototype.util.extensions.viewModelFactory

class AuthFragment: Fragment(R.layout.fragment_auth) {
    private val binding by viewBinding(FragmentAuthBinding::bind)
    private val viewModel by activityViewModels<AuthViewModel> { viewModelFactory }
    private val args by navArgs<AuthFragmentArgs>()
    private val username
        get() = binding.tvUsername.text.toString()
    private val password
        get() = binding.tvPassword.text.toString()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        setView()
    }

    private fun subscribe() {
        viewModel.run {
            user.observe(viewLifecycleOwner) {
                if (it != null) {
                    toMainPage()
                }
            }
            errMsg.observe(viewLifecycleOwner) {
                if (it != null) {
                    toast(it)
                }
            }
        }
    }

    private fun setView() {
        binding.run {
            bConfirm.text = getString(when (args.data.mode) {
                AuthMode.Login -> R.string.b_login
                AuthMode.Register -> R.string.b_register
            } )
            bConfirm.setOnClickListener {
                when (args.data.mode) {
                    AuthMode.Login -> viewModel.login(username, password)
                    AuthMode.Register -> viewModel.register(username, password)
                }
            }
        }
    }

    private fun toMainPage() =
        findNavController().navigate(R.id.action_authFragment_to_nav_main)
}