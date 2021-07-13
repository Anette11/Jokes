package com.example.jokes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokes.models.Value
import com.example.jokes.repository.JokeRepository
import kotlinx.coroutines.launch

class JokeViewModel : ViewModel() {
    val progressBarWebViewVisibility = MutableLiveData<Int>()

    val isNetworkAvailableToUse = MutableLiveData(false)

    val actionBarName = MutableLiveData<String>()

    val isShouldShowToastAboutNetworkIsNotAvailableToUse = MutableLiveData(false)

    private val _progressBarJokesListVisibility = MutableLiveData<Int>()
    val progressBarJokesListVisibility: LiveData<Int>
        get() = _progressBarJokesListVisibility

    private val _value = MutableLiveData<List<Value>>()
    val value: LiveData<List<Value>>
        get() = _value

    fun getValue(number: Int) = viewModelScope.launch {
        _progressBarJokesListVisibility.postValue(1)
        if (isNetworkAvailableToUse.value == true) {
            val response = JokeRepository.getJokesList(number)
            _value.postValue(response.body()?.value)
        } else {
            isShouldShowToastAboutNetworkIsNotAvailableToUse.postValue(true)
        }
        _progressBarJokesListVisibility.postValue(0)
    }
}