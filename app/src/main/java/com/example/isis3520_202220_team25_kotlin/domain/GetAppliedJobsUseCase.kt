package com.example.isis3520_202220_team25_kotlin.domain

import com.example.isis3520_202220_team25_kotlin.data.JobRepository
import com.example.isis3520_202220_team25_kotlin.data.User.toDomain
import com.example.isis3520_202220_team25_kotlin.data.database.entities.toDomain
import com.example.isis3520_202220_team25_kotlin.data.database.entities.toDomainEntity
import com.example.isis3520_202220_team25_kotlin.domain.model.Job
import com.example.isis3520_202220_team25_kotlin.domain.model.User
import javax.inject.Inject

class GetAppliedJobsUseCase @Inject constructor(private val repository: JobRepository){

    suspend fun getAppliedJobs (idUser:Int):List<Job>{
        var user = repository.getUser(idUser)
        if (user.userName.isEmpty() && user.userCareer.isEmpty())
        {
            user = repository.getUserFromDatabase()
        }

        var jobs = repository.getAllJobsFromApi()

        if(jobs.isEmpty()){
            jobs = repository.getAllJobsFromDatabase()
        }

        var jobsReturn = arrayListOf<Job>()

        for(i in user.jobsApplied.indices)
        {
            for(j in jobs.indices)
            {
                if(jobs[j].id == user.jobsApplied[i] && !jobsReturn.contains(jobs[j]))
                {
                    jobsReturn.add(jobs[j])
                }
            }
        }
        return jobsReturn
    }
}