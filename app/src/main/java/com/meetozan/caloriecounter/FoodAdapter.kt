package com.meetozan.caloriecounter

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meetozan.caloriecounter.data.Food
import com.meetozan.caloriecounter.databinding.FoodCardBinding
import com.squareup.picasso.Picasso

class FoodAdapter(private val foodList: List<Food>) :
    RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    class ViewHolder(binding: FoodCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val foodCardBinding: FoodCardBinding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FoodCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foodList[position]
        holder.foodCardBinding.foodCard = food

        Picasso.get().load(food.url)
            .resize(64, 64)
            .centerCrop()
            .into(holder.foodCardBinding.cardImageFood)


        holder.foodCardBinding.btnDown.setOnClickListener {
            if (holder.foodCardBinding.expandedLayout.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(
                    holder.foodCardBinding.foodCardView,
                    AutoTransition()
                )
                holder.foodCardBinding.expandedLayout.visibility = View.VISIBLE

                holder.foodCardBinding.btnDown.animate().apply {
                    duration = 500
                    rotationX(180f)
                }.start()
            } else {
                TransitionManager.beginDelayedTransition(
                    holder.foodCardBinding.foodCardView,
                    AutoTransition()
                )
                holder.foodCardBinding.expandedLayout.visibility = View.GONE

                holder.foodCardBinding.btnDown.animate().apply {
                    duration = 500
                    rotationX(-0f)
                }.start()
            }
        }
    }

    override fun getItemCount(): Int = foodList.size

}