package com.example.isis3520_202220_team25_kotlin.ui.view.jobs_list

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
import com.example.isis3520_202220_team25_kotlin.domain.model.Job
import com.example.isis3520_202220_team25_kotlin.ui.view.CreateJob.CreateJobView
import com.example.isis3520_202220_team25_kotlin.ui.view.home.HomeView
import com.example.isis3520_202220_team25_kotlin.ui.view.job_detail.JobDetailView
import com.example.isis3520_202220_team25_kotlin.ui.view.jobs_list.adapter.JobsListAdapter
import com.example.isis3520_202220_team25_kotlin.ui.view.user.DisplayUserView
import com.example.isis3520_202220_team25_kotlin.ui.viewmodel.jobs_list.JobsListViewModel
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class JobsListView : AppCompatActivity() {

    private lateinit var binding:ActivityJobsListBinding

    private val jobsListViewModel: JobsListViewModel by viewModels()

    //Reference of status conection
    private lateinit var networkObserverManager: NetworkObserverManager

    var nextId:Int = 0

    lateinit var tempList : ArrayList<Job>
    lateinit var newList : ArrayList<Job>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityJobsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        newList = arrayListOf<Job>()
        tempList = arrayListOf<Job>()

        jobsListViewModel.jobListModel.observe(this, Observer {
            nextId = it.size
            newList.addAll(it)
            tempList.addAll(it)
            initRecyclerView(tempList)
        })

        jobsListViewModel.isLoading.observe(this, Observer{
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

        jobsListViewModel.onCreate()

        binding.bottomNavigationView.selectedItemId = R.id.list

        binding.bottomNavigationView.setOnItemSelectedListener(object: NavigationBarView.OnItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                if(item.itemId == R.id.list)
                {
                    return true
                }
                else if(item.itemId == R.id.create){
                    val intent = Intent(this@JobsListView, CreateJobView::class.java)
                    intent.putExtra("id", nextId)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return true;
                }
                else if(item.itemId == R.id.profile)
                {
                    val intent = Intent(this@JobsListView, DisplayUserView::class.java)
                    startActivity(intent)
                    overridePendingTransition(0,0)
                    return true;
                }
                else if(item.itemId == R.id.home)
                {
                    val intent = Intent(this@JobsListView, HomeView::class.java)
                    startActivity(intent)
                    overridePendingTransition(0,0)
                    return true;
                }
                else
                    return false;
            }
        })

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
    }

    fun initRecyclerView(jobs:List<Job>){
        binding.recyclerJobsList.layoutManager = LinearLayoutManager(this)
        binding.recyclerJobsList.adapter = JobsListAdapter(jobs) { onItemSelected(it) }
    }

    fun onItemSelected(pos:Int){
        val intent = Intent(this@JobsListView, JobDetailView::class.java)
        intent.putExtra("id", pos)
        intent.putExtra("button", true)
        startActivity(intent)
        overridePendingTransition(0,0)
    }

}
