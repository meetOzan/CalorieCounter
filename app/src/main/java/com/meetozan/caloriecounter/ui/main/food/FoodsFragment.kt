package com.meetozan.caloriecounter.ui.main.food

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meetozan.caloriecounter.databinding.FragmentFoodsBinding

class FoodsFragment : Fragment() {

    private lateinit var binding: FragmentFoodsBinding
    private lateinit var rv: RecyclerView
    private lateinit var adapter: FoodAdapter
    private val foodViewModel by lazy { FoodViewModel(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFoodsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv = binding.rvFood
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv.layoutManager = linearLayoutManager

        foodObserver()
    }

    private fun foodObserver() {
        foodViewModel.foodList.observe(viewLifecycleOwner) { list ->
            adapter = FoodAdapter(list)
            rv.adapter = adapter
        }
    }
}