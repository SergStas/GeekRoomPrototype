package com.example.geekroomprototype.util.rv

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class DefaultCallback<T: Any>: DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) =
        oldItem == newItem

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T) =
        oldItem == newItem
}