package com.example.geekroomprototype.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.geekroomprototype.R
import com.example.geekroomprototype.databinding.FragmentSplashBinding
import com.example.geekroomprototype.ui.auth.models.AuthArgsData
import com.example.geekroomprototype.ui.auth.models.AuthMode
import com.example.geekroomprototype.ui.auth.vm.AuthViewModel
import com.example.geekroomprototype.util.extensions.toast
import com.example.geekroomprototype.util.extensions.viewModelFactory

class SplashFragment: Fragment(R.layout.fragment_splash) {
    private val binding by viewBinding(FragmentSplashBinding::bind)
    private val viewModel by activityViewModels<AuthViewModel> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        setView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadUser()
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
            bLogin.setOnClickListener {
                toAuthPage(AuthMode.Login)
            }
            bRegister.setOnClickListener {
                toAuthPage(AuthMode.Register)
            }
        }
    }

    private fun toAuthPage(mode: AuthMode) =
        findNavController().navigate(
            resId = R.id.action_splashFragment_to_authFragment,
            args = AuthFragmentArgs(AuthArgsData(mode)).toBundle(),
        )

    private fun toMainPage() =
        findNavController().navigate(R.id.action_splashFragment_to_nav_main)
}