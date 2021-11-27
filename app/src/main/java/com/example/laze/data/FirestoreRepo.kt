package com.example.laze.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class FirestoreRepo {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun getPostDetails() = callbackFlow {

        var collection: CollectionReference? = null
        try {
            collection = firestore.collection("users").document(auth.currentUser!!.uid).collection("uploads")
        } catch (e: Throwable) {
            close(e)
        }

        val subscription = collection?.addSnapshotListener { value, error ->
            val response = if (error == null) {
                OnSuccess(value)
            } else {
                OnError(error)
            }
            trySend(response)
        }

        awaitClose { subscription?.remove() }
    }
}