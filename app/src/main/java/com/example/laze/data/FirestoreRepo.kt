package com.example.laze.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class FirestoreRepo {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val storage = FirebaseStorage.getInstance()

    // get all posts uploaded to firebase
    fun getPostDetails() = callbackFlow {

        var collection: CollectionReference? = null
        try {
            collection = firestore.collection("uploads")
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

    // gets posts that belong to current user
    fun getUserPosts() = callbackFlow {
        var collection: CollectionReference? = null
        try {
            collection = auth.currentUser?.uid?.let { firestore.collection("users").document(it).collection("uploads") }
        } catch (e: Throwable) {
            Log.e("hello","Failed to get user posts ${e.localizedMessage}")
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
        awaitClose { subscription?.remove()}
    }

    // deletes a document
    fun deleteUserPost(imageName: String) = callbackFlow {
        firestore.collection("uploads").document(imageName).delete().addOnCompleteListener {
            if (it.isSuccessful) {
                if (auth.currentUser?.uid?.isNullOrEmpty() == true)  {
                    trySend(-1)
                    return@addOnCompleteListener
                }
                firestore.collection("users").document(auth.currentUser!!.uid).collection("uploads").document(imageName).delete().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        storage.getReference(imageName).delete().addOnCompleteListener { task2 ->
                            if (task2.isSuccessful) {
                                trySend(400)

                            } else {
                                Log.e("hello","Failed to delete image from firebase storage: ${task2.exception}")
                                trySend(-1)
                            }
                        }
                    } else {
                        Log.e("hello","Failed to delete from users/uploads: ${task.exception}")
                        trySend(-1)
                    }
                }

            } else {
                Log.e("hello","Couldn't delete document from uploads: ${it.exception}")
            }
        }
        awaitClose()
    }
}