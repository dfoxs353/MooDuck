package com.example.mooduck.ui.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mooduck.R
import com.example.mooduck.data.remote.auth.Result
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

        val username = binding.mailInput
        val password = binding.passwordInput
        val usernameLayout = binding.mailInputLayout
        val passwordLayout = binding.passwordInputLayout

        val login = binding.loginButton
        val signup = binding.signupButton



        signup.setOnClickListener {
            val intentSignUpActivity = Intent(this, SignUpActivity::class.java)
            startActivity(intentSignUpActivity)
        }

        viewModel.loginFormState.observe( this@LogInActivity, Observer {
            val loginState = it ?: return@Observer

            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                usernameLayout.isErrorEnabled = true
                usernameLayout.error = getString(loginState.usernameError)
            }
            else{
                usernameLayout.isErrorEnabled =false
            }

            if (loginState.passwordError != null) {
                passwordLayout.error = getString(loginState.passwordError)
            }
            else{
                passwordLayout.isErrorEnabled =false
            }
        })

        viewModel.loginResult.observe(this@LogInActivity, Observer {
            val loginResult = it ?: return@Observer

            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success.user.username)
            }
            setResult(Activity.RESULT_OK)
        })

        username.afterTextChanged {
            viewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                viewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                login(username.text.toString(), password.text.toString())
            }
        }
    }

    private fun login(mail:String,password:String) {
        GlobalScope.launch(Dispatchers.Main) {
                viewModel.login(mail,password)
        }
    }

    private fun updateUiWithUser(name: String) {
        val welcome = getString(R.string.welcome)
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $name",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}


fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}