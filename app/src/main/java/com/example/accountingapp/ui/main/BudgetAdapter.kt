package com.example.accountingapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.accountingapp.databinding.ListBudgetItemBinding
import com.example.accountingapp.store.database.Record
import java.util.*

interface OnBudgetItemClickListener {
    fun onItemClick(id: UUID?)
}

class BudgetAdapter(val listener: OnBudgetItemClickListener): ListAdapter<Record, RecyclerView.ViewHolder>(RecordDiffCallback()) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as RecordViewHolder).bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecordViewHolder(
            ListBudgetItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }


    inner class RecordViewHolder(
        private val binding: ListBudgetItemBinding
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: Record) {
            binding.apply {
                record = item
                executePendingBindings()
            }
        }

        override fun onClick(v: View?) {
            listener.onItemClick( binding.record?.recordId)
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