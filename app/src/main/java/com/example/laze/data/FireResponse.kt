package com.example.laze.data

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

sealed class FireResponse
data class OnSuccess(val querySnapshot: QuerySnapshot?): FireResponse()
data class OnError(val exception: FirebaseFirestoreException?): FireResponse()

sealed class ChatResponse
data class OnChatSuccess(val data: List<DocumentSnapshot>): ChatResponse()
data class OnChatError(val exception: FirebaseFirestoreException): ChatResponse()
