package com.example.corrutinestest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        job = SupervisorJob()

        val username: EditText = findViewById(R.id.username)
        val password: EditText = findViewById(R.id.password)
        val submit: Button = findViewById(R.id.submit)

        submit.setOnClickListener {
            launch {
                val success = withContext(Dispatchers.IO) {
                    validateLogin(username.text.toString(), password.text.toString())
                }
                toast(if (success) "success" else "failure")
            }
        }
    }

    private fun validateLogin(usernane: String, password: String): Boolean {
        Thread.sleep(2000)
        return usernane.isNotEmpty() && password.isNotEmpty()
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

}



private fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}