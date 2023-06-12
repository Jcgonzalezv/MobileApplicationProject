package com.example.isis3520_202220_team25_kotlin.data


import com.example.isis3520_202220_team25_kotlin.data.User.UserModel
import com.example.isis3520_202220_team25_kotlin.data.database.dao.JobDao
import com.example.isis3520_202220_team25_kotlin.data.database.dao.UserDao
import com.example.isis3520_202220_team25_kotlin.data.database.entities.JobEntity
import com.example.isis3520_202220_team25_kotlin.data.database.entities.UserEntity
import com.example.isis3520_202220_team25_kotlin.data.jobs_list.model.JobListModel
import com.example.isis3520_202220_team25_kotlin.data.jobs_list.model.JobListProvider
import com.example.isis3520_202220_team25_kotlin.data.network.NetworkService
import com.example.isis3520_202220_team25_kotlin.domain.model.Job
import com.example.isis3520_202220_team25_kotlin.domain.model.User
import com.example.isis3520_202220_team25_kotlin.domain.model.toDomain
import javax.inject.Inject

class JobRepository @Inject constructor(
    private val api: NetworkService,
    private val jobDao: JobDao,
    private val userDao: UserDao
) {

    suspend fun getAllJobsFromApi(): List<Job> {
        val response: List<JobListModel> = api.getJobs()
        response.filter { x: JobListModel? -> x != null }
        return response.map {it.toDomain()}
    }

    suspend fun getAllJobsFromDatabase():List<Job>
    {
        val response = jobDao.getAllJobs()
        return response.map { it.toDomain() }
    }

    suspend fun getDetailJob(id: Int): Job {
        return api.getDetailJob(id).toDomain()
    }

    suspend fun getDetailJobFromDatabase(id: Int):Job {
        return jobDao.getDetailJob(id).toDomain()
    }

    suspend fun getUser(id: Int): User {
        var user = api.getDetailUser(id)
        if(user.jobsApplied == null)
            user.jobsApplied = arrayListOf()
        return user.toDomain()
    }

    suspend fun createJob(id: Int, job: JobListModel):Boolean {
        return api.createJob(id, job)
    }

    suspend fun updateUser(id: Int, user: UserModel):Boolean {
        return api.updateUser(id, user)
    }

    suspend fun insetJobs(jobs:List<JobEntity>){
        jobDao.insertALl(jobs)
    }

    suspend fun clearJobs(){
        jobDao.deleteAllJobs()
    }
    suspend fun insetUser(user: UserEntity){
        userDao.insertUser(user)
    }

    suspend fun clearUser(){
        userDao.deleteUser()
    }
    suspend fun getUserFromDatabase():User{
        val response=userDao.getUser()
        return response.toDomain()
    }
}