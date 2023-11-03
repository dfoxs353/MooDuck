package com.example.mooduck.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mooduck.R
import com.example.mooduck.ui.viewmodel.AccountPageViewModel

class AccountPageFragment : Fragment() {

    companion object {
        fun newInstance() = AccountPageFragment()
    }

    private lateinit var viewModel: AccountPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AccountPageViewModel::class.java)
        // TODO: Use the ViewModel
    }

}