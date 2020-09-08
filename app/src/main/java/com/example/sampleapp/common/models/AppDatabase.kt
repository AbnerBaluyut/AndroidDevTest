package com.example.sampleapp.common.models

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sampleapp.common.models.dao.UserDao
import com.example.sampleapp.common.models.entity.UsersData

@Database(entities = [UsersData::class], version = 15, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}