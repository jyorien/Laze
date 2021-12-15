package com.example.laze.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.time.LocalDateTime

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
            collection = auth.currentUser?.uid?.let {
                firestore.collection("users").document(it).collection("uploads")
            }
        } catch (e: Throwable) {
            Log.e("hello", "Failed to get user posts ${e.localizedMessage}")
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

    // deletes a document
    fun deleteUserPost(imageName: String) = callbackFlow {
        firestore.collection("uploads").document(imageName).delete().addOnCompleteListener {
            if (it.isSuccessful) {
                if (auth.currentUser?.uid?.isNullOrEmpty() == true) {
                    trySend(-1)
                    return@addOnCompleteListener
                }
                firestore.collection("users").document(auth.currentUser!!.uid).collection("uploads")
                    .document(imageName).delete().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            storage.getReference(imageName).delete()
                                .addOnCompleteListener { task2 ->
                                    if (task2.isSuccessful) {
                                        trySend(400)

                                    } else {
                                        Log.e(
                                            "hello",
                                            "Failed to delete image from firebase storage: ${task2.exception}"
                                        )
                                        trySend(-1)
                                    }
                                }
                        } else {
                            Log.e("hello", "Failed to delete from users/uploads: ${task.exception}")
                            trySend(-1)
                        }
                    }

            } else {
                Log.e("hello", "Couldn't delete document from uploads: ${it.exception}")
            }
        }
        awaitClose()
    }

    // get current chat updates
    fun subscribeToMessages(ref: CollectionReference) = callbackFlow {
        val subscription = ref.addSnapshotListener { value, error ->
            val response = if (error == null) {
                value?.documents?.let { OnChatSuccess(it) }
            } else {
                OnChatError(error)
            }
            trySend(response)

        }
        awaitClose { subscription.remove() }
    }

    // send a message
    fun sendText(
        text: String,
        receiverReference: CollectionReference,
        senderReference: CollectionReference
    ) = callbackFlow {
        val currentTime = LocalDateTime.now().toString()
        auth.currentUser?.uid?.let { uid ->
            val data = mapOf(
                "message" to text,
                "senderId" to uid
            )
            senderReference.document(currentTime).set(data).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    receiverReference.document(currentTime).set(data).addOnCompleteListener { task2 ->
                        if (task.isSuccessful) {
                            trySend(200)

                        } else {
                            Log.e(
                                "hello",
                                "Error sending text2 ${task.exception?.localizedMessage}"
                            )
                            trySend(-1)
                        }
                    }

                } else {
                    Log.e("hello", "Error sending text ${task.exception?.localizedMessage}")
                    trySend(-1)
                }
            }
        }
        awaitClose()
    }

    // add participant details
    fun addParticipantDetails(
        senderId: String,
        senderUsername: String,
        senderReference: DocumentReference,
        receiverId: String,
        receiverUsername: String,
        receiverReference: DocumentReference
    ) =
        callbackFlow {
            val data = mapOf(
                "senderId" to senderId,
                "senderUsername" to senderUsername,
                "receiverId" to receiverId,
                "receiverUsername" to receiverUsername
            )

            val data2 = mapOf(
                "senderId" to receiverId,
                "senderUsername" to receiverUsername,
                "receiverId" to senderId,
                "receiverUsername" to senderUsername
            )
            senderReference.set(data).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    receiverReference.set(data2).addOnCompleteListener { task2 ->
                        if (task2.isSuccessful) {
                            trySend(200)
                        } else {
                            Log.e(
                                "hello",
                                "Couldn't set participant2 details ${task2.exception?.localizedMessage}"
                            )
                            trySend(-1)
                        }
                    }
                } else {
                    Log.e(
                        "hello",
                        "Couldn't set participant details ${task.exception?.localizedMessage}"
                    )
                    trySend(-1)
                }
            }
            awaitClose()
        }

    // get user's chats
    fun getAllChats() = callbackFlow {
        val subscription = auth.currentUser?.uid?.let { uid ->
            firestore.collection("users").document(uid).collection("chats").addSnapshotListener { value, error ->
                val response = if (error != null) {
                    OnError(error)
                } else {
                    OnSuccess(value)
                }
                trySend(response)
            }
        }
        awaitClose { subscription?.remove() }
    }
}