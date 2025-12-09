package com.example.petapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.petapp.data.dao.*
import com.example.petapp.data.entity.*

@Database(
    entities = [Pet::class, Vaccination::class, Mood::class, HealthSummary::class],
    version = 1,
    exportSchema = false
)
abstract class PetDatabase : RoomDatabase() {

    // DAOs
    abstract fun petDao(): PetDao
    abstract fun vaccinationDao(): VaccinationDao
    abstract fun moodDao(): MoodDao
    abstract fun healthSummaryDao(): HealthSummaryDao

    companion object {

        @Volatile
        private var instance: PetDatabase? = null

        fun getInstance(context: Context): PetDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PetDatabase::class.java,
                "pet_database"
            ).build()
    }
}
