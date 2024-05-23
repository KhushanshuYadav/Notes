package com.khushanshu.wishes.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


//this file and data class will be stored to data base
//id=0 tells the wish is not created yet

//making it a entity i.e table for our room data base by Entity annotation where each property  of data class in a column basically it is schema of sql table
@Entity(tableName = "wish-table")
data class Wish
    (
    @PrimaryKey(autoGenerate = true)  //making id column primary key its value will increase automatically when we insert data
    val id:Long=0L,
    @ColumnInfo(name = "wish-title")  //changing name of column kind of alias
    val title:String="",
    @ColumnInfo(name = "wish-desc")
    val description:String=""
)