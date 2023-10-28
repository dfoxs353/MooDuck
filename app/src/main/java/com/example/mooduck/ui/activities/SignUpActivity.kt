package com.example.mooduck.ui.activities

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mooduck.R
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

        val signup = binding.signupButton
        val login = binding.loginButton

        val username = binding.nameInput
        val maiil = binding.mailInput
        val firstPassword = binding.passwordFirstInput
        val secondPassword = binding.passwordSecondInput

        val usernameLayout = binding.nameInputLayout
        val maiilLayout = binding.mailInputLayout
        val firstPasswordLayout = binding.passwordFirstInputLayout
        val secondPasswordLayout = binding.passwordSecondInputLayout

        login.setOnClickListener {
            finish()
        }

        viewModel.signupFormState.observe( this@SignUpActivity, Observer {
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
                firstPasswordLayout.error = getString(loginState.passwordError)

                secondPasswordLayout.error = getString(loginState.passwordError)
            }
            else{
                secondPasswordLayout.isErrorEnabled =false
            }
        })

        viewModel.signupResult.observe(this@SignUpActivity, Observer {
            val signupResult = it ?: return@Observer

            if (signupResult.error != null) {
                showLoginFailed(signupResult.error)
            }
            if (signupResult.success != null) {
                updateUiWithUser(signupResult.success.user.username)
            }
            setResult(Activity.RESULT_OK)
        })

        username.afterTextChanged {
            viewModel.loginDataChanged(
                username.text.toString(),
                firstPassword.text.toString()
            )
        }

        firstPassword.apply {
            afterTextChanged {
                viewModel.loginDataChanged(
                    username.text.toString(),
                    firstPassword.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        signup(
                            username.text.toString(),
                            maiil.text.toString(),
                            firstPassword.text.toString()
                        )
                }
                false
            }

            signup.setOnClickListener {
                signup(username.text.toString(),maiil.text.toString(), firstPassword.text.toString())
            }
        }

    }



    private fun signup(username: String, mail:String,password:String) {
        GlobalScope.launch(Dispatchers.Main) {
            viewModel.signup(username,mail,password)
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