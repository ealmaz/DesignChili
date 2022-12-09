package com.design.chili.view.modals.bottom_sheet.serach_bottom_sheet

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class SearchSelectorAdapter(private var listener: SearchSelectorItemListener, val isHeaderVisible: Boolean) : ListAdapter<Pair<Type, Any>, RecyclerView.ViewHolder>(AsyncDifferConfig.Builder(SelectorItemsDiffUtil()).build()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Type.HEADER.value -> SearchSelectorHeaderVH.create(parent)
            else -> SearchSelectorItemVH.create(parent) { listener.onItemSelection(it) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].first.value
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val isLast = when (position+1 < currentList.size){
            true -> currentList[position+1].first.value == Type.HEADER.value
            else -> true
        }
        when (holder) {
            is SearchSelectorItemVH -> holder.onBind(currentList[position].second as Option, isLast)
            is SearchSelectorHeaderVH -> holder.onBind(currentList[position].second as String)
        }
    }

    fun addItems(list: List<Option>, filter: String = "") {
        val filteredList = list.filter { it.value.contains(filter, true) }
        val sortedList = filteredList.groupBy { it.value.firstOrNull()?.uppercase() ?: "" }
        val newList = mutableListOf<Pair<Type, Any>>()
        sortedList.forEach {
            if (isHeaderVisible) newList.add(Type.HEADER to it.key)
            it.value.forEach { item ->
                newList.add(Type.ITEM to item.copy())
            }
        }
        submitList(newList)
    }
}

interface SearchSelectorItemListener {
    fun onItemSelection(selectedId: String)
}

enum class Type(var value: Int) {
    HEADER(1), ITEM(2)
}

class SelectorItemsDiffUtil : DiffUtil.ItemCallback<Pair<Type, Any>>() {

    override fun areItemsTheSame(oldItem: Pair<Type, Any>, newItem: Pair<Type, Any>): Boolean {
        return oldItem.first == newItem.first
    }

    override fun areContentsTheSame(oldItem: Pair<Type, Any>, newItem: Pair<Type, Any>): Boolean {
        val newItemValue = newItem.second
        val oldItemItem = oldItem.second
        return if (newItemValue is Option && oldItemItem is Option) {
            newItemValue == oldItemItem
        } else {
            false
        }
    }
}