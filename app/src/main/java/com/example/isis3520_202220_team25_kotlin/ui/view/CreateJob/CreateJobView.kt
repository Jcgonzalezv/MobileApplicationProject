package com.example.isis3520_202220_team25_kotlin.ui.view.CreateJob

import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.isis3520_202220_team25_kotlin.R
import com.example.isis3520_202220_team25_kotlin.data.MyCache
import com.example.isis3520_202220_team25_kotlin.data.database.entities.JobEntity
import com.example.isis3520_202220_team25_kotlin.data.network.NetworkObserverManager
import com.example.isis3520_202220_team25_kotlin.databinding.JobCreateBinding
import com.example.isis3520_202220_team25_kotlin.domain.model.toDomain
import com.example.isis3520_202220_team25_kotlin.ui.view.home.HomeView
import com.example.isis3520_202220_team25_kotlin.ui.view.jobs_list.JobsListView
import com.example.isis3520_202220_team25_kotlin.ui.view.user.DisplayUserView
import com.example.isis3520_202220_team25_kotlin.ui.viewmodel.create_job.CreateJobViewModel
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class CreateJobView : AppCompatActivity() {
    private lateinit var binding: JobCreateBinding
    private val createJobViewModel: CreateJobViewModel by viewModels()
    private lateinit var networkObserverManager : NetworkObserverManager
    private lateinit var builder: AlertDialog.Builder
    private val IMAGE_REQUEST_CODE = 100
    private var currentId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = JobCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.selectedItemId = R.id.create

        binding.bottomNavigationView.setOnItemSelectedListener(object :
            NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                if (item.itemId == R.id.create) {
                    return true
                }
                else if (item.itemId == R.id.list) {
                    val intent = Intent(this@CreateJobView, JobsListView::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return true;
                }
                else if (item.itemId == R.id.profile)
                {
                    val intent = Intent(this@CreateJobView, DisplayUserView::class.java)
                    startActivity(intent)
                    overridePendingTransition(0,0)
                    return true;
                }
                else if (item.itemId == R.id.home)
                {
                    val intent = Intent(this@CreateJobView, HomeView::class.java)
                    startActivity(intent)
                    overridePendingTransition(0,0)
                    return true;
                }
                else
                    return false;
            }
        })

        binding.createJob.setOnClickListener { getDataToCreate() }

        binding.nameEditText.filters = arrayOf<InputFilter>(LengthFilter(30))
        binding.descriptionEditText.filters = arrayOf<InputFilter>(LengthFilter(60))
        binding.workingEditText.filters = arrayOf<InputFilter>(LengthFilter(30))
        binding.salaryEditText.filters = arrayOf<InputFilter>(LengthFilter(15))
        binding.vacantsEditText.filters = arrayOf<InputFilter>(LengthFilter(4))
        binding.tagEditText.filters = arrayOf<InputFilter>(LengthFilter(40))

        networkObserverManager = NetworkObserverManager(applicationContext)

        binding.noInternetCard.visibility = View.GONE

        networkObserverManager.observe(this){
            if(it)
            {
                binding.noInternetCard.visibility = View.GONE
                binding.createJob.isEnabled = true
                binding.createJob.isClickable = true
            } else{
                binding.noInternetCard.visibility = View.VISIBLE
                binding.createJob.isEnabled = false
                binding.createJob.isClickable = false
            }
        }

        builder = AlertDialog.Builder(this)

        createJobViewModel.result.observe(this, Observer{
            //create dialog
            if (it) {
                builder.setTitle("Create Job")
                    .setMessage("Job was created succesfully. Create another one?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialogInterface, it ->
                        clearInsertedData()
                    }
                    .setNegativeButton("Go Home") { dialogInterface, it ->
                        val intent = Intent(this@CreateJobView, HomeView::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                    }.show()
            } else {
                builder.setTitle("Create Job")
                    .setMessage("An error occurred when creating the job. Please check the data or try again")
                    .setCancelable(true)
                    .setPositiveButton("Close") { dialogInterface, it ->

                    }
                    .setNegativeButton("Clear data") { dialogInterface, it ->
                        clearInsertedData()
                    }.show()
            }
        })

        createJobViewModel.id.observe(this, Observer{
            if (it != null) {
                currentId = it
            }
        })

        var imageName = MyCache.instance.retrieveStringFromCache("image")
        if(imageName != null)
        {

            binding.imageSelectedText.text = imageName
            binding.imageView.setImageBitmap(MyCache.instance.retrieveBitmapFromCache(imageName))
        }

        if(binding.imageSelectedText.text.equals(""))
        {
            binding.imageView.visibility = View.GONE
            binding.imageSelectedText.visibility = View.GONE
        }
        binding.selectText.setOnClickListener {
            pickImageGallery()
        }

        createJobViewModel.getLatestId()
    }


    private fun getDataToCreate(){
        //call the viewModel
        try {
            val job = JobEntity(binding.nameEditText.text.toString(), binding.descriptionEditText.text.toString(),
                binding.workingEditText.text.toString(), binding.salaryEditText.text.toString().toDouble(),"Company1",
                "https://i.pinimg.com/474x/9b/47/a0/9b47a023caf29f113237d61170f34ad9.jpg",binding.vacantsEditText.text.toString().toInt(), binding.tagEditText.toString(), currentId)

            createJobViewModel.createJob(currentId,job.toDomain())
        }
        catch (e: Exception)
        {
            builder.setTitle("Create Job")
                .setMessage("An error occurred when creating the job. Please check the data or try again")
                .setCancelable(true)
                .setPositiveButton("Close") { dialogInterface, it ->

                }
                .setNegativeButton("Clear data") { dialogInterface, it ->
                    clearInsertedData()
                }.show()
        }
    }

    private fun clearInsertedData()
    {
        binding.nameEditText.setText("")
        binding.descriptionEditText.setText("")
        binding.workingEditText.setText("")
        binding.salaryEditText.setText("")
        binding.vacantsEditText.setText("")
        binding.tagEditText.setText("")
    }

    private fun pickImageGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_REQUEST_CODE){
            var uri = data?.data
            binding.imageView.visibility = View.VISIBLE
            binding.imageSelectedText.visibility = View.VISIBLE
            binding.imageView.setImageURI(uri)
            val fileName = File(uri?.path)
            val name = fileName.name + "."+ fileName.extension
            binding.imageSelectedText.setText(name)

            MyCache.instance.saveStringToCache("image", name)
            MyCache.instance.saveBitmapToCache(name,MediaStore.Images.Media.getBitmap(this.contentResolver, uri))
        }
    }
}