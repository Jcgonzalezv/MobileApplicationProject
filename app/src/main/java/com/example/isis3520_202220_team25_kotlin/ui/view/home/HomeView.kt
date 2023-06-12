package com.example.isis3520_202220_team25_kotlin.ui.view.home

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
import com.bumptech.glide.Glide
import com.datadog.android.Datadog
import com.datadog.android.DatadogSite
import com.datadog.android.core.configuration.Configuration
import com.datadog.android.core.configuration.Credentials
import com.datadog.android.privacy.TrackingConsent
import com.datadog.android.rum.GlobalRum
import com.datadog.android.rum.RumMonitor
import com.datadog.android.rum.tracking.ActivityViewTrackingStrategy
import com.example.isis3520_202220_team25_kotlin.R
import com.example.isis3520_202220_team25_kotlin.data.MyCache
import com.example.isis3520_202220_team25_kotlin.data.network.NetworkObserverManager
import com.example.isis3520_202220_team25_kotlin.databinding.ActivityHomeBinding
import com.example.isis3520_202220_team25_kotlin.databinding.ActivityJobsListBinding
import com.example.isis3520_202220_team25_kotlin.domain.model.Job
import com.example.isis3520_202220_team25_kotlin.ui.view.CreateJob.CreateJobView
import com.example.isis3520_202220_team25_kotlin.ui.view.job_detail.JobDetailView
import com.example.isis3520_202220_team25_kotlin.ui.view.jobs_list.JobsListView
import com.example.isis3520_202220_team25_kotlin.ui.view.jobs_list.adapter.JobsListAdapter
import com.example.isis3520_202220_team25_kotlin.ui.view.user.DisplayUserView
import com.example.isis3520_202220_team25_kotlin.ui.viewmodel.home.HomeViewModel
import com.example.isis3520_202220_team25_kotlin.ui.viewmodel.jobs_list.JobsListViewModel
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ViewWithFragmentComponent
import java.util.*

@AndroidEntryPoint
class HomeView : AppCompatActivity() {

    private lateinit var binding:ActivityHomeBinding

    private val jobsListViewModel: HomeViewModel by viewModels()

    private lateinit var networkObserverManager : NetworkObserverManager

    var nextId:Int = 0

    lateinit var tempList : ArrayList<Job>
    lateinit var newList : ArrayList<Job>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        newList = arrayListOf<Job>()
        tempList = arrayListOf<Job>()

        jobsListViewModel.jobListModel.observe(this, Observer {
            nextId = it.size
            newList.addAll(it)
            tempList.addAll(it)
            initRecyclerView(tempList)
        })

        jobsListViewModel.userActual.observe(this, Observer {
            if (it != null) {
                binding.theUserName.text = it.userName
                var imageBitmap = MyCache.instance.retrieveBitmapFromCache("image2")
                if(imageBitmap == null)
                    Glide.with(binding.imageViewUser).load(it.userImage).into(binding.imageViewUser)
                else
                    binding.imageViewUser.setImageBitmap(imageBitmap)
            }
        })

        jobsListViewModel.isLoading.observe(this, Observer{
            binding.progressBar.isVisible = it
        })

        networkObserverManager = NetworkObserverManager(applicationContext)

        binding.noInternetCard.visibility = View.GONE

        networkObserverManager.observe(this){
            if(it)
            {
                binding.noInternetCard.visibility = View.GONE
            } else{
                binding.noInternetCard.visibility = View.VISIBLE
            }
        }

        jobsListViewModel.onCreate()

        binding.bottomNavigationView.selectedItemId = R.id.home

        binding.bottomNavigationView.setOnItemSelectedListener(object :
            NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                if (item.itemId == R.id.home) {
                    return true;
                }
                else if (item.itemId == R.id.list) {
                    val intent = Intent(this@HomeView, JobsListView::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return true;
                } else if (item.itemId == R.id.create) {
                    val intent = Intent(this@HomeView, CreateJobView::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return true
                }
                else if (item.itemId == R.id.profile) {
                    val intent = Intent(this@HomeView, DisplayUserView::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return true
                }
                else
                    return true;
            }
        })

        //dataperro

        val clientToken = "pub399c4333151157703946d10c17ee5f16"
        val applicationId = "bc9d1e98-ceed-4fa5-9f41-f38152fd018c"

        val environmentName = "Sandbox"
        val appVariantName = "NO_VARIANT"

        val configuration = Configuration.Builder(
            rumEnabled = true,
            crashReportsEnabled = true,
            logsEnabled = true,
            tracesEnabled = true,
        )
            .trackInteractions()
            .trackLongTasks(15)
            .useSite(DatadogSite.US1)
            .build()
        val credentials = Credentials(clientToken,environmentName,appVariantName,applicationId)
        Datadog.initialize(this, credentials, configuration, TrackingConsent.GRANTED)
        GlobalRum.registerIfAbsent(RumMonitor.Builder().build())
    }
    fun initRecyclerView(jobs:List<Job>){
        binding.recyclerJobsList.layoutManager = LinearLayoutManager(this)
        binding.recyclerJobsList.adapter = JobsListAdapter(jobs) { onItemSelected(it) }
    }

    fun onItemSelected(pos:Int){
        val intent = Intent(this@HomeView, JobDetailView::class.java)
        intent.putExtra("id", pos)
        startActivity(intent)
        overridePendingTransition(0,0)
    }
}
