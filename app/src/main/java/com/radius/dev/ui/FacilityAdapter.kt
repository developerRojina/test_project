package com.radius.dev.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.radius.dev.data.model.Facility
import com.radius.dev.databinding.ItemFacilityBinding

class FacilityAdapter(val item: Facility) :
    RecyclerView.Adapter<FacilityAdapter.FacilityViewHolder>() {

    inner class FacilityViewHolder(val binding: ItemFacilityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(facility: Facility) {
            binding.facility = facility
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacilityViewHolder {
        val holder = ItemFacilityBinding.inflate(LayoutInflater.from(parent.context), null, false)
        return FacilityViewHolder(holder)
    }

    override fun onBindViewHolder(holder: FacilityViewHolder, position: Int) {
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return 1
    }
}