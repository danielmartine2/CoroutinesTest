package com.example.corrutinestest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val username: EditText = findViewById(R.id.username)
        val password: EditText = findViewById(R.id.password)
        val submit: Button = findViewById(R.id.submit)

        submit.setOnClickListener {
            lifecycleScope.launch {
                //Builder of coroutines: withContext, async
                val success = async(Dispatchers.IO) {
                    validateLogin(username.text.toString(), password.text.toString())
                }

                val success2 = async(Dispatchers.IO) {
                    validateLogin2(username.text.toString(), password.text.toString())
                }
                toast(if (success.await() && success2.await()) "success" else "failure")
            }
        }
    }

    private fun validateLogin(usernane: String, password: String): Boolean {
        Thread.sleep(2000)
        return usernane.isNotEmpty() && password.isNotEmpty()
    }

    private fun validateLogin2(usernane: String, password: String): Boolean {
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