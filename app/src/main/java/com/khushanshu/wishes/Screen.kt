package com.khushanshu.wishes



//each object of iit will be a screen itself with is route as property
sealed class Screen(val route:String) {

    object HomeScreen: Screen("home_screen")
    object AddScreen: Screen("add_screen")
}