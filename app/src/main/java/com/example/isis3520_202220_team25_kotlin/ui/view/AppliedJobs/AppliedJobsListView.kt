package com.example.isis3520_202220_team25_kotlin.ui.view.AppliedJobs

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.isis3520_202220_team25_kotlin.R
import com.example.isis3520_202220_team25_kotlin.data.network.NetworkObserverManager
import com.example.isis3520_202220_team25_kotlin.databinding.ActivityJobsListBinding
import com.example.isis3520_202220_team25_kotlin.databinding.AppliedJobListBinding
import com.example.isis3520_202220_team25_kotlin.domain.model.Job
import com.example.isis3520_202220_team25_kotlin.ui.view.CreateJob.CreateJobView
import com.example.isis3520_202220_team25_kotlin.ui.view.home.HomeView
import com.example.isis3520_202220_team25_kotlin.ui.view.job_detail.JobDetailView
import com.example.isis3520_202220_team25_kotlin.ui.view.jobs_list.adapter.JobsListAdapter
import com.example.isis3520_202220_team25_kotlin.ui.view.user.DisplayUserView
import com.example.isis3520_202220_team25_kotlin.ui.viewmodel.appliedJobs.AppliedJobsListViewModel
import com.example.isis3520_202220_team25_kotlin.ui.viewmodel.jobs_list.JobsListViewModel
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AppliedJobsListView : AppCompatActivity() {

    private lateinit var binding:AppliedJobListBinding

    private val appliedJobsListView: AppliedJobsListViewModel by viewModels()

    //Reference of status conection
    private lateinit var networkObserverManager: NetworkObserverManager

    var nextId:Int = 0

    lateinit var tempList : ArrayList<Job>
    lateinit var newList : ArrayList<Job>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = AppliedJobListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        newList = arrayListOf<Job>()
        tempList = arrayListOf<Job>()

        appliedJobsListView.jobListModel.observe(this, Observer {
            nextId = it.size
            newList.addAll(it)
            tempList.addAll(it)
            initRecyclerView(tempList)
        })

        appliedJobsListView.isLoading.observe(this, Observer{
            binding.progressBar.isVisible = it
        })

        //Observer to conection
        networkObserverManager = NetworkObserverManager(applicationContext)

        //hide the message
        binding.noInternetCard.visibility= View.GONE

        //Change the message depending the status of conection
        networkObserverManager.observe(this){
            if (it)
            {
                binding.noInternetCard.visibility=View.GONE
            }
            else
            {
                binding.noInternetCard.visibility=View.VISIBLE
            }
        }

        appliedJobsListView.onCreate()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                tempList.clear()

                val searchText = newText!!.lowercase(Locale.getDefault())

                if(searchText.isNotEmpty()){
                    for (job in newList) {
                        if(job.jobName.lowercase().contains(searchText)){
                            tempList.add(job)
                        }
                        for(string in job.tags){
                            if(string.lowercase().contains(searchText)){
                                if(!tempList.contains(job)){
                                    tempList.add(job)
                                }
                            }
                        }
                    }
                }
                else if(searchText.isEmpty()){
                    tempList.clear()
                    tempList.addAll(newList)
                }

                binding.recyclerJobsList.adapter!!.notifyDataSetChanged()
                return true
            }

        })

        binding.button4.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    fun initRecyclerView(jobs:List<Job>){
        binding.recyclerJobsList.layoutManager = LinearLayoutManager(this)
        binding.recyclerJobsList.adapter = JobsListAdapter(jobs) { onItemSelected(it) }
    }

    fun onItemSelected(pos:Int){
        val intent = Intent(this@AppliedJobsListView, JobDetailView::class.java)
        intent.putExtra("id", pos)
        intent.putExtra("button", false)
        startActivity(intent)
        overridePendingTransition(0,0)
    }

}
