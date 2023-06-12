package com.example.isis3520_202220_team25_kotlin.ui.viewmodel.appliedJobs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.isis3520_202220_team25_kotlin.domain.GetAppliedJobsUseCase
import com.example.isis3520_202220_team25_kotlin.domain.GetJobsUseCase
import com.example.isis3520_202220_team25_kotlin.domain.UnApplyJobUseCase
import com.example.isis3520_202220_team25_kotlin.domain.model.Job
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppliedJobsListViewModel @Inject constructor(
    private val getAppliedJobsUseCase: GetAppliedJobsUseCase
): ViewModel() {

    val jobListModel = MutableLiveData<List<Job>>()
    val isLoading = MutableLiveData<Boolean>()

    fun onCreate(){

        viewModelScope.launch(context = Dispatchers.IO) {
            isLoading.postValue(true)
            val result = getAppliedJobsUseCase.getAppliedJobs(0)

            if(!result.isNullOrEmpty()){
                jobListModel.postValue(result)
                isLoading.postValue(false)
            }
            else
            {
                isLoading.postValue(false)
            }
        }
    }
}