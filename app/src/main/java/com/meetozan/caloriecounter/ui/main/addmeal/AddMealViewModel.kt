package com.meetozan.caloriecounter.ui.main.addmeal

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.meetozan.caloriecounter.data.Food
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AddMealViewModel(context: Context) : ViewModel() {


    private var auth = Firebase.auth
    private var dbUsers = Firebase.firestore.collection("users")

    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val current = LocalDate.now().format(formatter)

    private var _addMealList = MutableLiveData<List<Food>>()
    val addMealList: MutableLiveData<List<Food>>
        get() = _addMealList

    init {
        getAddedMeal(context)
    }

    private fun getAddedMeal(context: Context) {
        dbUsers.document(auth.currentUser?.email.toString())
            .collection(current)
            .addSnapshotListener { querySnapshot, firestoreException ->
                firestoreException?.let {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }
                querySnapshot?.let {
                    val addedMealList: ArrayList<Food> = ArrayList()
                    for (document in it) {
                        val addedMeal = document.toObject<Food>()
                        addedMealList.add(addedMeal)
                        _addMealList.postValue(addedMealList)
                    }
                }
            }
    }
}