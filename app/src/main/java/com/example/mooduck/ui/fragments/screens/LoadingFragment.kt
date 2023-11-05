package com.example.mooduck.ui.fragments.screens

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mooduck.ui.viewmodel.LoadingViewModel
import com.example.mooduck.R

class LoadingFragment : Fragment(R.layout.fragment_loading) {

    companion object {
        fun newInstance() = LoadingFragment()
    }

    private lateinit var viewModel: LoadingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.postDelayed({
                         findNavController().navigate(R.id.action_loadingFragment_to_loginFragment)
        }, 1000)
    }

}