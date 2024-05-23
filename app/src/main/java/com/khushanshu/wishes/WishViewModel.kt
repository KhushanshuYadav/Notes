package com.khushanshu.wishes



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khushanshu.wishes.data.Wish
import com.khushanshu.wishes.data.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishViewModel(private val wishRepository: WishRepository=Graph.wishRepository) : ViewModel() {

    var wishTitleState by mutableStateOf("")
    var wishDescriptionState by mutableStateOf("")

    fun onTitleChange(newTitle:String){
        wishTitleState=newTitle;
    }

    fun onDescriptionChange(newDescription:String){
        wishDescriptionState=newDescription;
    }

    lateinit var getAllWishes:Flow<List<Wish>>

    init{
        viewModelScope.launch {
            getAllWishes=wishRepository.getWishes()
        }
    }

    fun addWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO) {//also we are specifying the thread also like IO thread
            wishRepository.addAWish(wish=wish)
        }
    }

    fun updateWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.updateAWish(wish=wish)
        }
    }

    fun deleteWish(wish: Wish){
        viewModelScope.launch(Dispatchers.IO) {
            wishRepository.deleteAWish(wish=wish)
            getAllWishes = wishRepository.getWishes()
        }
    }

    fun getAWishById(id:Long):Flow<Wish> {
        return wishRepository.getAWishById(id)
    }


}