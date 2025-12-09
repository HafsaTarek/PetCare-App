package com.example.petapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class Meal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val petId: Int,
    val mealName: String,
    val time: String,
    val quantity: String,
//    val imageUri: String? = null
)
