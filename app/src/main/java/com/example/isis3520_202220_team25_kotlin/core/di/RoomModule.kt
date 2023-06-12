package com.example.isis3520_202220_team25_kotlin.core.di

import android.content.Context
import androidx.room.Room
import com.example.isis3520_202220_team25_kotlin.data.database.JobDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val JOB_DATABASE_NAME = "job_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, JobDatabase::class.java, JOB_DATABASE_NAME).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideJobDao(db:JobDatabase) = db.getJobDao()

    @Singleton
    @Provides
    fun provideUserDao(db:JobDatabase) = db.getUserDao()
}