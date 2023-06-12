package com.example.isis3520_202220_team25_kotlin.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.isis3520_202220_team25_kotlin.domain.model.User

@Entity(tableName = "user_table")
data class UserEntity
    (
    @ColumnInfo(name="age") val userAge: Int,
    @ColumnInfo(name="career") val userCareer: String,
    @ColumnInfo(name="gender") val userGender: String,
    @ColumnInfo(name="job_preference") var userJobPreference: String,
    @ColumnInfo(name="name") var userName: String,
    @ColumnInfo(name="semester") var userSemester: String,
    @ColumnInfo(name = "userImage") var userImage:String,
    @ColumnInfo(name = "appliedJobs") var jobsApplied: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id:Int = 0,

)
fun User.toDomainEntity():UserEntity
{
    var tagsLine:String = ""

    for(i in jobsApplied.indices)
    {
        if(i < jobsApplied.count() - 1)
        {
            tagsLine += jobsApplied[i].toString() + ","
        }
        else
        {
            tagsLine += jobsApplied[i].toString()
        }
    }

    return UserEntity(userAge,userCareer,userGender,userJobPreference,userName,userSemester, userImage, tagsLine)
}
