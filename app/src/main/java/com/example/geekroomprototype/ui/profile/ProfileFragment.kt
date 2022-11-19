package com.example.geekroomprototype.ui.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.models.UserData
import com.example.geekroomprototype.R
import com.example.geekroomprototype.databinding.FragmentProfileBinding
import com.example.geekroomprototype.ui.MainActivity
import com.example.geekroomprototype.ui.abstractions.BaseFragment
import com.example.geekroomprototype.util.extensions.toastInDevelopment
import com.example.geekroomprototype.util.extensions.viewModelFactory

class ProfileFragment: BaseFragment(R.layout.fragment_profile) {
    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel by viewModels<ProfileViewModel> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        setView()
    }

    private fun setView() {
        binding.run {
            cardAvatar.setOnClickListener {
                toastInDevelopment()
            }
            bLogout.setOnClickListener {
                viewModel.logout()
            }
        }
    }

    private fun subscribe() {
        viewModel.run {
            userData.observe(viewLifecycleOwner) {
                when (it) {
                    ProfileViewModel.State.Loading -> displayLoading()
                    is ProfileViewModel.State.LoggedIn -> displayResults(it.userData)
                    ProfileViewModel.State.LoggedOut -> exitOnLogOut()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadUser()
    }

    private fun displayLoading() {
        binding.run {
            tvUsername.text = getString(R.string.tv_content_is_loading)
            pbLoading.isVisible = true
        }
    }

    private fun displayResults(userData: UserData) {
        binding.run {
            tvUsername.text = userData.username
            pbLoading.isVisible = false
        }
    }

    private fun exitOnLogOut() {
        requireContext().run {
            startActivity(MainActivity.refreshActivityIntent(this))
        }
    }
}