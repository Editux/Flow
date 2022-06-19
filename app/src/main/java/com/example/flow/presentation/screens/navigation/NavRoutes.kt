package com.example.flow.presentation.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.flow.DetailsUI
import com.example.flow.presentation.screens.CreateScreen
import com.example.flow.presentation.screens.EditScreen
import com.example.flow.presentation.screens.HomeScreen


@Composable
 fun NavRoutes() {
     val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination =NavScreens.HomeScreen.name ){
        //Home Screen route
        composable(NavScreens.HomeScreen.name){
        HomeScreen(navController = navController)
        }
        // Detail Screen route
        composable(NavScreens.DetailScreen.name +"/{article}",arguments = listOf(navArgument(name = "article") {type=
            NavType.StringType})){
            backStrackEntry ->
            DetailsUI(navController = navController,backStrackEntry.arguments?.getString("article") )
        }
        // Create Screen route
        composable(NavScreens.CreateScreen.name){
            CreateScreen(navController = navController)
        }
        composable(NavScreens.EditScreen.name +"/{article}",arguments = listOf(navArgument(name = "article") {type=
            NavType.StringType})){
                backStrackEntry ->
            EditScreen(navController = navController,backStrackEntry.arguments?.getString("article") )
        }

    }




}