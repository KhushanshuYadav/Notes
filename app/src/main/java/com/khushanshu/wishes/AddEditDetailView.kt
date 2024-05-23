package com.khushanshu.wishes

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults

import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.khushanshu.wishes.data.Wish
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(id:Long, viewModel: WishViewModel,navController: NavController ){

    val view = LocalView.current
    val window = (view.context as Activity).window
    window.statusBarColor = Color(221,30,95).toArgb()

    val snackMessage = remember{ mutableStateOf("") };
    val scope= rememberCoroutineScope();
    val scaffoldState= rememberScaffoldState()

    if(id!=0L){
        val wish =viewModel.getAWishById(id).collectAsState(initial = Wish(0L,"",""))
        viewModel.wishTitleState=wish.value.title  //the wish we created or retrieve's value given to field in box
        viewModel.wishDescriptionState=wish.value.description

    }
    else{
        viewModel.wishTitleState=""
        viewModel.wishDescriptionState=""
    }

    Scaffold(
        topBar =
        {
            AppBarView(  title =  if(id!=0L) stringResource(id = R.string.update_wish) else stringResource(id =R.string.add_wish)    )
            {navController.navigateUp()}
        },
        backgroundColor =  Color.White,
        scaffoldState=scaffoldState  //giving the state of scaffold to it
    )
    {




        Column(modifier = Modifier
            .padding(it)
            .wrapContentSize()
            .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center)
        {
            Spacer(modifier = Modifier.height(10.dp))

            WishTextField(label = "Title", value = viewModel.wishTitleState, onValueChanged ={viewModel.onTitleChange(it)} )

            //Spacer(modifier = Modifier.height(10.dp))

            WishTextField(label = "Description", value = viewModel.wishDescriptionState, onValueChanged ={viewModel.onDescriptionChange(it)} )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick =
                {
                    if(viewModel.wishTitleState.isNotEmpty() && viewModel.wishDescriptionState.isNotEmpty()){

                        if(id!=0L){
                            // modify it in data base and o show too
                            viewModel.updateWish( Wish( id=id, title = viewModel.wishTitleState.trim(), description = viewModel.wishDescriptionState.trim() ) )
                        }
                        else{
                            // wish in database
                            viewModel.addWish( Wish( title=viewModel.wishTitleState.trim(), description = viewModel.wishDescriptionState.trim() ) )
                            snackMessage.value="Note created"
                        }



                        //scope.launch {
                        //showing snackBar in the scaffold state
                        //scaffoldState.snackbarHostState.showSnackbar(snackMessage.value);
                        navController.navigateUp();
                        //}



                    }
                    else{

                        snackMessage.value="Enter the title and description to create note"
                        scope.launch {
                            //showing snackBar in the scaffold state
                            scaffoldState.snackbarHostState.showSnackbar(snackMessage.value);
                            //navController.navigateUp();
                        }



                    }


                    /*scope.launch {
                        //showing snackBar in the scaffold state
                        scaffoldState.snackbarHostState.showSnackbar(snackMessage.value);
                        navController.navigateUp();
                    }*/


                },
                shape = RoundedCornerShape(13),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.app_bar_color) , contentColor = Color.White , disabledContainerColor = Color.Black , disabledContentColor = Color.Blue)

            )
            {

                Text(  text= if(id!=0L) "Update" else "Add", style = TextStyle(fontSize = 18.sp)  )

            }
        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishTextField(label:String, value:String,  onValueChanged: (String)->Unit ){


    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label={ Text(text=label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp) ,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,

            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            cursorColor = Color.Black,
            focusedLabelColor =  Color.Black,
            unfocusedLabelColor = Color.Black,

            ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Sentences)

    )



}


@Preview
@Composable
fun WishTextFieldPreview()
{
    AddEditDetailView(id =0L , viewModel = viewModel() , navController= rememberNavController())
}