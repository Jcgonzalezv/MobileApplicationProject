package com.example.isis3520_202220_team25_kotlin.ui.viewmodel.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.isis3520_202220_team25_kotlin.data.User.UserModel
import com.example.isis3520_202220_team25_kotlin.data.jobs_list.model.JobListModel
import com.example.isis3520_202220_team25_kotlin.data.jobs_list.model.JobListProvider
import com.example.isis3520_202220_team25_kotlin.domain.GetJobsUseCase
import com.example.isis3520_202220_team25_kotlin.domain.GetUserUseCase
import com.example.isis3520_202220_team25_kotlin.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDisplayViewModel
@Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    val userActual = MutableLiveData<User>()
    val isLoading = MutableLiveData<Boolean>()


    fun onCreate() {
        viewModelScope.launch(context = Dispatchers.IO) {
            isLoading.postValue(true)
            val result = getUserUseCase.getUserJob(0)
            if (!result?.userName.equals("")) {
                userActual.postValue(result)
                isLoading.postValue(false)
            }
            else{
                isLoading.postValue(false)
            }
        }
    }

}