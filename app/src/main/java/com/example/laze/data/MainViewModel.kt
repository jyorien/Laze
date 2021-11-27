package com.example.laze.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){
    val postsStateFlow = MutableStateFlow<FireResponse?>(null)
    val repo = FirestoreRepo()

    init {
        viewModelScope.launch {
            repo.getPostDetails().collect {
                postsStateFlow.value = it
            }
        }
    }
}