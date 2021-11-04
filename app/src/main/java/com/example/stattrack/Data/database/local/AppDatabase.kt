package com.example.stattrack.Data.database.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.stattrack.Data.database.Entity.PlayerEntity
import com.example.stattrack.Data.database.local.logik.PlayerDao

@Database(
    entities = [PlayerEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun PlayerDao(): PlayerDao

    companion object {
        fun build(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "stattrack-local-db")
                .build()
        }
    }
}