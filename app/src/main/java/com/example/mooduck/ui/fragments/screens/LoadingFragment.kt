package com.example.mooduck.ui.fragments.screens

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.example.mooduck.ui.viewmodel.LoadingViewModel
import com.example.mooduck.R
import com.example.mooduck.data.remote.Result
import com.example.mooduck.data.repository.LocalUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoadingFragment : Fragment(R.layout.fragment_loading) {

    companion object {
        fun newInstance() = LoadingFragment()
    }

    private lateinit var viewModel: LoadingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoadingViewModel::class.java)
        val localUserRepository = LocalUserRepository(requireContext())



        with(localUserRepository){
            if(getAccessToken() != null && getRefreshToken() != null){
                viewModel.setRetrofitToken(getAccessToken()!!,getRefreshToken()!!)
                Log.d("TAG", getRefreshToken()!!)
            }
            else{
                findNavController().navigate(R.id.action_loadingFragment_to_loginFragment)
            }
        }

        viewModel.refreshResult.observe(viewLifecycleOwner, Observer {
            val  result = it ?: return@Observer

            if (result.error != null){
                findNavController().navigate(R.id.action_loadingFragment_to_loginFragment)
            }
            if (result.success != null){
                with(result.success){
                    findNavController().navigate(R.id.action_loadingFragment_to_mainFragment)
                }
            }
        })

    }


}