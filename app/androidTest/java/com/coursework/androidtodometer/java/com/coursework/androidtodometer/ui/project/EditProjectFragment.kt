package com.coursework.androidtodometer.ui.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialFade
import com.coursework.androidtodometer.R
import com.coursework.androidtodometer.databinding.EditProjectFragmentBinding
import com.coursework.androidtodometer.extensions.clearError
import com.coursework.androidtodometer.extensions.hideSoftKeyboard
import com.coursework.androidtodometer.model.Project
import com.coursework.androidtodometer.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProjectFragment : Fragment() {
    private lateinit var binding: EditProjectFragmentBinding

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.edit_project_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editProjectButton.apply {
            postDelayed(
                {
                    val transition = MaterialFade().apply {
                        duration = resources.getInteger(R.integer.fade_transition_duration).toLong()
                    }
                    TransitionManager.beginDelayedTransition(
                        requireActivity().findViewById(android.R.id.content),
                        transition
                    )
                    visibility = View.VISIBLE
                },
                resources.getInteger(R.integer.fade_transition_start_delay).toLong()
            )
            setOnClickListener {
                if (validateProjectName()) {
                    editProject()
                }
            }
        }
        mainViewModel.projectSelected.observe(
            viewLifecycleOwner,
            {
                binding.project = it
            }
        )
    }

    private fun validateProjectName(): Boolean {
        binding.projectNameInput.clearError()
        return if (binding.projectNameEditText.text.isNullOrBlank()) {
            binding.projectNameInput.error = getString(R.string.must_be_not_empty)
            false
        } else true
    }

    private fun editProject() {
        mainViewModel.projectSelected.value?.let {
            mainViewModel.updateProject(
                Project(
                    it.id,
                    binding.projectNameEditText.text.toString(),
                    binding.projectDescriptionEditText.text.toString()
                )
            )
            activity?.hideSoftKeyboard()
            findNavController().navigateUp()
        }
    }
}
