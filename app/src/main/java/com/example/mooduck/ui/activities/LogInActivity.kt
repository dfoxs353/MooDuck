package com.example.mooduck.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mooduck.R
import com.example.mooduck.databinding.ActivityLogInBinding
import com.example.mooduck.ui.viewmodel.LogInViewModel
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
            val mail = binding.mailInput.text.toString()
            val password = binding.passwordInput.text.toString()

           GlobalScope.launch {
               val response = viewModel.login(mail,password)
               if(response){
                   Log.d("TAG","log")
               }
               else{
                   Log.d("TAG", "no log")
               }
           }
        }
    }
}