package com.example.isis3520_202220_team25_kotlin.domain

import com.example.isis3520_202220_team25_kotlin.data.JobRepository
import com.example.isis3520_202220_team25_kotlin.data.User.toDomain
import com.example.isis3520_202220_team25_kotlin.data.database.entities.toDomain
import com.example.isis3520_202220_team25_kotlin.data.database.entities.toDomainEntity
import com.example.isis3520_202220_team25_kotlin.domain.model.User
import javax.inject.Inject

class ApplyJobUseCase @Inject constructor(private val repository: JobRepository){

    suspend fun applyJob (idUser:Int, idJob:Int):Boolean{
        var user = repository.getUser(idUser)
        if (user.userName.isEmpty() && user.userCareer.isEmpty())
        {
            user = repository.getUserFromDatabase()
        }

        if(!user.jobsApplied.contains(idJob))
            user.jobsApplied.add(idJob)
        else
            return false

        return repository.updateUser(0, user.toDomain())
    }
}