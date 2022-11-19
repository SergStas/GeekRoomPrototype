package com.example.geekroomprototype.util.extensions

import android.content.Context
import android.widget.Toast
import com.example.geekroomprototype.App
import com.example.geekroomprototype.R

val Context.appComponent
    get() = (applicationContext as App).appComponent

fun Context.toast(msg: String) =
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()

fun Context.toastInDevelopment() =
    toast(getString(R.string.toast_in_development))
