package com.kaftanchikova.entrantandroid.view

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kaftanchikova.entrantandroid.App
import com.kaftanchikova.entrantandroid.repository.Repository
import com.kaftanchikova.entrantandroid.screens.Navigator

class MainViewModelFactory(private val app: App) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            ListViewModel::class.java -> {
                ListViewModel(app.repository)
            }
            InfoViewModel::class.java -> {
                InfoViewModel(app.repository)
            }
            TheFormModel::class.java -> {
                TheFormModel(app.repository)
            }
            else -> {
                throw IllegalStateException("Unknown view model class")
            }
        }
        return viewModel as T
    }
}

fun Fragment.navigator() = requireActivity() as Navigator

fun Fragment.factory() = MainViewModelFactory(requireContext().applicationContext as App)