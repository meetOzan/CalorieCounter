package com.meetozan.caloriecounter.data

data class Food(
    val name : String = "",
    val calorie : String = "",
    val picUrl : String = "",
    val protein : Int = -1,
    val fat : Int = -1,
    val carbohydrate : Int = -1
)