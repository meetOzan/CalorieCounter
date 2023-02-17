package com.meetozan.caloriecounter.ui.main.main

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

class MainViewModel(context: Context) : ViewModel() {

    private var auth = Firebase.auth
    private val db = Firebase.firestore.collection("users")

    private val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    private val current = LocalDate.now().format(formatter)

    private val _mainList = MutableLiveData<List<Food>>()
    val mainList: MutableLiveData<List<Food>>
        get() = _mainList

    init {
        getMain(context)
    }

    private fun getMain(context: Context) {
        db.document(auth.currentUser?.email.toString()).collection(current)
            .addSnapshotListener { querySnapshot, firestoreException ->
                firestoreException?.let {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }
                querySnapshot?.let {
                    val mainList: ArrayList<Food> = ArrayList()
                    for (document in it) {
                        val mainFood = document.toObject<Food>()
                        mainList.add(mainFood)
                        _mainList.postValue(mainList)
                    }
                }
            }
    }
}