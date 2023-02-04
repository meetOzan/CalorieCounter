package com.meetozan.caloriecounter.ui.main.food

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.meetozan.caloriecounter.data.Food

class FoodViewModel(context: Context) : ViewModel() {

    private var auth = Firebase.auth
    private var dbFood = Firebase.firestore.collection("food")

    private var _foodList = MutableLiveData<Food>()
    val foodList: MutableLiveData<Food>
        get() = _foodList

    init {
        getFood(context)
    }

    private fun getFood(context: Context) {
        dbFood.addSnapshotListener { querySnapshot, firestoreException ->
            firestoreException?.let {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            }
            querySnapshot?.let {
                val foodList: ArrayList<Food> = ArrayList()
                for (document in it) {
                    val food = document.toObject<Food>()
                    foodList.add(food)
                }
            }
        }
    }
}