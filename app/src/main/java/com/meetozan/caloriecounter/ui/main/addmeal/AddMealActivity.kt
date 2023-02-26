package com.meetozan.caloriecounter.ui.main.addmeal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meetozan.caloriecounter.databinding.ActivityAddMealBinding
import com.meetozan.caloriecounter.ui.main.MainActivity
import com.meetozan.caloriecounter.ui.main.food.FoodViewModel

class AddMealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMealBinding
    private val foodViewModel by lazy { FoodViewModel(this@AddMealActivity) }
    private lateinit var adapter: AddMealAdapter
    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rv = binding.rvAddMeal
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv.layoutManager = linearLayoutManager

        foodObserver()

        binding.btnEat.setOnClickListener {
            val intent = Intent(this@AddMealActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this, "Bon appétit!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun foodObserver() {
        foodViewModel.foodList.observe(this) {
            adapter = AddMealAdapter(it, this@AddMealActivity)
            rv.adapter = adapter
        }
    }
}