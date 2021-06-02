package com.coursework.androidtodometer.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialFade
import com.coursework.androidtodometer.R
import com.coursework.androidtodometer.databinding.AddTaskFragmentBinding
import com.coursework.androidtodometer.extensions.clearError
import com.coursework.androidtodometer.extensions.hideSoftKeyboard
import com.coursework.androidtodometer.model.Tag
import com.coursework.androidtodometer.model.TaskState
import com.coursework.androidtodometer.ui.adapter.TagAdapter
import com.coursework.androidtodometer.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskFragment : Fragment() {
    private var _binding: AddTaskFragmentBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by viewModels<MainViewModel>()

    private var tag = Tag.OTHER

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddTaskFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createButton.apply {
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
                    insertTask()
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
    }

    private fun validateTaskName(): Boolean {
        binding.taskNameInput.clearError()
        return if (binding.taskNameEditText.text.isNullOrBlank()) {
            binding.taskNameInput.error = getString(R.string.must_be_not_empty)
            false
        } else true
    }

    private fun insertTask() {
        val name = binding.taskNameEditText.text.toString()
        val description = binding.taskDescriptionEditText.text.toString()
        mainViewModel.insertTask(name, description, tag, TaskState.DOING).observe(
            viewLifecycleOwner,
            {
                activity?.hideSoftKeyboard()
                findNavController().navigateUp()
            }
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
