package com.example.isis3520_202220_team25_kotlin.domain

import com.example.isis3520_202220_team25_kotlin.data.JobRepository
import com.example.isis3520_202220_team25_kotlin.data.jobs_list.model.JobListModel
import com.example.isis3520_202220_team25_kotlin.data.jobs_list.model.toDomain
import com.example.isis3520_202220_team25_kotlin.domain.model.Job
import javax.inject.Inject

class JobCreateUseCase @Inject constructor(private val repository: JobRepository){

    suspend fun CreateJob(id: Int, job: Job): Boolean {
        return repository.createJob(id, job.toDomain())
    }
}