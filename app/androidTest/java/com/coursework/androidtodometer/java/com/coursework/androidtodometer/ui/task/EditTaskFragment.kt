package com.coursework.androidtodometer.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialFade
import com.coursework.androidtodometer.R
import com.coursework.androidtodometer.databinding.EditTaskFragmentBinding
import com.coursework.androidtodometer.extensions.clearError
import com.coursework.androidtodometer.extensions.hideSoftKeyboard
import com.coursework.androidtodometer.model.Tag
import com.coursework.androidtodometer.model.Task
import com.coursework.androidtodometer.ui.adapter.TagAdapter
import com.coursework.androidtodometer.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditTaskFragment : Fragment() {
    private lateinit var binding: EditTaskFragmentBinding

    private val mainViewModel by viewModels<MainViewModel>()

    private val args: EditTaskFragmentArgs by navArgs()

    private var task: Task? = null

    private var tag: Tag? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.edit_task_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editTaskButton.apply {
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
                if (validateTaskName()) {
                    editTask()
                }
            }
        }
        val adapter = TagAdapter(
            requireContext(),
            R.layout.item_tag_dropdown,
            enumValues()
        )
        binding.tagDropdown.setAdapter(adapter)
        binding.tagDropdown.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                tag = enumValues<Tag>()[position]
            }
        mainViewModel.getTask(args.taskId).observe(
            viewLifecycleOwner,
            {
                task = it
                binding.task = task
                task?.tag?.let {
                    binding.tagDropdown.setText(it.description, false)
                }
            }
        )
    }

    private fun validateTaskName(): Boolean {
        binding.taskNameInput.clearError()
        return if (binding.taskNameEditText.text.isNullOrBlank()) {
            binding.taskNameInput.error = getString(R.string.must_be_not_empty)
            false
        } else true
    }

    private fun editTask() {
        task?.let {
            mainViewModel.updateTask(
                Task(
                    it.id,
                    binding.taskNameEditText.text.toString(),
                    binding.taskDescriptionEditText.text.toString(),
                    it.state,
                    it.projectId,
                    tag
                )
            )
            activity?.hideSoftKeyboard()
            findNavController().navigateUp()
        }
    }
}
