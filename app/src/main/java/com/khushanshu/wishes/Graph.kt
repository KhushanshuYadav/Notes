package com.khushanshu.wishes

import android.content.Context
import androidx.room.Room
import com.khushanshu.wishes.data.WishDatabase
import com.khushanshu.wishes.data.WishRepository

// graph is singleton i.e a class with one object allowed
// it initializes our data base and repository when needed
//it is our dependency injection setup i.e it will add or provide dependencies to app i.e will inject it acts as a service locator basically a container
//dependencies are repo and our database
object Graph {

    lateinit var database: WishDatabase

    //initializing repo when needed and by thread safety by passing dao
    val wishRepository by lazy {
        WishRepository(wishDAO = database.wishDAO())
    }

    //this graph should provide us with the data base

    fun provide(context: Context){

        //creating a ref or instance to database
        database = Room.databaseBuilder(context, WishDatabase::class.java, "wishlist.db").build()
    }


}