package com.example.corrutinestest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm = ViewModelProvider(this)[MainViewModel::class.java]

        vm.loginResult.observe(this, Observer { success ->
            toast(if (success) "success" else "failure")
        })

        val username: EditText = findViewById(R.id.username)
        val password: EditText = findViewById(R.id.password)
        val submit: Button = findViewById(R.id.submit)

        submit.setOnClickListener {
            //Builder of coroutines: withContext, async
            vm.onSubmitClicked(username.text.toString(), password.text.toString())
        }
    }

    private fun validateLogin(usernane: String, password: String): Boolean {
        Thread.sleep(2000)
        return usernane.isNotEmpty() && password.isNotEmpty()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}



private fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}