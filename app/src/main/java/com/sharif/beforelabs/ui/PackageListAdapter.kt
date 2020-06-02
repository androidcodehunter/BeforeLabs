package com.sharif.beforelabs.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sharif.beforelabs.R


class PackageListAdapter : ListAdapter<Pair<String, String>, RecyclerView.ViewHolder>(DiffCallbackCategory()) {

    var onItemClickListener: ((String, String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CategoryViewHolder).bind(getItem(position))
    }


    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onItemClickListener?.invoke(getItem(adapterPosition).first, getItem(adapterPosition).second)
            }
        }

        fun bind(appPackageName: Pair<String, String>) {
            (itemView as TextView).text = appPackageName.first
        }
    }
}


class DiffCallbackCategory : DiffUtil.ItemCallback<Pair<String, String>>() {

    override fun areItemsTheSame(oldItem: Pair<String, String>, newItem: Pair<String, String>): Boolean {
        return oldItem.first == newItem.first
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Pair<String, String>, newItem: Pair<String, String>): Boolean {
        return oldItem == newItem
    }
}
