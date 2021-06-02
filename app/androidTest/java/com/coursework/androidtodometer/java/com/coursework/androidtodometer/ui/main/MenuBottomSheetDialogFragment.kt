package com.coursework.androidtodometer.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.coursework.androidtodometer.R
import com.coursework.androidtodometer.databinding.MenuBottomSheetDialogFragmentBinding
import com.coursework.androidtodometer.model.Project
import com.coursework.androidtodometer.ui.adapter.ProjectsAdapter
import com.coursework.androidtodometer.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MenuBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private var _binding: MenuBottomSheetDialogFragmentBinding? = null
    private val binding get() = _binding!!

    private val projectsAdapter = ProjectsAdapter()

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MenuBottomSheetDialogFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addProjectButton.setOnClickListener {
            dismiss()
            findNavController().navigate(R.id.addProjectFragment)
        }

        binding.projectsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.projectsRecyclerView.adapter = projectsAdapter
        mainViewModel.projects.observe(
            viewLifecycleOwner,
            {
                binding.noProjects.isVisible = it.isNullOrEmpty()
                projectsAdapter.submitList(it)
            }
        )
        mainViewModel.projectSelectedId.observe(
            viewLifecycleOwner,
            {
                projectsAdapter.projectSelected = it
                projectsAdapter.notifyDataSetChanged()
            }
        )
        projectsAdapter.projectClickListener = object : ProjectsAdapter.ProjectClickListener {
            override fun onProjectClick(project: Project) {
                mainViewModel.setProjectSelected(project.id)
                dismiss()
            }
        }
    }
}
