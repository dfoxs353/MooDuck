package com.example.mooduck.ui.fragments.screens

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mooduck.R
import com.example.mooduck.data.remote.auth.AuthResponse
import com.example.mooduck.data.repository.LocalUserRepository
import com.example.mooduck.databinding.FragmentSignupBinding
import com.example.mooduck.ui.viewmodel.SignupViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignupFragment : Fragment() {

    companion object {
        fun newInstance() = SignupFragment()
    }

    private lateinit var binding: FragmentSignupBinding
    private val viewModel: SignupViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentSignupBinding.inflate(layoutInflater)

        val signup = binding.signupButton
        val login = binding.loginButton

        val username = binding.nameInput
        val mail = binding.mailInput
        val firstPassword = binding.passwordFirstInput
        val secondPassword = binding.passwordSecondInput

        val usernameLayout = binding.nameInputLayout
        val mailLayout = binding.mailInputLayout
        val firstPasswordLayout = binding.passwordFirstInputLayout
        val secondPasswordLayout = binding.passwordSecondInputLayout

        login.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }

        viewModel.signupFormState.observe( viewLifecycleOwner, Observer {
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

        viewModel.signupResult.observe(viewLifecycleOwner, Observer {
            val signupResult = it ?: return@Observer

            if (signupResult.error != null) {
                showLoginFailed(signupResult.error)
            }
            if (signupResult.success != null) {
                updateUiWithUser(signupResult.success.user.username)
                findNavController().navigate(R.id.mainFragment)
            }
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
                            mail.text.toString(),
                            firstPassword.text.toString()
                        )
                }
                false
            }

            signup.setOnClickListener {
                signup(username.text.toString(),mail.text.toString(), firstPassword.text.toString())
            }
        }

        return binding.root
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
            context,
            "$welcome $name",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(context, errorString, Toast.LENGTH_SHORT).show()
    }

}