package com.khushanshu.wishes



import android.app.Application
import android.content.Context

class WishListApplication:Application() {

    override fun onCreate() {
        super.onCreate()

        Graph.provide(this)
    }
}