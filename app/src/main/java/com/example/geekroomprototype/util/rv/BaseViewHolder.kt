package com.example.geekroomprototype.util.rv

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class BaseViewHolder<TItem: IRvItem>(
    itemView: View,
): ViewHolder(itemView) {
    protected val context: Context
        get() = itemView.context

    abstract fun bind(item: TItem)
}