package com.example.isis3520_202220_team25_kotlin.ui.view.jobs_list.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.isis3520_202220_team25_kotlin.data.jobs_list.model.JobListModel
import com.example.isis3520_202220_team25_kotlin.databinding.ItemJobsListBinding
import com.example.isis3520_202220_team25_kotlin.domain.model.Job

class JobsListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemJobsListBinding.bind(view)

    fun render(jobListModel: Job, onClickListener: (Int) -> Unit) {
        binding.tvJobName.text = jobListModel.jobName
        binding.tvWorkingHours.text = jobListModel.workingHours
        binding.tvSalary.text = jobListModel.salary.toString()

        Glide.with(binding.companyImage).load(jobListModel.companyImage).into(binding.companyImage)

        itemView.setOnClickListener { onClickListener(jobListModel.id)
        }

        binding.button.setOnClickListener { onClickListener(jobListModel.id) }
    }
}