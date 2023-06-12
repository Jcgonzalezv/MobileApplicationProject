package com.example.isis3520_202220_team25_kotlin.data.User


import com.example.isis3520_202220_team25_kotlin.domain.model.User
import com.google.gson.annotations.SerializedName


data class UserModel
(
        @SerializedName("age") val userAge: Int,
        @SerializedName("career") val userCareer: String,
        @SerializedName("gender") val userGender: String,
        @SerializedName("job_preference") var userJobPreference: String,
        @SerializedName("name") var userName: String,
        @SerializedName("semester") var userSemester: String,
        @SerializedName("userImage") var userImage: String,
        @SerializedName("appliedJobs") var jobsApplied: ArrayList<Int> = arrayListOf()
)

fun User.toDomain()= UserModel(userAge,userCareer,userGender,userJobPreference,userName,userSemester, userImage, jobsApplied)
