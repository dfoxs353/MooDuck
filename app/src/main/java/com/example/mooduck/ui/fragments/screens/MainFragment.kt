package com.example.mooduck.ui.fragments.screens

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.mooduck.R
import com.example.mooduck.data.repository.LocalUserRepository
import com.example.mooduck.ui.helpers.setupWithNavController
import com.example.mooduck.ui.viewmodel.MainViewModel
import com.example.mooduck.ui.viewmodel.MainViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainFragment() : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }


    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = MainViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        val localUserRepository = LocalUserRepository(requireContext())

        with(localUserRepository){
           if(getAccessToken() != null && getRefreshToken() != null)
           {
               viewModel.setRetrofitToken(getAccessToken()!!,getRefreshToken()!!)
               Log.d("TAG","tokens: ${getAccessToken()}")
           }
        }

        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottom_menu)

        bottomNavigationView.setupWithNavController(
            navGraphIds = listOf(
                R.navigation.nav_graph_book_list,
                R.navigation.nav_graph_search,
                R.navigation.nav_graph_account,
            ),
            fragmentManager = childFragmentManager,
            containerId = R.id.nav_host_fragment,
            intent = requireActivity().intent
        )
    }

}