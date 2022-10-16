package com.kaftanchikova.entrantandroid.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.kaftanchikova.entrantandroid.R
import com.kaftanchikova.entrantandroid.databinding.ActivityMainBinding
import com.kaftanchikova.entrantandroid.model.Entrant
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), Navigator {

    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavView = binding.bottomNavigationView
        navController = findNavController(R.id.fragment)

        val appBarConfig = AppBarConfiguration(setOf(R.id.theFormFragment, R.id.listFragment, R.id.infoFragment))
        setupActionBarWithNavController(navController, appBarConfig)

        bottomNavView.setupWithNavController(navController)
    }

    override fun showInfo(entrant: Entrant) {
        val fragment = InfoFragment.newInstance(entrant.id)
        navController.navigate(R.id.infoFragment, fragment.arguments)
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun toast(messageRes: Int) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }
}