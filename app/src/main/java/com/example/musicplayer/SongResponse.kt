package com.example.musicplayer

import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

sealed class SongResponse
data class OnSuccess(val querySnapshot: QuerySnapshot?): SongResponse()
data class OnError(val exception: FirebaseFirestoreException?): SongResponse()