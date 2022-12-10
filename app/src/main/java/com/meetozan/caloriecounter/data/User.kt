package com.meetozan.caloriecounter.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name : String = "",
    val surname : String = "",
    val email: String = "",
    val height : String = "",
    val weight : String = "",
):Parcelable