package com.example.mooduck.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mooduck.databinding.ActivitySignUpBinding
import com.example.mooduck.ui.fragments.ModalMailFragment
import com.example.mooduck.ui.viewmodel.SignUpViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    lateinit var viewModel: SignUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        binding.signupButton.setOnClickListener {
            signUp()
        }

        binding.loginButton.setOnClickListener {
            finish()
        }
    }

    private fun signUp() {
        val mail = binding.mailInput.text.toString()
        val password = binding.passwordFirstInput.text.toString()
        val username = binding.nameInput.text.toString()

        GlobalScope.launch(Dispatchers.Main) {
            val fragmentMail = ModalMailFragment()
            fragmentMail.show(supportFragmentManager, "modalMailFragment")

            val response = viewModel.signUp(mail,password, username)
            if(response != null){
                Log.d("TAG","log")
                showToast("Успешная регистрация ${response.user.username}")
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