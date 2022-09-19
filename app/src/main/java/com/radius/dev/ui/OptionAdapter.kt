package com.radius.dev.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.radius.dev.data.model.Option
import com.radius.dev.databinding.ItemOptionsBinding

class OptionAdapter(val items: List<Option>, val optionslistener: OptionClickListener) :
    RecyclerView.Adapter<OptionAdapter.OptionViewHolder>() {

    inner class OptionViewHolder(val binding: ItemOptionsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(option: Option) {
            binding.option = option
            binding.root.setOnClickListener { optionslistener.onOptionClick(option) }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val holder = ItemOptionsBinding.inflate(LayoutInflater.from(parent.context), null, false)
        return OptionViewHolder(holder)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface OptionClickListener {
        fun onOptionClick(option: Option)
    }
}