package com.example.isis3520_202220_team25_kotlin.ui.viewmodel.jobs_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.isis3520_202220_team25_kotlin.domain.GetJobsUseCase
import com.example.isis3520_202220_team25_kotlin.domain.model.Job
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobsListViewModel @Inject constructor(
    private val getJobsUseCase:GetJobsUseCase
): ViewModel() {

    val jobListModel = MutableLiveData<List<Job>>()
    val isLoading = MutableLiveData<Boolean>()

    fun onCreate(){

        viewModelScope.launch(context = Dispatchers.IO) {
            isLoading.postValue(true)
            val result = getJobsUseCase()

            if(!result.isNullOrEmpty()){
                jobListModel.postValue(result)
                isLoading.postValue(false)
            }
        }
    }


}