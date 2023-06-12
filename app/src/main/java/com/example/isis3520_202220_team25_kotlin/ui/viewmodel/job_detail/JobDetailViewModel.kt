package com.example.isis3520_202220_team25_kotlin.ui.viewmodel.job_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.isis3520_202220_team25_kotlin.data.jobs_list.model.JobListModel
import com.example.isis3520_202220_team25_kotlin.domain.ApplyJobUseCase
import com.example.isis3520_202220_team25_kotlin.domain.JobDetailUseCase
import com.example.isis3520_202220_team25_kotlin.domain.UnApplyJobUseCase
import com.example.isis3520_202220_team25_kotlin.domain.model.Job
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobDetailViewModel @Inject constructor(
    private val jobDetailUseCase: JobDetailUseCase,
    private val applyJobUseCase: ApplyJobUseCase,
    private val unApplyJobUseCase: UnApplyJobUseCase
) : ViewModel() {

    val jobDetailModel = MutableLiveData<Job>()
    val isLoading = MutableLiveData<Boolean>()
    val resultApply = MutableLiveData<Boolean>()
    val resultUnApply = MutableLiveData<Boolean>()

    fun OnCreate(pos: Int) {
        viewModelScope.launch(context = Dispatchers.IO) {
            isLoading.postValue(true)
            val result = jobDetailUseCase.getDetailJob(pos)

            if (!result?.jobName.equals("")) {
                jobDetailModel.postValue(result)
                isLoading.postValue(false)
            } else {
                //getJobs()
                isLoading.postValue(false)
            }
        }
    }

    fun ApplyJob(id: Int)
    {
        viewModelScope.launch(context = Dispatchers.IO) {
            resultApply.postValue(applyJobUseCase.applyJob(0, id))
        }
    }

    fun unApplyJob(id:Int)
    {
        viewModelScope.launch(context = Dispatchers.IO) {
            resultUnApply.postValue(unApplyJobUseCase.unApplyJob(0, id))
        }
    }
}