package com.example.isis3520_202220_team25_kotlin.domain

import com.example.isis3520_202220_team25_kotlin.data.JobRepository
import com.example.isis3520_202220_team25_kotlin.data.database.entities.toDomain
import com.example.isis3520_202220_team25_kotlin.domain.model.Job
import javax.inject.Inject

class GetLatestIdUseCase @Inject constructor(private val repository: JobRepository){

    suspend fun getId():Int{
        val jobs = repository.getAllJobsFromApi()

        if(jobs.isNotEmpty()){
            repository.clearJobs()
            repository.insetJobs(jobs.map { it.toDomain() })
            return jobs.size
        }
        else{
            return repository.getAllJobsFromDatabase().size
        }
    }
}