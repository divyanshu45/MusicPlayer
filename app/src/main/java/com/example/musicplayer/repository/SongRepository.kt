package com.example.musicplayer.repository

import com.example.musicplayer.Constants
import com.example.musicplayer.OnError
import com.example.musicplayer.OnSuccess
import com.example.musicplayer.model.Song
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi
class SongRepository {

    private val firestore = FirebaseFirestore.getInstance()

    fun getSongs() = callbackFlow {

        val collection = firestore.collection(Constants.COLLECTION_TRACKS)
        val snapshotListener = collection.addSnapshotListener { value, error ->
            val response = if (error == null) {
                OnSuccess(value)
            } else {
                OnError(error)
            }

            offer(response)
        }

        awaitClose {
            snapshotListener.remove()
        }
    }
}