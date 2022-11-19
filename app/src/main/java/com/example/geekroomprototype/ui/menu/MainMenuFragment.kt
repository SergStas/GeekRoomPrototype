package com.example.geekroomprototype.ui.menu

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.geekroomprototype.R
import com.example.geekroomprototype.databinding.FragmentMainMenuBinding
import com.example.geekroomprototype.ui.abstractions.BaseFragment

class MainMenuFragment: BaseFragment(R.layout.fragment_main_menu) {
    private val binding by viewBinding(FragmentMainMenuBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavBar()
    }

    private fun setupNavBar() {
        val navHost = childFragmentManager.findFragmentById(R.id.main_menu_host) as NavHostFragment
        binding.bnv.setupWithNavController(navHost.navController)
    }
}