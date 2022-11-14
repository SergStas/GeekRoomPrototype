package com.example.geekroomprototype.ui.auth.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthArgsData(
    val mode: AuthMode,
): Parcelable
