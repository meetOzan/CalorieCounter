package com.meetozan.caloriecounter.ui.main.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meetozan.caloriecounter.data.Food
import com.meetozan.caloriecounter.databinding.CalendarCardBinding

class CalendarAdapter(private val calendarFoodList: List<Food>) :
    RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    var totalCalorie: Int = 0

    class ViewHolder(binding: CalendarCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val calendarCardBinding: CalendarCardBinding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CalendarCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = calendarFoodList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val calendar = calendarFoodList[position]

        val cardCalorie = Integer.parseInt(calendar.calorie)
        holder.calendarCardBinding.calendarFoodCard = calendar

        totalCalorie += cardCalorie
    }
}