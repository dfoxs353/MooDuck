package com.example.mooduck.ui.fragments.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mooduck.R
import com.example.mooduck.ui.helpers.setupWithNavController
import com.example.mooduck.ui.viewmodel.MainViewModel
import com.example.mooduck.ui.viewmodel.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment() : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }


    private val userViewModel: UserViewModel by activityViewModels()
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        userViewModel.user.observe(viewLifecycleOwner) { user ->
            if (user == null)
                findNavController().navigate(R.id.loginFragment)
        }
    }

}