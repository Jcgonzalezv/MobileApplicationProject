package com.example.isis3520_202220_team25_kotlin.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.isis3520_202220_team25_kotlin.data.database.entities.JobEntity

@Dao
interface JobDao {

    @Query("SELECT * FROM job_table")
    suspend fun getAllJobs():List<JobEntity>

    @Query("SELECT * FROM job_table WHERE id = :id")
    suspend fun getDetailJob(id: Int):JobEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertALl(jobs:List<JobEntity>)

    @Query("DELETE FROM job_table")
    suspend fun deleteAllJobs()
}