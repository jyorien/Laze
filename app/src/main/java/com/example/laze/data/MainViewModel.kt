package com.example.laze.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){
    val repo = FirestoreRepo()
    val postsStateFlow = MutableStateFlow<FireResponse?>(null)
    val userPostsStateFlow = MutableStateFlow<FireResponse?>(null)


    init {
        viewModelScope.launch {
            repo.getPostDetails().collect {
                postsStateFlow.value = it
            }
        }

        viewModelScope.launch {
            repo.getUserPosts().collect {
                userPostsStateFlow.value = it
            }
        }
    }
}