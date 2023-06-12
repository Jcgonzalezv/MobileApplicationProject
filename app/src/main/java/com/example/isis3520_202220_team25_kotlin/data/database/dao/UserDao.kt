package com.example.isis3520_202220_team25_kotlin.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.isis3520_202220_team25_kotlin.data.database.entities.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table")
    suspend fun getUser(): UserEntity

    @Query("SELECT * FROM user_table WHERE id = :id")
    suspend fun getUserDetail(id: Int): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user:UserEntity)

    @Query("DELETE FROM user_table")
    suspend fun deleteUser()
}