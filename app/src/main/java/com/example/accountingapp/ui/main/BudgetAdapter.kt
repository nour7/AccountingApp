package com.example.accountingapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.accountingapp.databinding.ListBudgetItemBinding
import com.example.accountingapp.store.database.Record

class BudgetAdapter: ListAdapter<Record, RecyclerView.ViewHolder>(RecordDiffCallback()) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val plant = getItem(position)
        (holder as RecordViewHolder).bind(plant)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecordViewHolder(
            ListBudgetItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    class RecordViewHolder(
        private val binding: ListBudgetItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Record) {
            binding.apply {
                record = item
                executePendingBindings()
            }
        }
    }
}

private class RecordDiffCallback : DiffUtil.ItemCallback<Record>() {

    override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
        return oldItem.recordId == newItem.recordId
    }

    override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
        return oldItem == newItem
    }
}