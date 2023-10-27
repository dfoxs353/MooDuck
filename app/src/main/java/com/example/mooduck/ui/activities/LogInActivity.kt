package com.example.mooduck.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mooduck.R
import com.example.mooduck.databinding.ActivityLogInBinding
import com.example.mooduck.ui.viewmodel.LogInViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private lateinit var viewModel: LogInViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(LogInViewModel::class.java)

        binding = ActivityLogInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.loginButton.setOnClickListener {
            login()
        }

        binding.signupButton.setOnClickListener {
            val intentLogIn = Intent(this, SignUpActivity::class.java)
            startActivity(intentLogIn)
        }
    }

    private fun login() {
        val mail = binding.mailInput.text.toString()
        val password = binding.passwordInput.text.toString()

        GlobalScope.launch(Dispatchers.Main) {
                val response = viewModel.logIn(mail,password)
                if(response != null){
                    Log.d("TAG","log")
                    showToast("Успешный вход ${response.user.username}")
                }
                else{
                    Log.d("TAG", "no log")
                    showToast("Ошибка")
                }
        }
    }

    private fun showToast(text: String){
        val duration = Toast.LENGTH_SHORT
        val toastLogin = Toast.makeText(this, text,duration)

        toastLogin.show()
    }
}