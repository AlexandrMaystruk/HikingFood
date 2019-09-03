package com.gmail.maystruks08.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gmail.maystruks08.data.room.entity.MenuTable
import maystruks08.gmail.com.data.room.dao.*

@Database(
    entities = [MenuTable::class], version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {


    abstract fun userDao(): MenuDAO

}