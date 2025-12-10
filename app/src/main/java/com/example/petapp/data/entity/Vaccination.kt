package com.example.petapp.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.petapp.data.entity.Pet

@Entity(
    tableName = "vaccinations",
    foreignKeys = [ForeignKey(
        entity = Pet::class,
        parentColumns = ["id"],
        childColumns = ["petId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("petId")]
)
data class Vaccination(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val petId: Int,
    val name: String,
    val date: Long,      // timestamp
    val nextDue: Long? = null
)
