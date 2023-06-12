package com.example.isis3520_202220_team25_kotlin.domain.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.isis3520_202220_team25_kotlin.data.database.entities.JobEntity
import com.example.isis3520_202220_team25_kotlin.data.jobs_list.model.JobListModel

data class Job (
    val jobName: String,
    val jobDescription: String,
    val workingHours: String,
    var salary: Double,
    var companyName: String,
    var companyImage: String,
    var vacants: Int,
    var tags: List<String>,
    var id:Int
        )

fun JobListModel.toDomain() = Job(jobName, jobDescription, workingHours, salary, companyName, companyImage, vacants, tags, id)
fun JobEntity.toDomain():Job{

    var tagsList: List<String> = tags.split(",")

    return Job(jobName, jobDescription, workingHours, salary, companyName, companyImage, vacants, tagsList, id)
}