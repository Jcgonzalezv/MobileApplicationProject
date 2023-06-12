package com.example.isis3520_202220_team25_kotlin.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.isis3520_202220_team25_kotlin.data.database.dao.JobDao
import com.example.isis3520_202220_team25_kotlin.data.database.dao.UserDao
import com.example.isis3520_202220_team25_kotlin.data.database.entities.JobEntity
import com.example.isis3520_202220_team25_kotlin.data.database.entities.UserEntity

@Database(entities = [JobEntity::class, UserEntity::class], version = 1)
abstract class JobDatabase:RoomDatabase() {

    abstract fun getJobDao():JobDao
    abstract fun getUserDao(): UserDao
}