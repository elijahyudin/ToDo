package com.coursework.androidtodometer.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.coursework.androidtodometer.databinding.ItemProjectBinding
import com.coursework.androidtodometer.model.Project

class ProjectsAdapter : ListAdapter<Project, ProjectsAdapter.ProjectViewHolder>(DIFF_CALLBACK) {

    var projectClickListener: ProjectClickListener? = null

    var projectSelected: Int? = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        return ProjectViewHolder(
            ItemProjectBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            parent.context
        )
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class ProjectViewHolder(private val binding: ItemProjectBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(project: Project) {
            binding.project = project
            binding.projectItem.isSelected = projectSelected == project.id
            binding.projectName.isSelected = projectSelected == project.id
            binding.projectItem.setOnClickListener {
                projectClickListener?.onProjectClick(project)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Project>() {
            override fun areItemsTheSame(oldProject: Project, newProject: Project): Boolean {
                return oldProject.id == newProject.id
            }

            override fun areContentsTheSame(oldProject: Project, newProject: Project): Boolean {
                return oldProject == newProject
            }
        }
    }

    interface ProjectClickListener {
        fun onProjectClick(project: Project)
    }
}
