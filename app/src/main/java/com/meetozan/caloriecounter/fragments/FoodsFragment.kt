package com.meetozan.caloriecounter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.meetozan.caloriecounter.FoodAdapter
import com.meetozan.caloriecounter.data.Food
import com.meetozan.caloriecounter.databinding.FragmentFoodsBinding

class FoodsFragment : Fragment() {

    private lateinit var binding: FragmentFoodsBinding
    var dbFood = Firebase.firestore.collection("food")
    private lateinit var rv: RecyclerView
    private lateinit var adapter: FoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View {
        binding = FragmentFoodsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFood()
    }

    private fun getFood(){
        dbFood.addSnapshotListener { querySnapshot, firestoreException ->
            firestoreException?.let {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            }
            querySnapshot?.let {
                val foodList: ArrayList<Food> = ArrayList()
                for (document in it) {
                    val food = document.toObject<Food>()
                    foodList.add(food)
                }
                rv = binding.rvFood
                val linearLayoutManager = LinearLayoutManager(requireContext())
                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                rv.layoutManager = linearLayoutManager

                adapter = FoodAdapter(foodList)
                rv.adapter = adapter
            }
        }
    }
}
