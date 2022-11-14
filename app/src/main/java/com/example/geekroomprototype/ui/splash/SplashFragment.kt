package com.example.geekroomprototype.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.geekroomprototype.R
import com.example.geekroomprototype.databinding.FragmentSplashBinding
import com.example.geekroomprototype.ui.splash.vm.SplashViewModel
import com.example.geekroomprototype.util.extensions.toast
import com.example.geekroomprototype.util.extensions.viewModelFactory

class SplashFragment: Fragment(R.layout.fragment_splash) {
    private val binding by viewBinding(FragmentSplashBinding::bind)
    private val viewModel by activityViewModels<SplashViewModel> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        setView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadUser()
    }

    private fun setView() {
        binding.run {
            bLogin.setOnClickListener {
                toast("To login page")
            }
            bRegister.setOnClickListener {
                toast("To registration page")
            }
        }
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

    private fun toMainPage() =
        findNavController().navigate(R.id.action_splashFragment_to_nav_main)
}