package com.example.isis3520_202220_team25_kotlin.domain.model


import com.example.isis3520_202220_team25_kotlin.data.User.UserModel
import com.example.isis3520_202220_team25_kotlin.data.database.entities.UserEntity

data class User (
      val userAge: Int,
      val userCareer: String,
      val userGender: String,
      var userJobPreference: String,
      var userName: String,
      var userSemester: String,
      var userImage: String,
      var jobsApplied: ArrayList<Int> = arrayListOf()
)


fun UserEntity.toDomain():User
{
      var jobsList: ArrayList<Int> = ArrayList()
      if(jobsApplied != null){
            var tagsList: List<String> = jobsApplied.split(",")
            for (i in tagsList.indices)
            {
                  jobsList.add(tagsList[i].toInt())
            }
      }
      return User(userAge,userCareer,userGender,userJobPreference,userName,userSemester, userImage, jobsList)
}


fun UserModel.toDomain()=User(userAge,userCareer,userGender,userJobPreference,userName,userSemester, userImage, jobsApplied)