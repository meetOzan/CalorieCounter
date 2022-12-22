package com.meetozan.caloriecounter.data

import android.os.Parcelable
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val height: String = "",
    val weight: String = "",
    val calorieGoal : Int = -1
) : Parcelable {

    companion object {
        fun DocumentSnapshot.toUser(): User? {
            return try {
                val email = getString("email").toString()
                val name = getString("name").toString()
                val surname = getString("surname").toString()
                val height = getString("height").toString()
                val weight = getString("weight").toString()
                val calorieGoal = get("calorieGoal") as Int
                User(name, surname, email, height, weight,calorieGoal)
            } catch (e: java.lang.Exception) {
                Log.e(TAG, "Error converting user profile", e)
                FirebaseCrashlytics.getInstance().log("Error converting user profile")
                FirebaseCrashlytics.getInstance().setCustomKey("email", id)
                FirebaseCrashlytics.getInstance().recordException(e)
                null
            }
        }
        private const val TAG = "User"
    }
}