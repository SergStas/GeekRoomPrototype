package com.example.geekroomprototype.util.extensions

import androidx.fragment.app.Fragment

val Fragment.appComponent
    get() = requireContext().appComponent

val Fragment.viewModelFactory
    get() = appComponent.getViewModelFactory()

fun Fragment.toast(msg: String) =
    requireContext().toast(msg)

fun Fragment.toastInDevelopment() =
    requireContext().toastInDevelopment()