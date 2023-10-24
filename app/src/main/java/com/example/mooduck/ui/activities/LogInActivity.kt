package com.example.mooduck.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mooduck.R
import com.example.mooduck.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}