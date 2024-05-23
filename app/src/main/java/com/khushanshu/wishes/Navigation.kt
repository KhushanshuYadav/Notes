package com.khushanshu.wishes


import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.*


@Composable
fun Navigation(viewModel: WishViewModel= viewModel(), navController:NavHostController= rememberNavController()){

    NavHost(navController=navController, startDestination = Screen.HomeScreen.route)
    {

        composable(Screen.HomeScreen.route){
            HomeView(navController,viewModel)
        }

        composable(
            Screen.AddScreen.route+ "/{id}",
            arguments = listOf ( navArgument("id"){

                type= NavType.LongType
                defaultValue=0L
                nullable=false

            }  )
        )
        {entry->  //name given to "it" is entry
            //in our case the list has one item only to retrieve below retrieving id
            val id=if(entry.arguments!=null) entry.arguments!!.getLong("id") else 0L

            AddEditDetailView(id = id, viewModel = viewModel, navController = navController)
        }

    }

}