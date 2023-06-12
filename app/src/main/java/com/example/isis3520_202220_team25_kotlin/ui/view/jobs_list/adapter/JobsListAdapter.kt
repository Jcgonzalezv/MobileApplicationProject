package com.example.isis3520_202220_team25_kotlin.ui.view.jobs_list.adapter

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.isis3520_202220_team25_kotlin.data.jobs_list.model.JobListModel
import com.example.isis3520_202220_team25_kotlin.R
import com.example.isis3520_202220_team25_kotlin.domain.model.Job

class JobsListAdapter(private val jobsList: List<Job>, private val onClickListener:(Int) -> Unit) :
    RecyclerView.Adapter<JobsListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsListViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return JobsListViewHolder(layoutInflater.inflate(R.layout.item_jobs_list, parent, false))
    }

    override fun onBindViewHolder(holder: JobsListViewHolder, position: Int) {
        val item = jobsList[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = jobsList.size

}