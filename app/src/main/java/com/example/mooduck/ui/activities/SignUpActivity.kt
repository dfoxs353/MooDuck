package com.example.mooduck.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mooduck.R
import com.example.mooduck.databinding.ActivityLogInBinding
import com.example.mooduck.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}