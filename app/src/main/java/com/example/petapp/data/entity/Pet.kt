package com.example.petapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pets")
data class Pet(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val type: String,        // dog, cat
    val age: Int,
    val description: String,
    val imageUrl: String? = null,
)
