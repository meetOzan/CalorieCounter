package com.meetozan.caloriecounter.ui.main.addmeal

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meetozan.caloriecounter.data.Food
import com.meetozan.caloriecounter.databinding.AddMealCardBinding
import com.meetozan.caloriecounter.ui.main.profile.UserViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AddMealAdapter(private var addMealList: List<Food>, context: Context) :
    RecyclerView.Adapter<AddMealAdapter.ViewHolder>() {

    private val userViewModel by lazy { UserViewModel() }
    private val addMealViewModel by lazy { AddMealViewModel(context) }

    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val current = LocalDate.now().format(formatter)

    class ViewHolder(binding: AddMealCardBinding) : RecyclerView.ViewHolder(binding.root) {
        val addMealBinding: AddMealCardBinding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AddMealCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = addMealList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val addMeal = addMealList[position]
        holder.addMealBinding.addMealCard = addMeal
        val binding = holder.addMealBinding

        binding.tvAddMealCardMany.text = addMeal.many

        binding.btnAddMealCardAdd.setOnClickListener {
            var many = Integer.parseInt(binding.tvAddMealCardMany.text.toString())
            if (many < 9) {
                many += 1
                binding.tvAddMealCardMany.text = many.toString()

                val _hashMap = hashMapOf<String, Any>(
                    "name" to addMeal.name,
                    "calorie" to addMeal.calorie,
                    "url" to addMeal.url,
                    "protein" to addMeal.protein,
                    "fat" to addMeal.fat,
                    "carbohydrate" to addMeal.carbohydrate,
                    "many" to many.toString()
                )

                userViewModel.eatFood(current, addMeal.name, _hashMap)
            }
        }

        binding.btnAddMealCardMinus.setOnClickListener {
            var many = Integer.parseInt(binding.tvAddMealCardMany.text.toString())
            if (many > 0) {
                many += -1
                binding.tvAddMealCardMany.text = many.toString()
            }

            val _hashMap = hashMapOf<String, Any>(
                "name" to addMeal.name,
                "calorie" to addMeal.calorie,
                "url" to addMeal.url,
                "protein" to addMeal.protein,
                "fat" to addMeal.fat,
                "carbohydrate" to addMeal.carbohydrate,
                "many" to many.toString()
            )

            if (many > 0) {
                userViewModel.eatFood(current, addMeal.name, _hashMap)
            } else {
                userViewModel.vomitFood(current, addMeal.name)
            }
        }
    }
}


