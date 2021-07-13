package com.example.jokes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.jokes.R
import com.example.jokes.databinding.ActivityMainBinding
import com.example.jokes.util.ConnectivityLiveData

class JokeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var jokeViewModel: JokeViewModel
    private lateinit var connectivityLiveData: ConnectivityLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setViewModel()
        setActionBarName()
        checkIfNetworkIsAvailableToUse()
        setNavigation()
        checkIfShouldShowToastAboutNetworkIsNotAvailableToUse()
    }

    private fun setViewModel() {
        jokeViewModel = ViewModelProvider(this, JokeFactory())
            .get(JokeViewModel::class.java)
    }

    private fun checkIfNetworkIsAvailableToUse() {
        connectivityLiveData = ConnectivityLiveData(this)

        connectivityLiveData.observe(this, {
            if (it != null) {
                jokeViewModel.isNetworkAvailableToUse.postValue(it)
            }
        })
    }

    private fun checkIfShouldShowToastAboutNetworkIsNotAvailableToUse() {
        jokeViewModel.isShouldShowToastAboutNetworkIsNotAvailableToUse.observe(this, {
            if (it != null) if (it) {
                jokeViewModel.isShouldShowToastAboutNetworkIsNotAvailableToUse.postValue(false)
                Toast.makeText(
                    this,
                    getString(R.string.network_is_not_available),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun setActionBarName() {
        jokeViewModel.actionBarName.observe(this, {
            if (it != null) {
                val actionBar = supportActionBar
                actionBar!!.title = it
            }
        })
    }

    private fun setNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment?

        NavigationUI.setupWithNavController(
            binding.bottomNavigationView,
            navHostFragment!!.navController
        )
    }
}