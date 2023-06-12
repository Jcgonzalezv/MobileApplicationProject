package com.example.isis3520_202220_team25_kotlin.ui.viewmodel.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.isis3520_202220_team25_kotlin.domain.GetJobsUseCase
import com.example.isis3520_202220_team25_kotlin.domain.GetRecomendationJob
import com.example.isis3520_202220_team25_kotlin.domain.GetUserUseCase
import com.example.isis3520_202220_team25_kotlin.domain.model.Job
import com.example.isis3520_202220_team25_kotlin.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRecomendationJob: GetRecomendationJob,
    private val getUserUseCase: GetUserUseCase
): ViewModel() {

    val userActual = MutableLiveData<User?>()
    val jobListModel = MutableLiveData<List<Job>>()
    val isLoading = MutableLiveData<Boolean>()

    fun onCreate(){

        viewModelScope.launch(context = Dispatchers.IO) {
            val result_user = getUserUseCase.getUserJob(0)
            if (!result_user?.userName.equals("")) {
                userActual.postValue(result_user)
                val result = result_user?.let { getRecomendationJob.GetRecomendations(it.userJobPreference) }

                if(!result.isNullOrEmpty()){
                    jobListModel.postValue(result)
                    isLoading.postValue(false)
                }
                isLoading.postValue(false)
            }
            else{
                isLoading.postValue(false)
            }
        }
    }


}