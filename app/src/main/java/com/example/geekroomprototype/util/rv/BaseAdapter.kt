package com.example.geekroomprototype.util.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager

abstract class BaseAdapter<TItem: IRvItem, TVH: BaseViewHolder<TItem>>(
    private val viewHolderCreator: (ViewGroup) -> TVH,
): ListAdapter<TItem, TVH>(DefaultCallback<TItem>()) {
    companion object {
        fun <TItem: IRvItem, TVH: BaseViewHolder<TItem>>create(
            viewHolderCreator: (parent: ViewGroup) -> TVH
        ) = object: BaseAdapter<TItem, TVH>(viewHolderCreator) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        viewHolderCreator(parent)

    override fun onBindViewHolder(holder: TVH, position: Int) =
        holder.bind(getItem(position))

    fun bindToRv(recyclerView: RecyclerView, layoutManager: LayoutManager? = null) {
        recyclerView.adapter = this
        recyclerView.layoutManager = layoutManager ?: LinearLayoutManager(
            recyclerView.context, LinearLayoutManager.VERTICAL, false,
        )
    }
}