package com.example.flow.presentation.screens.navigation



enum class NavScreens {
     HomeScreen,
     DetailScreen,
     CreateScreen,
     EditScreen;
     companion object{
         fun fromRoute(route:String?):NavScreens
         = when (route?.substringBefore("/")){
             HomeScreen.name -> HomeScreen
             DetailScreen.name ->DetailScreen
             CreateScreen.name ->CreateScreen
             EditScreen.name -> EditScreen
             null->HomeScreen
             else -> throw IllegalArgumentException("Route $route is not recognized ")
         }
     }
}