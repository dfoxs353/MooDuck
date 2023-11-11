package com.example.mooduck.ui.fragments.screens

import android.app.Application
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mooduck.R
import com.example.mooduck.data.remote.auth.AuthResponse
import com.example.mooduck.data.repository.LocalUserRepository
import com.example.mooduck.databinding.FragmentLoginBinding
import com.example.mooduck.ui.viewmodel.LoginViewModel
import com.example.mooduck.ui.viewmodel.LoginViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        val viewModelFactory = LoginViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        binding = FragmentLoginBinding.inflate(layoutInflater)
        val view = binding.root

        val localUserRepository = LocalUserRepository(requireContext())

        val username = binding.mailInput
        val password = binding.passwordInput
        val usernameLayout = binding.mailInputLayout
        val passwordLayout = binding.passwordInputLayout

        val login = binding.loginButton
        val signup = binding.signupButton


        signup.setOnClickListener {
            findNavController().navigate(R.id.action_loadingFragment_to_loginFragment)
        }

        viewModel.loginFormState.observe( viewLifecycleOwner, Observer {
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

        viewModel.loginResult.observe(viewLifecycleOwner, Observer {
            val loginResult = it ?: return@Observer

            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success.user.username)
                view.postDelayed({
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                },1000)
            }
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
        return view
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
            context,
            "$welcome $name",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(context, errorString, Toast.LENGTH_SHORT).show()
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