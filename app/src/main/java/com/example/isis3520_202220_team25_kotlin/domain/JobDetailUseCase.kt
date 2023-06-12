package com.example.isis3520_202220_team25_kotlin.domain

import com.example.isis3520_202220_team25_kotlin.data.JobRepository
import com.example.isis3520_202220_team25_kotlin.data.jobs_list.model.JobListModel
import com.example.isis3520_202220_team25_kotlin.domain.model.Job
import javax.inject.Inject

class JobDetailUseCase @Inject constructor(private val repository: JobRepository){
    suspend fun getDetailJob (id:Int): Job?{
        var job = repository.getDetailJob(id)

        return if(job.jobName.isNotEmpty() && job.jobDescription.isNotEmpty()) {
            repository.getDetailJob(id)
        } else {
            repository.getDetailJobFromDatabase(id)
        }
    }
}