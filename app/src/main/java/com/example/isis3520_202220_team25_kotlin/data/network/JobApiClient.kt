package com.example.isis3520_202220_team25_kotlin.data.network

import com.example.isis3520_202220_team25_kotlin.data.User.UserModel
import com.example.isis3520_202220_team25_kotlin.data.jobs_list.model.JobListModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface JobApiClient {
    @GET("/Jobs/.json")
    suspend fun getAllJobs():Response<List<JobListModel>>

    @GET("/Jobs/{id}/.json")
    suspend fun getDetailJob(@Path("id") id:Int):Response<JobListModel>

    @GET("/User/{id}/.json")
    suspend fun getUser(@Path("id") id:Int):Response<UserModel>

    @PUT("/User/{id}/.json")
    suspend fun updateUser(@Path("id") id:Int, @Body user:UserModel)

    @PUT("/Jobs/{id}.json")
    suspend fun createJob(@Path("id") id:Int,@Body job:JobListModel)
}