package com.example.jokes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class JokeFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JokeViewModel::class.java)) {
            return JokeViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}