package com.example.laze.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){
    val repo = FirestoreRepo()
    val postsStateFlow = MutableStateFlow<FireResponse?>(null)
    val userPostsStateFlow = MutableStateFlow<FireResponse?>(null)
    val deleteStateFlow = MutableStateFlow(0)
    val messagesList = MutableStateFlow<ChatResponse?>(null)

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

    fun deletePost(imagePath: String) {
        viewModelScope.launch {
            repo.deleteUserPost(imagePath).collect {
                deleteStateFlow.value = it
            }
        }

    }

    fun subscribeToMessageThread(ref: CollectionReference) {
        viewModelScope.launch {
            repo.subscribeToMessages(ref).collect {
                messagesList.value = it
            }
        }

    }
}