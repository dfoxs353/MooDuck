package com.example.mooduck.ui.fragments.screens

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.mooduck.R
import com.example.mooduck.databinding.FragmentAccountBinding
import com.example.mooduck.databinding.FragmentBookPageBinding
import com.example.mooduck.ui.viewmodel.AccountViewModel
import com.example.mooduck.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : Fragment() {

    companion object {
        fun newInstance() = AccountFragment()
    }

    private val userViewModel: UserViewModel by activityViewModels()
    private val viewModel: AccountViewModel by viewModels()


    private lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logOutButton.setOnClickListener {
            userViewModel.deleteUser()
        }
    }

}