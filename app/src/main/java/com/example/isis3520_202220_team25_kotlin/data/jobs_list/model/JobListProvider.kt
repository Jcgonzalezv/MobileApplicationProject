package com.example.isis3520_202220_team25_kotlin.data.jobs_list.model

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobListProvider @Inject constructor() {

    fun getAllJobs(): List<JobListModel> {
        return jobs
    }

    fun addJobs(pJobs: List<JobListModel>) {
        jobs = pJobs
    }

    fun addAllJobs() {
        jobs = listOf<JobListModel>(
            JobListModel(
                jobName = "Nombre 1",
                jobDescription = "Esta es la descripcion de un trabajo",
                workingHours = "Lunes a Viernes. De 9 AM a 5 PM",
                salary = 10000.00,
                companyName = "Compañía 1",
                companyImage = "https://example.com",
                vacants = 4,
                tags = emptyList(),
                id = 0
            ),
            JobListModel(
                jobName = "Nombre 2",
                jobDescription = "Esta es la descripcion de un trabajo",
                workingHours = "Lunes a Viernes. De 9 AM a 5 PM",
                salary = 10400.50,
                companyName = "Compañía 2",
                companyImage = "https://example.com",
                vacants = 4,
                tags = emptyList(),
                id = 0
            ),
            JobListModel(
                jobName = "Nombre 3",
                jobDescription = "Esta es la descripcion de un trabajo",
                workingHours = "Lunes a Viernes. De 9 AM a 5 PM",
                salary = 40000.00,
                companyName = "Compañía 3",
                companyImage = "https://example.com",
                vacants = 4,
                tags = emptyList(),
                id = 0
            ),
            JobListModel(
                jobName = "Nombre 4",
                jobDescription = "Esta es la descripcion de un trabajo",
                workingHours = "Lunes a Viernes. De 9 AM a 5 PM",
                salary = 5432000.00,
                companyName = "Compañía 4",
                companyImage = "https://example.com",
                vacants = 4,
                tags = emptyList(),
                id = 0
            ),
            JobListModel(
                jobName = "Nombre 5",
                jobDescription = "Esta es la descripcion de un trabajo",
                workingHours = "Lunes a Viernes. De 9 AM a 5 PM",
                salary = 3173900.00,
                companyName = "Compañía 5",
                companyImage = "https://example.com",
                vacants = 4,
                tags = emptyList(),
                id = 0
            ),
            JobListModel(
                jobName = "Nombre 6",
                jobDescription = "Esta es la descripcion de un trabajo",
                workingHours = "Lunes a Viernes. De 9 AM a 5 PM",
                salary = 3163180.00,
                companyName = "Compañía 6",
                companyImage = "https://example.com",
                vacants = 4,
                tags = emptyList(),
                id = 0
            )
        )
    }

    private var jobs: List<JobListModel> = emptyList()
}