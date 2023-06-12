package com.example.isis3520_202220_team25_kotlin.ui.view.job_detail

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.isis3520_202220_team25_kotlin.R
import com.example.isis3520_202220_team25_kotlin.data.network.NetworkObserverManager
import com.example.isis3520_202220_team25_kotlin.databinding.JobDetailBinding
import com.example.isis3520_202220_team25_kotlin.domain.model.Job
import com.example.isis3520_202220_team25_kotlin.ui.view.home.HomeView
import com.example.isis3520_202220_team25_kotlin.ui.view.jobs_list.JobsListView
import com.example.isis3520_202220_team25_kotlin.ui.viewmodel.job_detail.JobDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JobDetailView : AppCompatActivity() {

    private lateinit var binding: JobDetailBinding

    private val jobDetailViewModel: JobDetailViewModel by viewModels()
    private lateinit var builder: AlertDialog.Builder

    //Reference of status conection
    private lateinit var networkObserverManager: NetworkObserverManager

    val CHANNEL_ID = "channelID"
    val CHANNEL_NAME = "channelName"
    val NOTIF_ID = 0

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = JobDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Observer to conection
        networkObserverManager = NetworkObserverManager(applicationContext)

        //hide the message
        binding.noInternetCard.visibility= View.GONE

        //Change the message depending the status of conection
        networkObserverManager.observe(this){
            if (it)
            {
                binding.noInternetCard.visibility= View.GONE
                //diable the buttom if you dont have conection
                binding.applyButton.isEnabled = true
                binding.applyButton.isClickable = true

            }
            else
            {
                //diable the buttom if you dont have conection
                binding.applyButton.isEnabled = false
                binding.applyButton.isClickable = false
                binding.noInternetCard.visibility= View.VISIBLE
            }
        }


        //get stuff from the intent

        jobDetailViewModel.jobDetailModel.observe(this, Observer {
            job = it
            binding.companyNameDetail.text = it.companyName
            binding.jobNameDetail.text = it.jobName
            binding.salaryDetail.text = it.salary.toString()
            binding.workingHoursDetail.text = it.workingHours
            binding.jobDescriptionDetail.setText(it.jobDescription)
            binding.tagsDescription.setText(it.tags.toString())
            binding.contactDescription.setText(it.companyName)
            binding.vacantsDetail.setText(it.vacants.toString())
            Glide.with(binding.companyImageDetail).load(it.companyImage).into(binding.companyImageDetail)

            createNotifChannel()

            val intent = Intent(this@JobDetailView, JobsListView::class.java)
            val pendingIntent = TaskStackBuilder.create(this).run {
                addNextIntentWithParentStack(intent)
                getPendingIntent(0, PendingIntent.FLAG_MUTABLE)
            }

            val notif = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("AviJobs")
                .setContentText("You have applied for " + job.jobName + "!")
                .setSmallIcon(R.drawable.ic_info)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()

            val notif2 = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("AviJobs")
                .setContentText("You have been removed from " + job.jobName + "!")
                .setSmallIcon(R.drawable.ic_info)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()

            val notifManager = NotificationManagerCompat.from(this)

            binding.applyButton.setOnClickListener{
                notifManager.notify(NOTIF_ID, notif)
                ApplyToJob(job.id)
            }

            binding.unapplyButton.setOnClickListener {
                notifManager.notify(NOTIF_ID, notif2)
                UnApplyJob(job.id)
            }
        })

        val jobId:Int = intent.getIntExtra("id", 0)

        jobDetailViewModel.OnCreate(jobId)

        binding.applyButton2.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }



        if(intent.getBooleanExtra("button", true))
        {
            binding.applyButton.visibility = View.VISIBLE
            binding.unapplyButton.visibility = View.GONE
        }
        else
        {
            binding.applyButton.visibility = View.GONE
            binding.unapplyButton.visibility = View.VISIBLE
        }

        builder = AlertDialog.Builder(this)
        jobDetailViewModel.resultApply.observe(this, Observer{
            //create dialog
            if (it) {
                builder.setTitle("Apply Job")
                    .setMessage("You applied successfully to " + job.jobName + "!")
                    .setCancelable(true)
                    .setNeutralButton("Close") { dialogInterface, it ->
                    }.show()
            } else {
                builder.setTitle("Apply Job")
                    .setMessage("An error occurred when applying to " + job.jobName + " please check your connection or that you haven't applied yet.")
                    .setCancelable(true)
                    .setNeutralButton("Close") { dialogInterface, it ->
                    }.show()
            }
        })

        jobDetailViewModel.resultUnApply.observe(this, Observer{
            //create dialog
            if (it) {
                builder.setTitle("UnApply Job")
                    .setMessage("You unapplied successfully to " + job.jobName + "!")
                    .setCancelable(true)
                    .setNeutralButton("Close") { dialogInterface, it ->
                    }.show()
            } else {
                builder.setTitle("UnApply Job")
                    .setMessage("An error occurred when unapplying to " + job.jobName + " please check your connection or that you have applied before.")
                    .setCancelable(true)
                    .setNeutralButton("Close") { dialogInterface, it ->
                    }.show()
            }
        })
    }

    private fun createNotifChannel()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                lightColor = Color.BLUE
                enableLights(true)
            }
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun ApplyToJob(id:Int)
    {
        //apply
        jobDetailViewModel.ApplyJob(id)
    }

    private fun UnApplyJob(id: Int)
    {
        jobDetailViewModel.unApplyJob(id)
    }
}