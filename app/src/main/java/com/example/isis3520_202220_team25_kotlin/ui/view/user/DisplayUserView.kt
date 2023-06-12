package com.example.isis3520_202220_team25_kotlin.ui.view.user

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.isis3520_202220_team25_kotlin.R
import com.example.isis3520_202220_team25_kotlin.data.MyCache
import com.example.isis3520_202220_team25_kotlin.data.network.NetworkObserverManager
import com.example.isis3520_202220_team25_kotlin.databinding.JobProfileBinding
import com.example.isis3520_202220_team25_kotlin.ui.view.AppliedJobs.AppliedJobsListView
import com.example.isis3520_202220_team25_kotlin.ui.view.CreateJob.CreateJobView
import com.example.isis3520_202220_team25_kotlin.ui.view.home.HomeView
import com.example.isis3520_202220_team25_kotlin.ui.view.jobs_list.JobsListView
import com.example.isis3520_202220_team25_kotlin.ui.viewmodel.user.UserDisplayViewModel
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DisplayUserView : AppCompatActivity() {
    private val REQUEST_CAMERA = 100
    var photo: Uri? = null
    private lateinit var binding: JobProfileBinding
    private val userDisplayViewModel: UserDisplayViewModel by viewModels()
    //Reference of status conection
    private lateinit var networkObserverManager: NetworkObserverManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = JobProfileBinding.inflate(layoutInflater)
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
            }
            else
            {
                binding.noInternetCard.visibility= View.VISIBLE
            }
        }
        userDisplayViewModel.userActual.observe(this, Observer {
            binding.userName.text = it.userName
            binding.userAge.text = it.userAge.toString()
            binding.userCarrier.text = it.userCareer
            binding.userGender.text = it.userGender
            binding.userPreference.text = it.userJobPreference
            binding.userSemester.text = it.userSemester
            binding.userPreference.text = it.userJobPreference
            if(MyCache.instance.retrieveBitmapFromCache("image2") == null)
                Glide.with(binding.userImage).load(it.userImage).into(binding.userImage)
        })
        userDisplayViewModel.onCreate()

        binding.bottomNavigationView.selectedItemId = R.id.profile

        binding.userImage.setOnClickListener{
            abreCamara_click()
        }

        binding.bottomNavigationView.setOnItemSelectedListener(object :
            NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                if (item.itemId == R.id.profile) {
                    return true;
                }
                else if (item.itemId == R.id.list) {
                    val intent = Intent(this@DisplayUserView, JobsListView::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return true;
                } else if (item.itemId == R.id.create) {
                    val intent = Intent(this@DisplayUserView, CreateJobView::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return true
                }
                else if (item.itemId == R.id.home) {
                    val intent = Intent(this@DisplayUserView, HomeView::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return true
                }
                else
                    return false;
            }
        })

        binding.button2.setOnClickListener {
            val intent = Intent(this@DisplayUserView, AppliedJobsListView::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        //obtiene la imagen remplaza messi kimochi
        var imageBitMap = MyCache.instance.retrieveBitmapFromCache("image2")
        if (imageBitMap!=null)
        {
            //setea la imagen
            binding.userImage.setImageBitmap(imageBitMap)
        }

    }

    // DETECTAR CUANDO SE PULSA LA CAMARA
    private fun abreCamara_click() {
        //binding.userImage.setOnClickListener(){
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
            {
                if(checkSelfPermission(android.Manifest.permission.CAMERA)==PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED)
                {
                    val permisosCamara = arrayOf(android.Manifest.permission.CAMERA,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permisosCamara,REQUEST_CAMERA)
                }
                else openCamnera()

            }
            else openCamnera()


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CAMERA ->{
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    openCamnera()
                }
                else
                    Toast.makeText(applicationContext, "You Can't open the camera",Toast.LENGTH_SHORT).show()
            }

        }
    }
    // abre la camara del telefono
    private fun openCamnera(){
        val value = ContentValues()
        value.put(MediaStore.Images.Media.TITLE, "New image")
        photo = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,value)
        val camaraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT,photo)
        startActivityForResult(camaraIntent,REQUEST_CAMERA)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CAMERA && this.photo != null)
        {
            binding.userImage.setImageURI(this.photo)
            //guarda la imagen en cache con la key
            MyCache.instance.saveBitmapToCache("image2",MediaStore.Images.Media.getBitmap(this.contentResolver, this.photo))

        }
    }
}