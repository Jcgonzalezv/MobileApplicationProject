package com.example.isis3520_202220_team25_kotlin.data.jobs_list.model

import com.example.isis3520_202220_team25_kotlin.domain.model.Job
import com.google.gson.annotations.SerializedName

data class JobListModel(
    @SerializedName("jobName") val jobName: String,
    @SerializedName("jobDescription") val jobDescription: String,
    @SerializedName("workingHours") val workingHours: String,
    @SerializedName("salary") var salary: Double,
    @SerializedName("companyName") var companyName: String,
    @SerializedName("companyImage") var companyImage: String,
    @SerializedName("vacants") var vacants: Int,
    @SerializedName("tags") var tags: List<String>,
    @SerializedName("id") var id:Int
)

fun Job.toDomain() = JobListModel(jobName, jobDescription, workingHours, salary, companyName, companyImage, vacants, tags, id)