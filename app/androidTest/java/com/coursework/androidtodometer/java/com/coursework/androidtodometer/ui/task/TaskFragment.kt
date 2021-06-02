package com.coursework.androidtodometer.ui.task

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import androidx.transition.TransitionManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialFade
import com.coursework.androidtodometer.R
import com.coursework.androidtodometer.databinding.TaskFragmentBinding
import com.coursework.androidtodometer.model.TaskState
import com.coursework.androidtodometer.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class TaskFragment : Fragment() {

    private lateinit var binding: TaskFragmentBinding

    private val args: TaskFragmentArgs by navArgs()

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.task_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = buildContainerTransform()
        sharedElementReturnTransition = buildContainerTransform()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NavigationUI.setupWithNavController(binding.toolbar, findNavController())
        binding.coordinator.transitionName = args.taskId.toString()
        initAppBarLayoutOffsetChangedListener()
        initEditButton()
        mainViewModel.getTask(args.taskId).observe(
            viewLifecycleOwner,
            {
                it?.let { task ->
                    binding.task = task
                    task.tag?.resId?.let { resId ->
                        binding.taskTagColor.setColorFilter(
                            ContextCompat.getColor(
                                requireContext(),
                                resId
                            )
                        )
                    }
                    if (task.state == TaskState.DONE) {
                        val spannableString = SpannableString(task.name)
                        spannableString.setSpan(
                            StrikethroughSpan(),
                            0,
                            spannableString.length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        binding.taskNameTextView.text = spannableString
                    }
                }
            }
        )
    }

    private fun initAppBarLayoutOffsetChangedListener() {
        binding.appBarLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                binding.toolbarTitle.isVisible = abs(verticalOffset) >= appBarLayout.totalScrollRange
            }
        )
    }

    private fun initEditButton() {
        binding.editButton.apply {
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
                resources.getInteger(R.integer.fade_transition_start_long_delay).toLong()
            )
            setOnClickListener {
                val action =
                    TaskFragmentDirections.navToEditTaskFragment(
                        args.taskId
                    )
                findNavController().navigate(action)
            }
        }
    }

    /**
     * Build the Material Container Transform for this fragment.
     *
     * Note: Edit button MaterialFade animation is delayed to avoid starting it earlier
     * container transform. If delay time is less than duration of container transform,
     * the container transform won't be done.
     */
    private fun buildContainerTransform(): MaterialContainerTransform =
        MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            interpolator = FastOutSlowInInterpolator()
            containerColor = MaterialColors.getColor(requireActivity().findViewById(android.R.id.content), R.attr.colorSurface)
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
            duration = resources.getInteger(R.integer.container_transform_duration).toLong()
        }
}
