package com.example.mooduck.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.mooduck.ui.components.AppBar
import com.example.mooduck.ui.models.SearchWidgetState
import com.example.mooduck.ui.navigation.MainNavigationTree

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navController: NavController,
) {
    val  viewState = homeViewModel.viewState.observeAsState()
    val books = homeViewModel.bookPagingFlow.collectAsLazyPagingItems()



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
        ListBookScreen(
            books = books,
            onBookClick = {},
            onWantToReadClick = {},
        )
    }
    

}