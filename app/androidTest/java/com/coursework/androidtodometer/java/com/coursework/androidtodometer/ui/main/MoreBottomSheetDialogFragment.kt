package com.coursework.androidtodometer.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.coursework.androidtodometer.R
import com.coursework.androidtodometer.databinding.MoreBottomSheetDialogFragmentBinding
import com.coursework.androidtodometer.preferences.AppTheme.Companion.THEME_ARRAY
import com.coursework.androidtodometer.util.MaterialDialog
import com.coursework.androidtodometer.util.MaterialDialog.Companion.icon
import com.coursework.androidtodometer.util.MaterialDialog.Companion.message
import com.coursework.androidtodometer.util.MaterialDialog.Companion.negativeButton
import com.coursework.androidtodometer.util.MaterialDialog.Companion.positiveButton
import com.coursework.androidtodometer.util.MaterialDialog.Companion.singleChoiceItems
import com.coursework.androidtodometer.util.MaterialDialog.Companion.title
import com.coursework.androidtodometer.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private var _binding: MoreBottomSheetDialogFragmentBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MoreBottomSheetDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAppThemeObserver()
        initEditProjectObserver()
        initDeleteProjectObserver()
        setClickListeners()
    }

    private fun initAppThemeObserver() {
        mainViewModel.appTheme.observe(
            viewLifecycleOwner,
            { currentTheme ->
                val appTheme = THEME_ARRAY.firstOrNull() { it.modeNight == currentTheme }
                appTheme?.let {
                    binding.themeIcon.setImageResource(it.themeIconRes)
                    binding.themeDescription.text = getString(it.modeNameRes)
                }
            }
        )
    }

    private fun initDeleteProjectObserver() {
        mainViewModel.projects.observe(
            viewLifecycleOwner,
            {
                it?.let { list ->
                    if (list.size > 1) {
                        enableDeleteProjectButton()
                    } else {
                        disableDeleteProjectButton()
                    }
                } ?: disableDeleteProjectButton()
            }
        )
    }

    private fun enableDeleteProjectButton() {
        binding.deleteProjectIcon.isEnabled = true
        binding.deleteProjectText.isEnabled = true
        binding.deleteProject.isEnabled = true
        binding.deleteProject.setOnClickListener {
            MaterialDialog.createDialog(requireContext()) {
                icon(R.drawable.ic_warning_24dp)
                message(R.string.delete_project_dialog)
                positiveButton(getString(R.string.ok)) {
                    mainViewModel.deleteProject()
                }
                negativeButton(getString(R.string.cancel))
            }.show()
        }
    }

    private fun disableDeleteProjectButton() {
        binding.deleteProjectIcon.isEnabled = false
        binding.deleteProjectText.isEnabled = false
        binding.deleteProject.isEnabled = false
        binding.deleteProject.setOnClickListener(null)
    }

    private fun initEditProjectObserver() {
        mainViewModel.projectSelected.observe(
            viewLifecycleOwner,
            { projectSelected ->
                projectSelected?.let {
                    enableEditProjectButton()
                } ?: disableEditProjectButton()
            }
        )
    }

    private fun enableEditProjectButton() {
        binding.editProjectIcon.isEnabled = true
        binding.editProjectText.isEnabled = true
        binding.editProject.isEnabled = true
        binding.editProject.setOnClickListener {
            dismiss()
            findNavController().navigate(R.id.editProjectFragment)
        }
    }

    private fun disableEditProjectButton() {
        binding.editProjectIcon.isEnabled = false
        binding.editProjectText.isEnabled = false
        binding.editProject.isEnabled = false
        binding.editProject.setOnClickListener(null)
    }

    private fun setClickListeners() {
        binding.themeOption.setOnClickListener {
            chooseThemeClick()
        }
        binding.aboutButton.setOnClickListener {
            dismiss()
            findNavController().navigate(R.id.aboutFragment)
        }
        binding.openSourceLicensesButton.setOnClickListener {
            dismiss()
            val intent = Intent(requireContext(), OssLicensesMenuActivity::class.java)
            intent.putExtra("title", getString(R.string.open_source_licenses))
            startActivity(intent)
        }
    }

    private fun chooseThemeClick() {
        val currentTheme = mainViewModel.appTheme.value
        var checkedItem = THEME_ARRAY.indexOfFirst { it.modeNight == currentTheme }
        if (checkedItem >= 0) {
            val items = THEME_ARRAY.map {
                getText(it.modeNameRes)
            }.toTypedArray()
            MaterialDialog.createDialog(requireContext()) {
                title(R.string.choose_theme)
                singleChoiceItems(items, checkedItem) {
                    checkedItem = it
                }
                positiveButton(getString(R.string.ok)) {
                    val mode = THEME_ARRAY[checkedItem].modeNight
                    AppCompatDelegate.setDefaultNightMode(mode)
                    mainViewModel.setAppTheme(mode)
                    // Update theme description TextView
                    binding.themeDescription.text = getString(THEME_ARRAY[checkedItem].modeNameRes)
                }
                negativeButton(getString(R.string.cancel))
            }.show()
        }
    }
}
