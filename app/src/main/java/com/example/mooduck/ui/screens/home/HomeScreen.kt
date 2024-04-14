package com.example.mooduck.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.mooduck.ui.components.AppBar
import com.example.mooduck.ui.models.SearchWidgetState
import com.example.mooduck.ui.navigation.MainNavigationTree
import com.example.mooduck.ui.screens.home.models.HomeSubState
import com.example.mooduck.ui.screens.home.views.ListBookView

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navController: NavController,
) {
    val  viewState = homeViewModel.viewState.observeAsState()

    LaunchedEffect(key1 = homeViewModel) {
        homeViewModel.getBooks()
    }

    Column {
        AppBar(
            searchWidgetState = SearchWidgetState.CLOSED,
            searchText = "",
            onTextChange = {},
            onCloseClick = {},
            onSearchClick = {},
            onSearchTriggered = {
                                navController.navigate(MainNavigationTree.Search.route){
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
            },
        )
        with(viewState.value!!){
            when(homeSubState){
                HomeSubState.BookList -> {
                    ListBookView(
                        bookList = bookList,
                        onBookClick = {},
                        onWantToReadClick = {},
                    )
                }
                HomeSubState.Loading -> Text(text = "Loading")
                HomeSubState.Error -> Text(text = "Error")
            }
        }
    }
    

}