package com.example.isis3520_202220_team25_kotlin.domain

import com.example.isis3520_202220_team25_kotlin.data.JobRepository
import com.example.isis3520_202220_team25_kotlin.data.database.entities.toDomain
import com.example.isis3520_202220_team25_kotlin.data.database.entities.toDomainEntity
import com.example.isis3520_202220_team25_kotlin.domain.model.User
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: JobRepository){
    suspend fun getUserJob (id:Int): User?{
        var user = repository.getUser(id)
        if (user.userName.isNotEmpty() && user.userCareer.isNotEmpty())
        {
            repository.clearUser()
            repository.insetUser(user.toDomainEntity())
            if(user.jobsApplied == null)
                user.jobsApplied = arrayListOf()
            return user
        }
        else
        {
            user = repository.getUserFromDatabase()
            if(user.jobsApplied == null)
                user.jobsApplied = arrayListOf()

            return user
        }

    }
}