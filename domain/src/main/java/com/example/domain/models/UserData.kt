package com.example.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    val username: String,
    val avatarUrl: String,
): Parcelable
