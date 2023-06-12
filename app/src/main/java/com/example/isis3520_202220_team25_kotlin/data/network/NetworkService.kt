package com.example.isis3520_202220_team25_kotlin.data.network

import android.util.Log
import com.example.isis3520_202220_team25_kotlin.data.User.UserModel
import com.example.isis3520_202220_team25_kotlin.data.jobs_list.model.JobListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class NetworkService @Inject constructor(private val api: JobApiClient) {

    suspend fun getJobs(): List<JobListModel> {
        try {
            return withContext(Dispatchers.IO)
            {
                val response: Response<List<JobListModel>> = api.getAllJobs()
                response.body() ?: emptyList()
            }
        }
        catch (e: Exception)
        {
            return emptyList()
        }
    }

    suspend fun getDetailJob(id: Int): JobListModel {
        try {
            return withContext(Dispatchers.IO)
            {
                val response: Response<JobListModel> = api.getDetailJob(id)
                response.body() ?: JobListModel("", "", "", 0.0, "", "", 0, emptyList(), 0)
            }
        }
        catch (e: Exception)
        {
            return JobListModel("", "", "", 0.0, "", "", 0, emptyList(), 0)
        }
    }

    suspend fun getDetailUser(id: Int): UserModel {
        try {
            return withContext(Dispatchers.IO)
            {
                val response: Response< UserModel> = api.getUser(id)
                response.body() ?:  UserModel(0, "", "", "", "", "", "", arrayListOf())
            }
        }
        catch (e: Exception)
        {
            return UserModel(0, "", "", "", "", "", "", arrayListOf())
        }

    }

    suspend fun createJob(id: Int, job: JobListModel): Boolean
    {
        try {
            api.createJob(id, job)
            return true
        }
        catch (e: Exception)
        {
            return false
        }
    }

    suspend fun updateUser(id: Int, user: UserModel): Boolean
    {
        try {
            api.updateUser(id, user)
            return true
        }
        catch (e: Exception)
        {
            return false
        }
    }

}