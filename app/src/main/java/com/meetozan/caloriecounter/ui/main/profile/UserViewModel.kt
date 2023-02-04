package com.meetozan.caloriecounter.ui.main.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.meetozan.caloriecounter.data.User

class UserViewModel : ViewModel() {

    private var auth = Firebase.auth
    private val db = Firebase.firestore

    private var _userInfo = MutableLiveData<User>()
    val userInfo: MutableLiveData<User>
        get() = _userInfo

    init {
        getUserInfo()
    }

    fun getUserInfo() {
        db.collection("users").document(auth.currentUser?.email.toString())
            .get()
            .addOnSuccessListener {
                it.toObject<User>()?.let { user ->
                    _userInfo.value = user
                }
            }
    }

    fun setCalorieData(calorie: Int) {
        db.collection("users").document(auth.currentUser?.email.toString())
            .update(
                mapOf(
                    ("calorieGoal" to calorie)
                )
            )
    }
}