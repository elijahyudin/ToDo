package com.coursework.androidtodometer.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.coursework.androidtodometer.R
import com.coursework.androidtodometer.databinding.MainActivityBinding
import com.coursework.androidtodometer.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavigation()
        setBottomAppBar()
        binding.createButton.setOnClickListener {
            navController().navigate(R.id.addTaskFragment)
        }
    }

    private fun setBottomAppBar() {
        binding.bottomAppBar.setNavigationOnClickListener {
            showMenu()
        }
        binding.bottomAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.more -> {
                    showMoreOptions()
                    true
                }
                else -> false
            }
        }
    }

    private fun setNavigation() {
        navController().addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.tasksFragment -> {
                    binding.bottomAppBar.visibility = View.VISIBLE
                    mainViewModel.projects.observe(
                        this,
                        {
                            if (!it.isNullOrEmpty()) {
                                binding.createButton.show()
                            } else {
                                binding.createButton.hide()
                            }
                        }
                    )
                }
                else -> {
                    binding.bottomAppBar.visibility = View.GONE
                    binding.createButton.hide()
                    mainViewModel.projects.removeObservers(this)
                }
            }
        }
    }

    private fun showMoreOptions() =
        navController().navigate(R.id.moreBottomSheetDialog)

    private fun showMenu() =
        navController().navigate(R.id.menuDialog)

    fun showSnackbar(text: String) {
        Snackbar.make(binding.coordinatorLayout, text, Snackbar.LENGTH_LONG)
            .setAnchorView(binding.createButton)
            .show()
    }

    private fun navController() = findNavController(R.id.nav_host_fragment)

    companion object {
        private const val TAG = "MainActivity"
    }
}
