package com.khushanshu.wishes

import android.app.Activity
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Shapes
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.*
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.navigation.NavController
import com.khushanshu.wishes.data.Wish
import java.util.Locale

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(navController: NavController,viewModel: WishViewModel){

    val view = LocalView.current
    val window = (view.context as Activity).window
    window.statusBarColor = Color(221,30,95).toArgb()

    val context= LocalContext.current;
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,

        topBar ={ AppBarView(title = "WishList") },

        backgroundColor = Color.Black,  //TODO change later

        floatingActionButton = {
            FloatingActionButton( onClick = { navController.navigate(Screen.AddScreen.route + "/0L") }, modifier = Modifier.padding(20.dp), contentColor = Color.White, backgroundColor = colorResource(id = R.color.app_bar_color), )
            {

                Icon(imageVector = Icons.Default.Add, contentDescription =null )

            }
        }



    )
    {
        //obtaining  the list of wishes from database
        val wishlist=viewModel.getAllWishes.collectAsState(initial = listOf())

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(it)){
            items(wishlist.value, key= { wish -> wish.id} ){

                    wish ->  //name given to it

                //creating a dismiss state
                val dismissState= rememberDismissState(
                    confirmStateChange = {
                        if(it==DismissValue.DismissedToEnd || it==DismissValue.DismissedToStart) {
                            viewModel.deleteWish(wish)
                        }
                        true

                    }
                )

                SwipeToDismiss(
                    state = dismissState,
                    background ={
                        //creating a dynamic color i.e changes as per animation
                        val color by animateColorAsState(
                            if(dismissState.dismissDirection == DismissDirection.EndToStart) Color.Red else Color.Transparent,
                            label = ""
                        )
                        val alignment = Alignment.CenterEnd
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = alignment
                        ){
                            Icon(Icons.Default.Delete,
                                contentDescription = "Delete Icon",
                                tint = Color.White
                            )
                        }
                    } ,
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds ={FractionalThreshold(0.25f)},
                    dismissContent = {

                        WishItem(wish=wish) {
                            val id=wish.id;
                            navController.navigate(Screen.AddScreen.route+ "/$id") //passing argument id to second screen
                        }
                    }

                )





            }

        }



    }

}


@Composable
//A new wish item which is displayed to user
fun WishItem(wish:Wish,onClick : () -> Unit){

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, start = 8.dp, end = 8.dp)
        .clickable { onClick() } , elevation = 10.dp , backgroundColor = Color.White)
    {
        Column (modifier=Modifier.padding(16.dp)){

            Text(text = wish.title, fontWeight = FontWeight.ExtraBold)

            Text(text = wish.description,)

        }

    }

}