package com.meetozan.caloriecounter.ui.main.addmeal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meetozan.caloriecounter.databinding.FragmentAddMealBinding
import com.meetozan.caloriecounter.ui.main.food.FoodViewModel

class AddMealFragment : Fragment() {

    private lateinit var binding: FragmentAddMealBinding
    private val foodViewModel by lazy { FoodViewModel(requireContext()) }
    private lateinit var adapter: AddMealAdapter
    private lateinit var rv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv = binding.rvAddMeal
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv.layoutManager = linearLayoutManager

        foodObserver()

        binding.btnEat.setOnClickListener {

        }
    }

    private fun foodObserver() {
        foodViewModel.foodList.observe(viewLifecycleOwner) {
            adapter = AddMealAdapter(it, requireContext())
            rv.adapter = adapter
        }
    }
}

