package com.meetozan.caloriecounter.ui.main.food

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.meetozan.caloriecounter.data.Food

class FoodViewModel(context: Context) : ViewModel() {

    private var db = Firebase.firestore.collection("food")

    private var _foodList = MutableLiveData<List<Food>>()
    val foodList: MutableLiveData<List<Food>>
        get() = _foodList

    init {
        getFood(context)
    }

    private fun getFood(context: Context) {
        db.addSnapshotListener { querySnapshot, firestoreException ->
            firestoreException?.let {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            }
            querySnapshot?.let {
                val foodList: ArrayList<Food> = ArrayList()
                for (document in it) {
                    val food = document.toObject<Food>()
                    foodList.add(food)
                    _foodList.postValue(foodList)
                }
            }
        }
    }

    private fun setSingleData(data: String, path: String, name: String) {
        db.document(name)
            .set(
                mapOf(
                    (path to data)
                )
            )
    }

}