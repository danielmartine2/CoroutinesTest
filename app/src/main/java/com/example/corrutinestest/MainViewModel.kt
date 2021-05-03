package com.example.corrutinestest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//Nadie debe decirle al view model lo que debe hacer, si no lo que ha ocurrido.
class MainViewModel(private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO): ViewModel() {

    private val  _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    fun onSubmitClicked(username: String, password: String){
        viewModelScope.launch {
            val result = withContext(ioDispatcher){validateLogin(username, password)}
            _loginResult.value = result
        }
    }

    private fun validateLogin(username: String, password: String): Boolean{
        Thread.sleep(2000)
        return username.isNotEmpty() && password.isNotEmpty()
    }
}