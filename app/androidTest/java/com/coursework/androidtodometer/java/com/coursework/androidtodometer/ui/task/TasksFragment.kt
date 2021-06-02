package com.coursework.androidtodometer.ui.task

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.card.MaterialCardView
import com.coursework.androidtodometer.R
import com.coursework.androidtodometer.databinding.TasksFragmentBinding
import com.coursework.androidtodometer.model.Task
import com.coursework.androidtodometer.ui.adapter.TasksAdapter
import com.coursework.androidtodometer.ui.swipe.SwipeController
import com.coursework.androidtodometer.util.MaterialDialog
import com.coursework.androidtodometer.util.MaterialDialog.Companion.icon
import com.coursework.androidtodometer.util.MaterialDialog.Companion.message
import com.coursework.androidtodometer.util.MaterialDialog.Companion.negativeButton
import com.coursework.androidtodometer.util.MaterialDialog.Companion.positiveButton
import com.coursework.androidtodometer.util.ProgressUtil.getPercentage
import com.coursework.androidtodometer.util.ProgressUtil.getTasksDoneProgress
import com.coursework.androidtodometer.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksFragment : Fragment() {
    private var _binding: TasksFragmentBinding? = null
    private val binding get() = _binding!!

    private val tasksAdapter = TasksAdapter()

    private val mainViewModel by viewModels<MainViewModel>()
    // NOTE: using Koin we can write also:
    // private val mainViewModel by lazy { getViewModel<MainViewModel>() }
    // Using fragment-ktx extension:
    // private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TasksFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        initTasksRecyclerView()
        initProjectSelectedObserver()
        setSwipeActions()
    }

    private fun initProjectSelectedObserver() {
        mainViewModel.projectSelected.observe(
            viewLifecycleOwner,
            { project ->
                binding.projectNameTextView.text = project?.name ?: "-"
                project?.tasks?.let {
                    if (it.isEmpty()) {
                        showEmptyListIllustration()
                    } else {
                        hideEmptyListIllustration()
                    }
                    tasksAdapter.submitList(it)
                    setProgressValue(getTasksDoneProgress(it))
                }
            }
        )
    }

    private fun setProgressValue(progress: Int) {
        ObjectAnimator.ofInt(binding.progressBar, "progress", binding.progressBar.progress, progress).apply {
            duration = resources.getInteger(R.integer.progress_bar_animation).toLong()
            interpolator = AccelerateInterpolator()
        }.start()
        binding.progressTextView.text = getPercentage(progress)
    }

    private fun showEmptyListIllustration() {
        binding.emptyList.visibility = View.VISIBLE
        removeToolbarScrollFlags()
    }

    private fun hideEmptyListIllustration() {
        binding.emptyList.visibility = View.GONE
        setToolbarScrollFlags()
    }

    private fun initTasksRecyclerView() {
        binding.tasksRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tasksAdapter
        }
        binding.tasksRecyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }
        tasksAdapter.taskClickListener = object : TasksAdapter.TaskClickListener {
            override fun onTaskClick(taskId: Int, card: MaterialCardView) {
                val extras = FragmentNavigatorExtras(
                    card to taskId.toString()
                )
                val action = TasksFragmentDirections.navToTask(
                    taskId = taskId
                )
                findNavController().navigate(action, extras)
            }

            override fun onTaskDoneClick(task: Task) {
                mainViewModel.setTaskDone(task.id)
            }

            override fun onTaskDoingClick(task: Task) {
                mainViewModel.setTaskDoing(task.id)
            }
        }
    }

    private fun removeToolbarScrollFlags() {
        (binding.toolbar.layoutParams as AppBarLayout.LayoutParams).scrollFlags = 0
    }

    private fun setToolbarScrollFlags() {
        (binding.toolbar.layoutParams as AppBarLayout.LayoutParams).scrollFlags =
            AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
    }

    private fun setSwipeActions() {
        val swipeController = SwipeController(
            requireContext(),
            object :
                SwipeController.SwipeControllerActions {
                override fun onDelete(position: Int) {
                    MaterialDialog.createDialog(requireContext()) {
                        icon(R.drawable.ic_warning_24dp)
                        message(R.string.delete_task_dialog)
                        positiveButton(getString(R.string.ok)) {
                            tasksAdapter.currentList[position]?.id?.let { taskId ->
                                mainViewModel.deleteTask(
                                    taskId
                                )
                            }
                        }
                        negativeButton(getString(R.string.cancel))
                    }.show()
                }
            }
        )
        val itemTouchHelper = ItemTouchHelper(swipeController)
        itemTouchHelper.attachToRecyclerView(binding.tasksRecyclerView)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val TAG = "TasksFragment"
    }
}
