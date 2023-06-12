package com.example.isis3520_202220_team25_kotlin.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.isis3520_202220_team25_kotlin.domain.model.Job

@Entity(tableName = "job_table")
data class JobEntity (

    @ColumnInfo(name = "jobName") val jobName: String,
    @ColumnInfo(name = "jobDescription") val jobDescription: String,
    @ColumnInfo(name = "workingHours") val workingHours: String,
    @ColumnInfo(name = "salary") var salary: Double,
    @ColumnInfo(name = "companyName") var companyName: String,
    @ColumnInfo(name = "companyImage") var companyImage: String,
    @ColumnInfo(name = "vacants") var vacants: Int,
    @ColumnInfo(name = "tags") var tags: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id:Int = 0

        )

fun Job.toDomain():JobEntity
{
    var tagsLine:String = ""

    for(i in tags.indices)
    {
        if(i < tags.count() - 1)
        {
            tagsLine += tags[i] + ",";
        }
        else
        {
            tagsLine += tags[i]
        }
    }

    return JobEntity(jobName, jobDescription, workingHours, salary, companyName, companyImage, vacants, tagsLine, id)
}