package com.example.petapp.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.petapp.data.entity.Pet  // import your Pet entity


@Entity(
    tableName = "health_summaries",
    foreignKeys = [ForeignKey(
        entity = Pet::class,
        parentColumns = ["id"],
        childColumns = ["petId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("petId")]
)
data class HealthSummary(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val petId: Int,
    val weight: Float,
    val height: Float,
    val notes: String?,
    val recordedAt: Long
)
