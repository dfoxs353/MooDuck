package com.example.mooduck.ui.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mooduck.R
import com.example.mooduck.ui.navigation.MainNavigationTree
import com.example.mooduck.ui.navigation.NavigationArgs
import com.example.mooduck.ui.screens.detailbook.DetailBookScreen
import com.example.mooduck.ui.screens.detailbook.DetailBookViewModel
import com.example.mooduck.ui.screens.home.HomeScreen
import com.example.mooduck.ui.screens.home.HomeViewModel
import com.example.mooduck.ui.screens.profile.ProfileScreen
import com.example.mooduck.ui.screens.search.SearchScreen
import com.example.mooduck.ui.theme.Cian
import com.example.mooduck.ui.theme.White

data class BottomNavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

@Composable
fun MainScreen(
    navController: NavHostController
) {
    val items = listOf(
        BottomNavigationItem(
            title = stringResource(id = R.string.home),
            route = MainNavigationTree.Home.route,
            unselectedIcon = Icons.Filled.Home,
            selectedIcon = Icons.Outlined.Home,
        ),
        BottomNavigationItem(
            title = stringResource(id = R.string.home),
            route = MainNavigationTree.Search.route,
            unselectedIcon = Icons.Filled.Search,
            selectedIcon = Icons.Outlined.Search,
        ),
        BottomNavigationItem(
            title = stringResource(id = R.string.home),
            route = MainNavigationTree.Account.route,
            unselectedIcon = Icons.Filled.Person,
            selectedIcon = Icons.Outlined.Person,
        ),
    )

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val mainNavController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = White
            ) {
                val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEachIndexed { index, item ->  
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            selectedItemIndex = index
                            mainNavController.navigate(item.route){
                                popUpTo(mainNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                restoreState = true
                            }
                        },
                        label = {
                            Text(
                                text = item.title
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = if (currentDestination?.hierarchy?.any { it.route == item.route } == true){
                                    item.selectedIcon
                                }
                                else{
                                    item.unselectedIcon
                                },
                                contentDescription = item.title,
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Cian,
                            selectedIconColor = White,
                        ),
                    )
                }
            }
        }
    ) { pv ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(pv),
        ) {
            NavHost(navController = mainNavController, startDestination = MainNavigationTree.Home.route){
                composable(MainNavigationTree.Home.route){
                    val homeViewModel = hiltViewModel<HomeViewModel>()
                    HomeScreen(
                        homeViewModel = homeViewModel,
                        navController = mainNavController
                    )
                }
                composable(MainNavigationTree.Search.route){ SearchScreen() }
                composable(MainNavigationTree.Account.route){ ProfileScreen() }
                composable("${MainNavigationTree.DetailBook.route}/{${NavigationArgs.BookId}}"){
                    val detailBookViewModel = hiltViewModel<DetailBookViewModel>()

                    DetailBookScreen(
                        detailBookViewModel = detailBookViewModel,
                        navController = mainNavController,
                    )
                }
            }
        }
    }

}