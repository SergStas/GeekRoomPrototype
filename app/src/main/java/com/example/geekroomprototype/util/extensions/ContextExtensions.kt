package com.example.geekroomprototype.util.extensions

import android.content.Context
import android.widget.Toast
import com.example.geekroomprototype.App

val Context.appComponent
    get() = (applicationContext as App).appComponent

fun Context.toast(msg: String) =
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
