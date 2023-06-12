package com.example.isis3520_202220_team25_kotlin.domain

import com.example.isis3520_202220_team25_kotlin.data.JobRepository
import com.example.isis3520_202220_team25_kotlin.data.database.entities.toDomain
import com.example.isis3520_202220_team25_kotlin.domain.model.Job
import java.util.prefs.AbstractPreferences
import javax.inject.Inject
import kotlin.random.Random

class GetRecomendationJob @Inject constructor(private val repository: JobRepository){

    suspend fun GetRecomendations(preferences: String):List<Job>{
        var jobs = repository.getAllJobsFromApi()
        val jobsFiltered = arrayListOf<Job>()
        val jobsReturn = arrayListOf<Job>()


        if(jobs.isNotEmpty()){

            repository.clearJobs()
            repository.insetJobs(jobs.map { it.toDomain() })
            for (job in jobs )
            {
                for (string in job.tags)
                    if(string.lowercase() == preferences.lowercase())
                        jobsFiltered.add(job)
            }


            if(jobsFiltered.size > 0)
            {
                for (i in 0..2)
                {
                    val index = Random.nextInt(0,jobsFiltered.size)
                    jobsReturn.add(jobsFiltered[index])
                    jobsFiltered.removeAt(index)
                }
            }
            else
            {
                for (i in 0..2)
                {
                    val index = Random.nextInt(0,jobs.size)
                    jobsReturn.add(jobs[index])
                }
            }
            return jobsReturn
        }
        else{
            jobs = repository.getAllJobsFromDatabase()
            for (job in jobs )
            {
                for (string in job.tags)
                    if(string.lowercase() == preferences.lowercase())
                        jobsFiltered.add(job)
            }


            for (i in 0..2)
            {
                val index = Random.nextInt(0,jobsFiltered.size)
                jobsReturn.add(jobsFiltered[index])
            }
            return jobsReturn
        }
    }
}