package com.meetozan.caloriecounter.ui.main.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meetozan.caloriecounter.data.Food
import com.meetozan.caloriecounter.databinding.CalendarCardBinding

class MainAdapter(private val mainFoodList: List<Food>) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {


    class ViewHolder(binding: CalendarCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val mainCardBinding: CalendarCardBinding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CalendarCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = mainFoodList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mainAdapter = mainFoodList[position]
        holder.mainCardBinding.calendarFoodCard = mainAdapter
    }
}