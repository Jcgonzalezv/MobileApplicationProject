package com.example.isis3520_202220_team25_kotlin.ui.viewmodel.create_job

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.isis3520_202220_team25_kotlin.data.jobs_list.model.JobListModel
import com.example.isis3520_202220_team25_kotlin.domain.GetLatestIdUseCase
import com.example.isis3520_202220_team25_kotlin.domain.JobCreateUseCase
import com.example.isis3520_202220_team25_kotlin.domain.model.Job
import com.example.isis3520_202220_team25_kotlin.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateJobViewModel
@Inject constructor(
    private val jobCreateUseCase: JobCreateUseCase,
    private val getLatestIdUseCase: GetLatestIdUseCase
    ) : ViewModel() {

    val result = MutableLiveData<Boolean>()
    val id = MutableLiveData<Int?>()

    fun createJob(id: Int, job: Job)
    {
        viewModelScope.launch(context = Dispatchers.IO) {
            result.postValue(jobCreateUseCase.CreateJob(id, job))
        }
    }

    fun getLatestId() {
        viewModelScope.launch(context = Dispatchers.IO) {
            id.postValue(getLatestIdUseCase.getId())
        }
    }
}